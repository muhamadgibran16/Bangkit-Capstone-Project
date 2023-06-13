package com.example.donorgo.activity.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class SelectImageViewModel: ViewModel() {
    private val _isViewPhotoIsFill = MutableLiveData<Boolean>()
    val isViewPhotoIsFill: LiveData<Boolean> = _isViewPhotoIsFill

    private val _thisIsFilePhoto = MutableLiveData<File?>()
    val thisIsFilePhoto: LiveData<File?> = _thisIsFilePhoto

    fun setConditionOfViewPhoto(value: Boolean) {
        _isViewPhotoIsFill.value = value
    }

    fun setMyFilePhoto(value: File?) {
        _thisIsFilePhoto.value = value
    }

    fun getHashCode(): Int {
        return System.identityHashCode(this)
    }
}