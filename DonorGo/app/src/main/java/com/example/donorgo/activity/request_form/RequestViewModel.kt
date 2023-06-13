package com.example.donorgo.activity.request_form

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donorgo.dataclass.*
import com.example.donorgo.repository.ViewModelRepository

class RequestViewModel(private val mViewModelRepository: ViewModelRepository) : ViewModel() {

    val messageRequest: LiveData<String?> = mViewModelRepository.message

    val isLoading: LiveData<Boolean> = mViewModelRepository.isLoading

    val isError: LiveData<Boolean> = mViewModelRepository.isError

    val dataProvince: LiveData<List<ProvinceItem>> = mViewModelRepository.dataProvince

    val dataCity: LiveData<List<CityItem>> = mViewModelRepository.dataCity

    val dataHospital: LiveData<List<HospitalItem>> = mViewModelRepository.dataHospital

    fun getAllProvince() {
        mViewModelRepository.getAllProvince()
    }

    fun getAllCity(provId: Int) {
        mViewModelRepository.getAllCity(provId)
    }

    fun getAllHospital(cityId: Int) {
        mViewModelRepository.getAllHospital(cityId)
    }

    fun postBloodRequest(request: RequestBloodRequest, token: String) {
        mViewModelRepository.postBloodRequest(request, token)
    }

}