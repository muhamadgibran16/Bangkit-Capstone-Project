package com.example.donorgo.activity.edit_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donorgo.dataclass.RequestEditUserProfile
import com.example.donorgo.repository.ViewModelRepository

class EditProfileViewModel(private val mViewModelRepository: ViewModelRepository) : ViewModel() {

    val message: LiveData<String?> = mViewModelRepository.message

    val isLoading: LiveData<Boolean> = mViewModelRepository.isLoading

    val isError: LiveData<Boolean> = mViewModelRepository.isError

    fun editUserProfile(request: RequestEditUserProfile, token: String) {
        mViewModelRepository.editUserProfile(request, token)
    }

}