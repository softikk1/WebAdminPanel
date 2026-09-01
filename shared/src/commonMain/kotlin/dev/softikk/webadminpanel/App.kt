package dev.softikk.webadminpanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateMap
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun App() {
    val siteHost = "localhost:8081"
    val client = HttpClient {
        defaultRequest {
            host = "localhost"
            port = 8080
        }
        install(ContentNegotiation) {
            json()
        }
    }

    var schemas by remember { mutableStateOf<MutableMap<String, String>?>(null) }

    val coroutine = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val receiveSchema = getSchema(
            client = client, host = siteHost
        )
        receiveSchema?.let {
            schemas = receiveSchema.jsonObject.map { (key, value) ->
                key to value.jsonPrimitive.content
            }.toMutableStateMap()
        }

    }


    Column {
        schemas?.let {
            LazyColumn {
                items(it.toList().sortedBy { value -> value.first }, key = { value -> value.first }) { value ->
                    val stateKey = rememberTextFieldState(initialText = value.first)
                    val stateValue = rememberTextFieldState(initialText = value.second)
                    var oldStateKey by remember { mutableStateOf(stateKey.text) }
                    LaunchedEffect(stateKey.text, stateValue.text) {
                        if (stateKey.text !in it.keys) {
                            it.remove(oldStateKey)
                        }
                        it[stateKey.text.toString()] = stateValue.text.toString()
                        oldStateKey = stateKey.text
                    }

                    Row {
                        TextField(
                            state = stateKey, label = {
                                Text(
                                    text = "Key"
                                )
                            })
                        TextField(
                            state = stateValue, label = {
                                Text(
                                    text = "Value"
                                )
                            })
                    }
                }
            }
        }
        Row {
            Button({
                coroutine.launch {
                    if (getSchema(client = client, host = siteHost) != null) {
                        updateSchema(
                            client = client,
                            host = siteHost,
                            model = Json.encodeToJsonElement(schemas)
                        )
                    } else {
                        schemas = addSchema(
                            client = client,
                            host = siteHost,
                            model = Json.encodeToJsonElement(schemas)
                        ).jsonObject.map { (key, value) ->
                            key to value.jsonPrimitive.content
                        }.toMutableStateMap()
                    }
                }
            }) {
                Text("Save")
            }
            Button({
                if (schemas != null) {
                    schemas!![(schemas!!.keys.size + 1).toString()] = ""
                } else {
                    schemas = mutableMapOf("" to "")
                }
            }) {
                Text("Plus")
            }
        }
    }
}