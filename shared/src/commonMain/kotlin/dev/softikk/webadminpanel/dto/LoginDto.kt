package dev.softikk.webadminpanel.dto

import kotlinx.serialization.Serializable

@Serializable
class LoginReceiveDto(
    val email: String,
    val password: String
)