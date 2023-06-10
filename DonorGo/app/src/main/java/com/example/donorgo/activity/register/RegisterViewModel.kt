package com.example.donorgo.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donorgo.dataclass.RequestRegister
import com.example.donorgo.repository.ViewModelRepository

class RegisterViewModel(private val mViewModelRepository: ViewModelRepository) : ViewModel() {

    val messageRegis: LiveData<String?> = mViewModelRepository.message

    val uniqueID: LiveData<String> = mViewModelRepository.uniqueID

    val isLoading: LiveData<Boolean> = mViewModelRepository.isLoading

    val isError: LiveData<Boolean> = mViewModelRepository.isError

    fun registerMyNewAccount(newAccount: RequestRegister) {
        mViewModelRepository.registerMyNewAccount(newAccount)
    }

}