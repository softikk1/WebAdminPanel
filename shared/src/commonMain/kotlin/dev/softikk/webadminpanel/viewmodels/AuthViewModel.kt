package dev.softikk.webadminpanel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.softikk.webadminpanel.api.AuthApi
import dev.softikk.webadminpanel.datastore.AuthDataStore
import dev.softikk.webadminpanel.models.AuthDataStoreModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authApi: AuthApi, private val authDataStore: AuthDataStore) :
    ViewModel() {
    private val _isLogin = MutableStateFlow(false)
    val isLogin = _isLogin.asStateFlow()

    init {
        viewModelScope.launch {
            val authModel = authDataStore.getAuthModel()
            authModel?.let {
                login(
                    email = authModel.email, password = authModel.password
                )
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val isLoginReceive = authApi.login(
                email = email, password = password
            )
            if (isLoginReceive) {
                authDataStore.setAuthModel(
                    authModel = AuthDataStoreModel(
                        email = email, password = password
                    )
                )
                _isLogin.value = isLoginReceive
            }
        }
    }
}
