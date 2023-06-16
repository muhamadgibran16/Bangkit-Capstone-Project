package com.example.donorgo.activity.stock

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donorgo.dataclass.StockItem
import com.example.donorgo.repository.ViewModelRepository
import okhttp3.MultipartBody

class StockViewModel (private val mViewModelRepository: ViewModelRepository) : ViewModel() {

    val listStock: LiveData<List<StockItem>> = mViewModelRepository.listStock

    val messageStock: LiveData<String?> = mViewModelRepository.message

    val isLoading: LiveData<Boolean> = mViewModelRepository.isLoading

    val isError: LiveData<Boolean> = mViewModelRepository.isError

    fun fetchStockAllData(token: String) {
        mViewModelRepository.fetchStockAllData(token)
    }

    fun fetchStockDataByTypeId(token: String, idBloodType: Int) {
        mViewModelRepository.fetchStockDataByTypeId(token, idBloodType)
    }

    fun fetchStockDataByTypeIdAndRhesusId(token: String, idBloodType: Int, idRhesus: Int) {
        mViewModelRepository.fetchStockDataByTypeIdAndRhesusId(token, idBloodType, idRhesus)
    }

}