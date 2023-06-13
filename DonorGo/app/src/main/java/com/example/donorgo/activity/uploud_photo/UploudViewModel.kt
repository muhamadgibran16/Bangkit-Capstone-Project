package com.example.donorgo.activity.uploud_photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donorgo.dataclass.UserProfileData
import com.example.donorgo.repository.ViewModelRepository
import okhttp3.MultipartBody

class UploudViewModel(private val mViewModelRepository: ViewModelRepository) : ViewModel() {

    val messageUploud: LiveData<String?> = mViewModelRepository.message

    val isLoading: LiveData<Boolean> = mViewModelRepository.isLoading

    val isError: LiveData<Boolean> = mViewModelRepository.isError

    fun uploudPhotoProfile(image: MultipartBody.Part, token: String) {
        mViewModelRepository.uploudPhotoProfile(image, token)
    }

    fun uploudKTP(userId: String, image: MultipartBody.Part) {
        mViewModelRepository.uploudKTP(userId, image)
    }

}