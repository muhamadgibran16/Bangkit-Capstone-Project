package com.example.storyapp.factory

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.donorgo.datastore.SessionPreferences
import com.example.donorgo.datastore.SessionViewModel
import com.example.donorgo.di.Injection

class SessionViewModelFactory(private val pref: SessionPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {
            return SessionViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: SessionViewModelFactory? = null

        @JvmStatic
        fun getInstance(dataStore: DataStore<Preferences>): SessionViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SessionViewModelFactory(Injection.providePreferences(dataStore))
            }.also {
                INSTANCE = it
            }
    }
}