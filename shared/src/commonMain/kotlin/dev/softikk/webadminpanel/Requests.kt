package dev.softikk.webadminpanel

import dev.softikk.webadminpanel.dto.GetContentDtoReceive
import dev.softikk.webadminpanel.dto.PostContentDtoReceive
import dev.softikk.webadminpanel.dto.PostContentDtoRespond
import dev.softikk.webadminpanel.dto.UpdateContentDtoReceive
import dev.softikk.webadminpanel.dto.UpdateContentDtoRespond
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

suspend inline fun addSchema(client: HttpClient, host: String, model: JsonElement): JsonElement {
    val receive = client.post(RECEIVE_AND_RESPOND_SCHEME_PATH_API) {
        contentType(ContentType.Application.Json)
        setBody(
            PostContentDtoRespond(
                host = host, model = Json.encodeToJsonElement(
                    model
                )
            )
        )
    }
    return receive.body<PostContentDtoReceive>().model
}

suspend inline fun updateSchema(
    client: HttpClient, host: String, model: JsonElement
): JsonElement {
    val receive = client.post(UPDATE_SCHEME_PATH_API) {
        contentType(ContentType.Application.Json)
        setBody(
            UpdateContentDtoRespond(
                host = host, model = model
            )
        )
    }
    return receive.body<UpdateContentDtoReceive>().model
}

private const val HOST_KEY_BY_PARAMETER = "host"
suspend fun getSchema(client: HttpClient, host: String): JsonElement? {
    val receive = client.get(RECEIVE_AND_RESPOND_SCHEME_PATH_API) {
        parameter(HOST_KEY_BY_PARAMETER, host)
    }
    return receive.body<GetContentDtoReceive>().model
}