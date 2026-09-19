package dev.softikk.webadminpanel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.softikk.webadminpanel.TestDatabase
import dev.softikk.webadminpanel.models.SiteModel
import kotlinx.coroutines.launch

class SitesViewModel(private val database: TestDatabase) : ViewModel() {
    fun saveSite(site: SiteModel) {
        viewModelScope.launch {
            database.saveSite(site.copy(elements = site.elements.filter { it.key.isNotBlank() }))
        }
    }
}