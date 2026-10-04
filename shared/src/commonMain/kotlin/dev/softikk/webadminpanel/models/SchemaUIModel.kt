package dev.softikk.webadminpanel.models

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class SchemaUIModel(
    val id: Uuid?,
    val seqId: Uuid,
    val key: String,
    val value: String
)