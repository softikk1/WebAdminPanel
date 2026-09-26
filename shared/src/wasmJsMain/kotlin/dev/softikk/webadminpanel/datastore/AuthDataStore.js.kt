package dev.softikk.webadminpanel.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.WebLocalStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer

actual fun createDataStore(): DataStore<Preferences> = DataStoreFactory.create(
    storage = WebLocalStorage(
        serializer = PreferencesSerializer, name = dataStoreFileName
    )
)