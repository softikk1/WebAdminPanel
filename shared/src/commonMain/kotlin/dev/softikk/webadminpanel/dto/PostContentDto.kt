package dev.softikk.webadminpanel.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class PostContentDtoRespond(
    val host: String, val model: JsonElement
)

@Serializable
data class PostContentDtoReceive(
    val model: JsonElement
)