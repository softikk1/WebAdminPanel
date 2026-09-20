package dev.softikk.webadminpanel.models

import dev.softikk.webadminpanel.DefaultDescriptionNameSiteDetails
import dev.softikk.webadminpanel.DefaultHostNameSiteDetails
import dev.softikk.webadminpanel.DefaultSiteNameSiteDetails

data class SiteScreenModel(
    val siteName: String = DefaultSiteNameSiteDetails,
    val host: String = DefaultHostNameSiteDetails,
    val description: String = DefaultDescriptionNameSiteDetails,
    val elements: List<UISiteElementModel> = emptyList()
)
