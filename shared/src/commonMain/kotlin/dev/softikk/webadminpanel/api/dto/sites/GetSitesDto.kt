package dev.softikk.webadminpanel.api.dto.sites

import dev.softikk.webadminpanel.models.SiteModel
import kotlinx.serialization.Serializable

@Serializable
data class GetSitesRespondDto(
    val sites: List<SiteModel>
)