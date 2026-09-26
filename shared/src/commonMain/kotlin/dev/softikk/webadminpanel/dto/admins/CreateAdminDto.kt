package dev.softikk.webadminpanel.dto.admins

import dev.softikk.webadminpanel.models.SiteModel
import kotlinx.serialization.Serializable

@Serializable
data class CreateAdminReceiveDto(
    val email: String, val name: String, val password: String, val sites: List<SiteModel>
)