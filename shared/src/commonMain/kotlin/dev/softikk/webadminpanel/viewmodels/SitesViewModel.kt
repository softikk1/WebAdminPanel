package dev.softikk.webadminpanel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.softikk.webadminpanel.api.SitesApi
import dev.softikk.webadminpanel.models.SchemaModel
import dev.softikk.webadminpanel.models.SchemaUIModel
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
    suspend fun initStateSite(siteId: String?): SiteScreenModel {
        clearStateSite()
        val siteScreenModel = if (siteId != null) {
            val site = sitesApi.getSite(Uuid.parse(siteId))
            _stateSite.value.copy(
                siteName = site.name,
                host = site.host,
                description = site.description,
                elements = site.elements.map { elementModel ->
                    SchemaUIModel(
                        id = elementModel.id,
                        seqId = Uuid.generateV4(),
                        key = elementModel.key,
                        value = elementModel.value
                    )
                },
                createAt = site.createAt
            )
        } else {
            _stateSite.value
        }
        _stateSite.update {
            siteScreenModel
        }
        return siteScreenModel
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
                        SchemaModel(
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
                        SchemaModel(
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

    fun deleteSchema(seqId: Uuid) {
        viewModelScope.launch {
            _stateSite.update {
                it.copy(
                    elements = _stateSite.value.elements.filter { elementUiModel -> elementUiModel.seqId != seqId })
            }
        }
    }

    fun searchSites(search: String) {
        viewModelScope.launch {
            _sites.value = sitesApi.searchSites(search)
        }
    }
}