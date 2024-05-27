package com.book.myapplication.GlobalState

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DarkModeData(private val context: Context) {
    companion object {
        private val IS_DARK_MODE_DATA = stringPreferencesKey("darkModeData")
    }

    val getDarkModeData : Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[IS_DARK_MODE_DATA] ?: "0"
        }

    suspend fun setDarkModeData(data: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE_DATA] = data
        }
    }
}