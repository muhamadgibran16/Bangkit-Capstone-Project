package com.example.donorgo.activity.donor

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donorgo.dataclass.*
import com.example.donorgo.repository.ViewModelRepository

class VoluntaryViewModel(private val mViewModelRepository: ViewModelRepository) : ViewModel() {

    val messageDonation: LiveData<String?> = mViewModelRepository.message

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

    fun postBloodDonation(request: RequestBloodDonation, token: String) {
        mViewModelRepository.postBloodDonation(request, token)
    }

}