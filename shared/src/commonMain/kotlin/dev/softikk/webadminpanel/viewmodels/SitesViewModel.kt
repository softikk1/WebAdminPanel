package dev.softikk.webadminpanel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.softikk.webadminpanel.api.SitesApi
import dev.softikk.webadminpanel.models.ElementModel
import dev.softikk.webadminpanel.models.ElementUiModel
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

    @OptIn(ExperimentalUuidApi::class)
    fun initStateSite(siteId: String?) {
        viewModelScope.launch {
            clearStateSite()
            _stateSite.update {
                if (siteId != null) {
                    val site = sitesApi.getSite(Uuid.parse(siteId))
                    it.copy(
                        siteName = site.name,
                        host = site.host,
                        description = site.description,
                        elements = site.elements.map { elementModel ->
                            ElementUiModel(
                                id = elementModel.id,
                                seqId = Uuid.generateV4(),
                                key = elementModel.key,
                                value = elementModel.value
                            )
                        },
                        createAt = site.createAt
                    )
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
                    elements = stateSiteReceive.elements.filter { it.key.isNotBlank() }.map {
                        ElementModel(
                            id = it.id, key = it.key, value = it.value
                        )
                    })
            } else {
                sitesApi.updateSite(
                    siteId = siteId,
                    name = stateSiteReceive.siteName,
                    host = stateSiteReceive.host,
                    description = stateSiteReceive.description,
                    elements = stateSiteReceive.elements.filter { it.key.isNotBlank() }.map {
                        ElementModel(
                            id = it.id, key = it.key, value = it.value
                        )
                    })
            }
        }
    }

    fun deleteSite(siteId: Uuid) {
        viewModelScope.launch {
            sitesApi.deleteSite(siteId)
        }
    }
}