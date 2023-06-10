package com.example.donorgo.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getStateSession(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[STATE_KEY] ?: false
        }
    }

    suspend fun saveStateSession(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = state
        }
    }

    fun getUserUniqueId(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[UNIQUE_ID] ?: ""
        }
    }

    suspend fun saveUserUniqueId(userID: String) {
        dataStore.edit { preferences ->
            preferences[UNIQUE_ID] = userID
        }
    }

    fun getUsername(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USERNAME_KEY] ?: ""
        }
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    fun getUserToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    fun getIsAlreadyEntered(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[ALREADY_ENTERED] ?: false
        }
    }

    suspend fun saveIsAlreadyEntered(condition: Boolean) {
        dataStore.edit { preferences ->
            preferences[ALREADY_ENTERED] = condition
        }
    }

//    fun getIsUnVerifiedOTP(): Flow<Boolean> {
//        return dataStore.data.map { preferences ->
//            preferences[VERIFY_CONDITION] ?: false
//        }
//    }
//
//    suspend fun saveIsUnVerifiedOTP(condition: Boolean) {
//        dataStore.edit { preferences ->
//            preferences[VERIFY_CONDITION] = condition
//        }
//    }
//
//    fun getIsNoDataKTP(): Flow<Boolean> {
//        return dataStore.data.map { preferences ->
//            preferences[KTP_CONDITION] ?: false
//        }
//    }
//
//    suspend fun saveIsNoDataKTP(condition: Boolean) {
//        dataStore.edit { preferences ->
//            preferences[KTP_CONDITION] = condition
//        }
//    }

    companion object {
        @Volatile
        private var INSTANCE: SessionPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SessionPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SessionPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }

        private val STATE_KEY = booleanPreferencesKey("state_key")
        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val TOKEN_KEY = stringPreferencesKey("token_key")
        private val UNIQUE_ID = stringPreferencesKey("unique_id")
        private val ALREADY_ENTERED = booleanPreferencesKey("already_entered")
//        private val KTP_CONDITION = booleanPreferencesKey("ktp_condition")
//        private val VERIFY_CONDITION = booleanPreferencesKey("verify_condition")
    }
}