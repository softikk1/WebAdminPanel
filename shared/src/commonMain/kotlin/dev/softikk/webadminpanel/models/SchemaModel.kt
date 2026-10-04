package dev.softikk.webadminpanel.models

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class SchemaModel(
    val id: Uuid?,
    val key: String,
    val value: String
)
