package dev.softikk.webadminpanel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.softikk.webadminpanel.api.AdminsApi
import dev.softikk.webadminpanel.models.AdminModel
import dev.softikk.webadminpanel.models.AdminScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AdminsViewModel(private val adminsApi: AdminsApi) : ViewModel() {
    private val _stateAdmin = MutableStateFlow(AdminScreenModel())
    val stateAdmin = _stateAdmin.asStateFlow()

    private val _admins = MutableStateFlow<List<AdminModel>>(emptyList())
    val admins = _admins.asStateFlow()

    fun initStateAdmin(adminId: String?) {
        viewModelScope.launch {
            val admins = adminsApi.getAdmins()
            val adminId = adminId?.let { Uuid.parse(it) }
            _stateAdmin.update {
                if ((adminId != null) and (adminId in admins.map { admin -> admin.id })) {
                    val adminReceive = admins.singleOrNull { admin -> adminId == admin.id }
                    if (adminReceive != null) {
                        it.copy(
                            name = adminReceive.name,
                            email = adminReceive.email,
                            password = adminReceive.password,
                            sites = adminReceive.sites
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

    fun getAdmins() {
        viewModelScope.launch {
            _admins.value = adminsApi.getAdmins()
        }
    }

    fun setStateAdmin(adminParam: AdminScreenModel) {
        _stateAdmin.update {
            it.copy(
                name = adminParam.name,
                email = adminParam.email,
                password = adminParam.password,
                sites = adminParam.sites
            )
        }
    }

    fun clearStateAdmin() {
        _stateAdmin.update { AdminScreenModel() }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun saveAdmin(adminId: String?) {
        viewModelScope.launch {
            val stateAdminReceive = _stateAdmin.value
            val adminId = adminId?.let { Uuid.parse(it) }
            if (adminId == null) {
                adminsApi.createAdmin(
                    name = stateAdminReceive.name,
                    email = stateAdminReceive.email,
                    password = stateAdminReceive.password,
                    sites = stateAdminReceive.sites
                )
            } else {
                adminsApi.updateAdmin(
                    adminId = adminId,
                    name = stateAdminReceive.name,
                    email = stateAdminReceive.email,
                    password = stateAdminReceive.password,
                    sites = stateAdminReceive.sites
                )
            }
        }
    }
}