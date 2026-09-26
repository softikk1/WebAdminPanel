package dev.softikk.webadminpanel.dto.sites

import dev.softikk.webadminpanel.models.ElementModel
import kotlinx.serialization.Serializable

@Serializable
data class UpdateSiteReceiveDto(
    val siteName: String,
    val host: String,
    val description: String,
    val elements: List<ElementModel>
)
