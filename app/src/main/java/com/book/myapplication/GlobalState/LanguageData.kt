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
import java.util.Locale

val Context.dataStore by preferencesDataStore("setting")
class LanguageData(private val context: Context) {
    companion object {
        private val IS_LANGUAGE_DATA = stringPreferencesKey("language_data")
    }



    val getDataUserFromLocal : Flow<Locale> = context.dataStore.data
        .map { preferences ->
            val languageCode = preferences[IS_LANGUAGE_DATA] ?: Locale.getDefault().language
            Locale(languageCode)
        }

    suspend fun SetDataUserInLocal(data: Locale) {
        context.dataStore.edit { preferences ->
            preferences[IS_LANGUAGE_DATA] = data.language
        }
    }
}