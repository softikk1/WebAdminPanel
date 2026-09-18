package dev.softikk.webadminpanel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.softikk.webadminpanel.Admin
import dev.softikk.webadminpanel.TestDatabase
import kotlinx.coroutines.launch

class AdminsViewModel(private val database: TestDatabase) : ViewModel() {
    fun saveAdmin(admin: Admin) {
        viewModelScope.launch {
            database.saveAdmin(admin = admin)
        }
    }
}