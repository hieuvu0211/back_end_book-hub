package com.book.myapplication.GlobalState

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.book.myapplication.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserData(private val context:Context) {
    companion object{
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "userLogin")
        val IS_USER_LOGIN = stringPreferencesKey("user_login")
    }
    val getDataUserFromLocal : Flow<User?> = context.dataStore.data
        .map { preferences  ->
            val userDataString = preferences[IS_USER_LOGIN] ?: return@map null
            Gson().fromJson(userDataString, User::class.java)
        }

    suspend fun SetDataUserInLocal(data: User?) {
        val dataString = Gson().toJson(data)
        context.dataStore.edit { preferences ->
            preferences[IS_USER_LOGIN] = dataString
        }
    }
}