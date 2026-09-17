package dev.softikk.webadminpanel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object TestDatabase {
    private var _sites = MutableStateFlow<List<Site>>(emptyList())
    val sites = _sites.asStateFlow()

    private var _admins = MutableStateFlow<List<Admin>>(emptyList())
    val admins = _admins.asStateFlow()

    suspend fun newSite(site: Site) {
        _sites.emit(
            _sites.value + site
        )
    }
}