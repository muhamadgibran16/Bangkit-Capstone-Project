package com.example.donorgo.activity.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donorgo.dataclass.RequestEditUserProfile
import com.example.donorgo.dataclass.RequestRegister
import com.example.donorgo.dataclass.UserProfileData
import com.example.donorgo.repository.ViewModelRepository
import okhttp3.MultipartBody

class ProfileViewModel(private val mViewModelRepository: ViewModelRepository) : ViewModel() {

    val messagePhoto: LiveData<String?> = mViewModelRepository.message

    val userProfile: LiveData<UserProfileData> = mViewModelRepository.userProfile

    val photoProfile: LiveData<String> = mViewModelRepository.photoProfile

    val isLoading: LiveData<Boolean> = mViewModelRepository.isLoading

    val isError: LiveData<Boolean> = mViewModelRepository.isError

    fun getUserProfile(token: String) {
        mViewModelRepository.getDataUserProfile(token)
    }

    fun editUserProfile(request: RequestEditUserProfile, token: String) {
        mViewModelRepository.editUserProfile(request, token)
    }

}