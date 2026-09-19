package dev.softikk.webadminpanel.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class SiteModel(
    val id: Uuid,
    val name: String,
    val host: String,
    val description: String,
    val createAt: LocalDateTime,
    val elements: List<UISiteElementModel> = emptyList()
)