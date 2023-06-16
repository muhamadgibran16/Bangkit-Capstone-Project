package com.example.donorgo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.donorgo.database.LocalRoomDatabase
import com.example.donorgo.datastore.SessionPreferences
import com.example.donorgo.helper.AppExecutors
import com.example.donorgo.repository.ViewModelRepository
import com.example.donorgo.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): ViewModelRepository {
        val apiService = ApiConfig.getApiService()
        val database = LocalRoomDatabase.getDatabase(context)
        val appExecutors = AppExecutors()
        return ViewModelRepository.getInstance(apiService, database, appExecutors)
    }
    fun providePreferences(dataStore: DataStore<Preferences>): SessionPreferences {
        return SessionPreferences.getInstance(dataStore)
    }
}