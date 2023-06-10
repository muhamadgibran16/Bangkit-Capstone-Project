package com.example.donorgo.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donorgo.dataclass.DataLogin
import com.example.donorgo.dataclass.RequestLogin
import com.example.donorgo.dataclass.RequestResendOTP
import com.example.donorgo.repository.ViewModelRepository

class LoginViewModel(private val mViewModelRepository: ViewModelRepository) : ViewModel() {

    val messageLogin: LiveData<String?> = mViewModelRepository.message

    val dataSesion: LiveData<DataLogin> = mViewModelRepository.dataSesion

    val uniqueID: LiveData<String> = mViewModelRepository.uniqueID

    val isLoading: LiveData<Boolean> = mViewModelRepository.isLoading

    val isError: LiveData<Boolean> = mViewModelRepository.isError

    fun accessLoginUser(credential: RequestLogin) {
        mViewModelRepository.accessLoginUser(credential)
    }

    fun resendOTP(requestResendOTP: RequestResendOTP) {
        mViewModelRepository.resendOTP(requestResendOTP)
    }

}