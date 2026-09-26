package dev.softikk.webadminpanel.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthDataStoreModel(
    val email: String,
    val password: String
)
