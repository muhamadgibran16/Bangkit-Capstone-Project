package com.example.donorgo.activity.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donorgo.dataclass.RequestResendOTP
import com.example.donorgo.dataclass.RequestVerify
import com.example.donorgo.repository.ViewModelRepository

class OtpViewModel(private val mViewModelRepository: ViewModelRepository) : ViewModel() {

    val messageOTP: LiveData<String?> = mViewModelRepository.message

    val isLoading: LiveData<Boolean> = mViewModelRepository.isLoading

    val isError: LiveData<Boolean> = mViewModelRepository.isError

    fun verifyOTP(dataToVerify: RequestVerify) {
        mViewModelRepository.verifyOTP(dataToVerify)
    }

    fun resendOTP(requestResendOTP: RequestResendOTP) {
        mViewModelRepository.resendOTP(requestResendOTP)
    }

}