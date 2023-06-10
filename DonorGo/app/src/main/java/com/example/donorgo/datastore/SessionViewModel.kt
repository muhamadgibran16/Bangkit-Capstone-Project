package com.example.donorgo.datastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.donorgo.datastore.SessionPreferences
import kotlinx.coroutines.launch

class SessionViewModel(private val pref: SessionPreferences) : ViewModel() {

    fun getStateSession(): LiveData<Boolean> {
        return pref.getStateSession().asLiveData()
    }

    fun saveStateSession(state: Boolean) {
        viewModelScope.launch {
            pref.saveStateSession(state)
        }
    }

    fun getUserUniqueID(): LiveData<String> {
        return pref.getUserUniqueId().asLiveData()
    }

    fun saveUserUniqueID(userID: String) {
        viewModelScope.launch {
            pref.saveUserUniqueId(userID)
        }
    }

    fun getUsername(): LiveData<String> {
        return pref.getUsername().asLiveData()
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            pref.saveUsername(username)
        }
    }

    fun getUserToken(): LiveData<String> {
        return pref.getUserToken().asLiveData()
    }

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            pref.saveUserToken(token)
        }
    }

    fun getIsAlreadyEntered(): LiveData<Boolean> {
        return pref.getIsAlreadyEntered().asLiveData()
    }

    fun saveIsAlreadyEntered(condition: Boolean) {
        viewModelScope.launch {
            pref.saveIsAlreadyEntered(condition)
        }
    }

//    fun getIsUnVerifiedOTP(): LiveData<Boolean> {
//        return pref.getIsUnVerifiedOTP().asLiveData()
//    }
//
//    fun saveIsUnVerifiedOTP(condition: Boolean) {
//        viewModelScope.launch {
//            pref.saveIsUnVerifiedOTP(condition)
//        }
//    }
//
//    fun getIsNoDataKTP(): LiveData<Boolean> {
//        return pref.getIsNoDataKTP().asLiveData()
//    }
//
//    fun saveIsNoDataKTP(condition: Boolean) {
//        viewModelScope.launch {
//            pref.saveIsNoDataKTP(condition)
//        }
//    }

}