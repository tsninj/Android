package com.example.translator.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "settings")

class ConfigRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val CONFIG_LAYOUT = intPreferencesKey("config_layout")
        const val TAG = "ConfigRepo"
    }

    val configLayout: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            when(preferences[CONFIG_LAYOUT]){
                1 -> 1
                2 -> 2
                else -> 3
            }
        }
    suspend fun saveSettingPreference(configLayout: Int) {
        dataStore.edit { preferences ->
            preferences[CONFIG_LAYOUT] = configLayout
        }
    }
}