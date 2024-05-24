package com.book.myapplication.GlobalState

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.book.myapplication.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LanguageData(private val context: Context) {
    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "languageData")
        val IS_LANGUAGE_DATA = stringPreferencesKey("language_data")
    }
    val getDataUserFromLocal : Flow<String> = context.dataStore.data
        .map { preferences  ->
            preferences[IS_LANGUAGE_DATA] ?: ""
        }

    suspend fun SetDataUserInLocal(data: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_LANGUAGE_DATA] = data
        }
    }
}