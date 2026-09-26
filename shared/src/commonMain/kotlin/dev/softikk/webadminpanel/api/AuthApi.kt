package dev.softikk.webadminpanel.api

import dev.softikk.webadminpanel.dto.LoginReceiveDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(private val client: HttpClient) {
    suspend fun login(email: String, password: String): Boolean {
        val receive = client.post("/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginReceiveDto(
                email = email,
                password = password
            ))
        }
        return receive.body<Boolean>()
    }
}