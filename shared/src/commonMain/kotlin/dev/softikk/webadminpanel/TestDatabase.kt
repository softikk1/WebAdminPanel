package dev.softikk.webadminpanel

import dev.softikk.webadminpanel.models.AdminModel
import dev.softikk.webadminpanel.models.SiteModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object TestDatabase {
    private var _sites = MutableStateFlow<List<SiteModel>>(emptyList())
    val sites = _sites.asStateFlow()

    private var _admins = MutableStateFlow<List<AdminModel>>(emptyList())
    val admins = _admins.asStateFlow()

    suspend fun saveSite(site: SiteModel) {
        if (site.id in _sites.value.map { it.id }) {
            replaceSite(site)
        } else {
            newSite(site)
        }
    }

    private suspend fun newSite(site: SiteModel) {
        _sites.emit(
            _sites.value + site
        )
    }

    private suspend fun replaceSite(site: SiteModel) {
        _sites.emit(
            _sites.value.map {
                if (it.id == site.id) {
                    site
                } else {
                    it
                }
            })
    }

    suspend fun saveAdmin(admin: AdminModel) {
        if (admin.id in _admins.value.map { it.id }) {
            replaceAdmin(admin)
        } else {
            newAdmin(admin)
        }
    }

    private suspend fun newAdmin(admin: AdminModel) {
        _admins.emit(
            _admins.value + admin
        )
    }

    private suspend fun replaceAdmin(admin: AdminModel) {
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