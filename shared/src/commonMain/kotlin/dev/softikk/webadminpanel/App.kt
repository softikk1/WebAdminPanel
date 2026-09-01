package dev.softikk.webadminpanel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.softikk.webadminpanel.components.WebTextField
import dev.softikk.webkit.Website
import dev.softikk.webkit.navigation.Route
import dev.softikk.webkit.navigation.WebNavigation
import dev.softikk.webkit.theme.Dimens
import dev.softikk.webkit.theme.DimensTheme
import dev.softikk.webkit.theme.Shapes
import dev.softikk.webkit.theme.WebTheme

sealed class Routes(open val route: String) {
    data object Auth : Routes("/auth")
    data object Sites : Routes("/sites") {
        data object Id : Routes("${route}/{id}")
    }

    data object Admins : Routes("/admins") {
        data object Id : Routes("${route}/{id}")
    }
}

@Composable
fun App(onNavHostReady: (suspend (NavController) -> Unit)) {
//    val siteHost = "localhost:8081"
//    val client = HttpClient {
//        defaultRequest {
//            host = "localhost"
//            port = 8080
//        }
//        install(ContentNegotiation) {
//            json()
//        }
//    }
//
//    var schemas by remember { mutableStateOf<MutableMap<String, String>?>(null) }
//
//    val coroutine = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        val receiveSchema = getSchema(
//            client = client, host = siteHost
//        )
//        receiveSchema?.let {
//            schemas = receiveSchema.jsonObject.map { (key, value) ->
//                key to value.jsonPrimitive.content
//            }.toMutableStateMap()
//        }
//
//    }

    val navController = rememberNavController()

    Website(
        theme = WebTheme(
            colorScheme = lightColorScheme(
                background = Color(0xFFFFFFFF),
                primary = Color(0xFF9340FF),
                surface = Color(0xFFFFFFFF),
                onSurface = Color(0xFF000000),
                onSurfaceVariant = Color(0xFF8B8B8B),
                surfaceContainer = Color(0xFFEBEBEB)
            ), dimens = Dimens(
                shapes = Shapes(
                    mediumShape = RoundedCornerShape(10.dp), largeShape = RoundedCornerShape(20.dp)
                )
            )
        )
    ) {
        WebNavigation(
            navController = navController,
            onNavHostReady = onNavHostReady,
            startDestination = Routes.Auth.route,
            routes = listOf(
                Route(
                    urlPath = Routes.Auth.route, content = {
                        val stateTextFieldEmail = rememberTextFieldState()
                        val stateTextFieldPassword = rememberTextFieldState()

                        Column(
                            verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.smallPadding)
                        ) {
                            WebTextField(
                                state = stateTextFieldEmail, labelText = "Email"
                            )
                            WebTextField(
                                state = stateTextFieldPassword,
                                labelText = "Password",
                                keyboardType = KeyboardType.Password
                            )
                        }
                    }), Route(
                    urlPath = Routes.Admins.route, content = {

                    }), Route(
                    urlPath = Routes.Sites.route, content = {

                    }), Route(
                    urlPath = Routes.Admins.Id.route, content = {

                    }), Route(
                    urlPath = Routes.Sites.Id.route, content = {

                    })
            )
        )
    }
}


//Column {
//    schemas?.let {
//        LazyColumn {
//            items(
//                it.toList().sortedBy { value -> value.first },
//                key = { value -> value.first }) { value ->
//                val stateKey = rememberTextFieldState(initialText = value.first)
//                val stateValue = rememberTextFieldState(initialText = value.second)
//                var oldStateKey by remember { mutableStateOf(stateKey.text) }
//                LaunchedEffect(stateKey.text, stateValue.text) {
//                    if (stateKey.text !in it.keys) {
//                        it.remove(oldStateKey)
//                    }
//                    it[stateKey.text.toString()] = stateValue.text.toString()
//                    oldStateKey = stateKey.text
//                }
//
//                Row {
//                    TextField(
//                        state = stateKey, label = {
//                            Text(
//                                text = "Key"
//                            )
//                        })
//                    TextField(
//                        state = stateValue, label = {
//                            Text(
//                                text = "Value"
//                            )
//                        })
//                }
//            }
//        }
//    }
//    Row {
//        Button({
//            coroutine.launch {
//                if (getSchema(client = client, host = siteHost) != null) {
//                    updateSchema(
//                        client = client,
//                        host = siteHost,
//                        model = Json.encodeToJsonElement(schemas)
//                    )
//                } else {
//                    schemas = addSchema(
//                        client = client,
//                        host = siteHost,
//                        model = Json.encodeToJsonElement(schemas)
//                    ).jsonObject.map { (key, value) ->
//                        key to value.jsonPrimitive.content
//                    }.toMutableStateMap()
//                }
//            }
//        }) {
//            Text("Save")
//        }
//        Button({
//            if (schemas != null) {
//                schemas!![(schemas!!.keys.size + 1).toString()] = ""
//            } else {
//                schemas = mutableMapOf("" to "")
//            }
//        }) {
//            Text("Plus")
//        }
//    }
//}