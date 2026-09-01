package dev.softikk.webadminpanel.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class GetContentDtoReceive(
    val model: JsonElement?
)
