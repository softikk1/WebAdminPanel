package dev.softikk.webadminpanel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object TestDatabase {
    private var _sites = MutableStateFlow<List<Site>>(emptyList())
    val sites = _sites.asStateFlow()

    private var _admins = MutableStateFlow<List<Admin>>(emptyList())
    val admins = _admins.asStateFlow()

    suspend fun saveSite(site: Site) {
        if (site.id in _sites.value.map { it.id }) {
            replaceSite(site)
        } else {
            newSite(site)
        }
    }

    private suspend fun newSite(site: Site) {
        _sites.emit(
            _sites.value + site
        )
    }

    private suspend fun replaceSite(site: Site) {
        _sites.emit(
            _sites.value.map {
                if (it.id == site.id) {
                    site
                } else {
                    it
                }
            })
    }

    suspend fun saveAdmin(admin: Admin) {
        if (admin.id in _admins.value.map { it.id }) {
            replaceAdmin(admin)
        } else {
            newAdmin(admin)
        }
    }

    private suspend fun newAdmin(admin: Admin) {
        _admins.emit(
            _admins.value + admin
        )
    }

    private suspend fun replaceAdmin(admin: Admin) {
        _admins.emit(
            _admins.value.map {
                if (it.id == admin.id) {
                    admin
                } else {
                    it
                }
            })
    }
}