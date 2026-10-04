package dev.softikk.webadminpanel.api.dto.sites

import dev.softikk.webadminpanel.models.SchemaModel
import kotlinx.serialization.Serializable

@Serializable
data class CreateSiteReceiveDto(
    val name: String, val host: String, val description: String, val elements: List<SchemaModel>
)
