package dev.softikk.webadminpanel.models

import dev.softikk.webadminpanel.DefaultDescriptionNameSiteDetails
import dev.softikk.webadminpanel.DefaultHostNameSiteDetails
import dev.softikk.webadminpanel.DefaultSiteNameSiteDetails
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class SiteScreenModel(
    val siteName: String = DefaultSiteNameSiteDetails,
    val host: String = DefaultHostNameSiteDetails,
    val description: String = DefaultDescriptionNameSiteDetails,
    val elements: List<ElementUiModel> = emptyList(),
    val createAt: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
)
