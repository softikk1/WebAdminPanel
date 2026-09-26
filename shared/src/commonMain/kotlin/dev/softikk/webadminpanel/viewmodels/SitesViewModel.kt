package dev.softikk.webadminpanel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.softikk.webadminpanel.api.SitesApi
import dev.softikk.webadminpanel.models.SiteModel
import dev.softikk.webadminpanel.models.SiteScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SitesViewModel(private val sitesApi: SitesApi) : ViewModel() {
    private val _stateSite = MutableStateFlow(SiteScreenModel())
    val stateSite = _stateSite.asStateFlow()

    private val _sites = MutableStateFlow<List<SiteModel>>(emptyList())
    val sites = _sites.asStateFlow()

    fun initStateSite(siteId: String?) {
        viewModelScope.launch {
            val sites = sitesApi.getSites()
            val siteId = siteId?.let { Uuid.parse(it) }
            _stateSite.update {
                if ((siteId != null) and (siteId in sites.map { site -> site.id })) {
                    val site = sites.singleOrNull { site -> siteId == site.id }
                    if (site != null) {
                        it.copy(
                            siteName = site.name,
                            host = site.host,
                            description = site.description,
                            elements = site.elements,
                            createAt = site.createAt
                        )
                    } else {
                        it
                    }
                } else {
                    it
                }
            }
        }
    }

    fun getSites() {
        viewModelScope.launch {
            _sites.value = sitesApi.getSites()
        }
    }

    fun setStateSite(siteScreenModel: SiteScreenModel) {
        _stateSite.update { siteScreenModel }
    }

    fun clearStateSite() {
        _stateSite.update { SiteScreenModel() }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun saveSite(siteId: String?) {
        viewModelScope.launch {
            val siteId = siteId?.let { Uuid.parse(it) }
            val stateSiteReceive = _stateSite.value

            if (siteId == null) {
                sitesApi.createSite(
                    name = stateSiteReceive.siteName,
                    host = stateSiteReceive.host,
                    description = stateSiteReceive.description,
                    elements = stateSiteReceive.elements
                )
            } else {
                sitesApi.updateSite(
                    siteId = siteId,
                    name = stateSiteReceive.siteName,
                    host = stateSiteReceive.host,
                    description = stateSiteReceive.description,
                    elements = stateSiteReceive.elements.filter { it.key.isNotBlank() })
            }
        }
    }
}