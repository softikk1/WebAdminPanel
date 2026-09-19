package dev.softikk.webadminpanel.models

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class AdminModel(
    val id: Uuid,
    val name: String,
    val email: String,
    val password: String,
    val sites: List<SiteModel>
)