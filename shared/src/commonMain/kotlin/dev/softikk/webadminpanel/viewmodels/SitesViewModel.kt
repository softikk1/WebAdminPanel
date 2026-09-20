package dev.softikk.webadminpanel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.softikk.webadminpanel.TestDatabase
import dev.softikk.webadminpanel.models.SiteModel
import dev.softikk.webadminpanel.models.SiteScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SitesViewModel(private val database: TestDatabase) : ViewModel() {
    private val _stateSite = MutableStateFlow(SiteScreenModel())
    val stateSite = _stateSite.asStateFlow()

    fun initStateSite(siteId: String?) {
        val sites = database.sites.value
        val siteId = siteId?.let { Uuid.parse(it) }
        _stateSite.update {
            if ((siteId != null) and (siteId in sites.map { site -> site.id })) {
                val site = sites.singleOrNull { site -> siteId == site.id }
                if (site != null) {
                    it.copy(
                        siteName = site.name,
                        host = site.host,
                        description = site.description,
                        elements = site.elements
                    )
                } else {
                    it
                }
            } else {
                it
            }
        }
    }

    fun setStateSite(siteScreenModel: SiteScreenModel) {
        _stateSite.update { siteScreenModel }
    }

    fun clearStateSite() {
        _stateSite.update { SiteScreenModel() }
    }

    fun getSite(siteId: Uuid): SiteModel? = database.sites.value.singleOrNull { it.id == siteId }

    @OptIn(ExperimentalUuidApi::class)
    fun saveSite(siteId: String?) {
        viewModelScope.launch {
            val sites = database.sites.value
            val siteId = siteId?.let { Uuid.parse(it) }
            val createAt = sites.singleOrNull { it.id == siteId }?.createAt
            val stateSiteReceive = _stateSite.value

            database.saveSite(
                SiteModel(
                    id = siteId ?: Uuid.generateV4(),
                    name = stateSiteReceive.siteName,
                    host = stateSiteReceive.host,
                    description = stateSiteReceive.description,
                    createAt = createAt ?: Clock.System.now().toLocalDateTime(TimeZone.UTC),
                    elements = stateSiteReceive.elements.filter { it.key.isNotBlank() },
                )
            )
        }
    }
}