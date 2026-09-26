package dev.softikk.webadminpanel.dto.admins

import dev.softikk.webadminpanel.models.AdminModel
import kotlinx.serialization.Serializable

@Serializable
data class GetAdminRespondDto(
    val admin: AdminModel
)
