package dev.softikk.webadminpanel.models

import dev.softikk.webadminpanel.DefaultAdminEmailAdminDetails
import dev.softikk.webadminpanel.DefaultAdminNameAdminDetails

data class AdminScreenModel(
    val name: String = DefaultAdminNameAdminDetails,
    val email: String = DefaultAdminEmailAdminDetails,
    val password: String = "",
    val sites: List<SiteModel> = emptyList()
)