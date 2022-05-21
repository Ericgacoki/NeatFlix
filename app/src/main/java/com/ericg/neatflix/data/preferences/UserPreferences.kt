package com.ericg.neatflix.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** A calm reminder. This class is injected. Do not instantiate it directly.*/
class UserPreferences(private val context: Context) {
    private companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")
        val INCLUDE_ADULT_KEY = booleanPreferencesKey("include_adult")
    }

    val includeAdultFlow: Flow<Boolean?> = context.dataStore.data.map { prefs ->
        prefs[INCLUDE_ADULT_KEY] ?: true
    }
    suspend fun updateIncludeAdult(includeAdult: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[INCLUDE_ADULT_KEY] = includeAdult
        }
    }
}