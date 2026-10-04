package dev.softikk.webadminpanel.api.dto.admins

import dev.softikk.webadminpanel.models.SiteModel
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAdminReceiveDto(
    val name: String, val email: String, val password: String, val sites: List<SiteModel>
)
