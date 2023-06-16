package com.example.donorgo.activity.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.donorgo.dataclass.ItemHistory
import com.example.donorgo.repository.ViewModelRepository

class HistoryViewModel(private val mViewModelRepository: ViewModelRepository) : ViewModel() {

    val messageHistory: LiveData<String?> = mViewModelRepository.message

    val listHistory: LiveData<List<ItemHistory>?> = mViewModelRepository.listHistory

    val isLoading: LiveData<Boolean> = mViewModelRepository.isLoading

    val isError: LiveData<Boolean> = mViewModelRepository.isError

    fun fetchHistoryData(token: String) {
        mViewModelRepository.fetchHistoryData(token)
    }

}