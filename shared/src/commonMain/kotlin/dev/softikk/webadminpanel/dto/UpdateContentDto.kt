package dev.softikk.webadminpanel.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class UpdateContentDtoRespond(
    val host: String, val model: JsonElement
)

@Serializable
data class UpdateContentDtoReceive(
    val model: JsonElement
)