package dev.softikk.webadminpanel.dto.sites

import dev.softikk.webadminpanel.models.ElementModel
import kotlinx.serialization.Serializable

@Serializable
data class CreateSiteReceiveDto(
    val name: String, val host: String, val description: String, val elements: List<ElementModel>
)
