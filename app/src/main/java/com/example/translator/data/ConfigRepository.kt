package com.example.translator.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore(name = "display_settings")

class ConfigRepository(
    private val context: Context
) {
     companion object {
        val CONFIG_LAYOUT = stringPreferencesKey("config_layout")
        const val TAG = "ConfigRepo"
    }

    open val configLayout: Flow<Option> = context.dataStore.data
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
                Option.ENGLISH.value -> Option.ENGLISH
                Option.MONGOLIA.value -> Option.MONGOLIA
                else -> Option.BOTH
            }
        }
    suspend fun saveSettingPreference(configLayout: Option) {
        context.dataStore.edit { preferences ->
            preferences[CONFIG_LAYOUT] = configLayout.value
        }
    }

}