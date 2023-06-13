package com.example.donorgo.activity.lastdonor

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donorgo.dataclass.RequestEditLastDonor
import com.example.donorgo.dataclass.RequestRegister
import com.example.donorgo.repository.ViewModelRepository

class LastDonorViewModel(private val mViewModelRepository: ViewModelRepository) : ViewModel() {

    val messageLastDonor: LiveData<String?> = mViewModelRepository.message

    val isLoading: LiveData<Boolean> = mViewModelRepository.isLoading

    val isError: LiveData<Boolean> = mViewModelRepository.isError

    fun setLastDonor(request: RequestEditLastDonor, token: String) {
        mViewModelRepository.setFirstLastDonor(request, token)
    }

}