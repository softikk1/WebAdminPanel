package dev.softikk.webadminpanel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.softikk.webadminpanel.Site
import dev.softikk.webadminpanel.TestDatabase
import kotlinx.coroutines.launch

class SitesViewModel(private val database: TestDatabase) : ViewModel() {
    fun newSite(site: Site) {
        viewModelScope.launch {
            database.newSite(site)
        }
    }
}