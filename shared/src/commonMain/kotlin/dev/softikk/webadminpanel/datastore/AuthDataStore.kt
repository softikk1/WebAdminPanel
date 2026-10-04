package dev.softikk.webadminpanel.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.softikk.webadminpanel.models.AuthDataStoreModel
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

val DATA_AUTH = stringPreferencesKey("data_auth")

expect fun createDataStore(): DataStore<Preferences>

internal const val dataStoreFileName = "cms.preferences_pb"

class AuthDataStore {
    private val dataStore = createDataStore()

    suspend fun setAuthModel(authModel: AuthDataStoreModel) {
        dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[DATA_AUTH] = Json.encodeToString(authModel)
            }
        }
    }

    suspend fun getAuthModel(): AuthDataStoreModel? {
        val preferences = dataStore.data.first()
        return preferences[DATA_AUTH]?.let {
            Json.decodeFromString<AuthDataStoreModel?>(
                it
            )
        }
    }
}
