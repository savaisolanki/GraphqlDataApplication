package com.example.graphqldataapplication.fragment.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.graphqldataapplication.fragment.main.repository.MainRepository
import com.example.graphqldataapplication.utils.BaseResponse
import com.example.graphqldataapplication.utils.Event
import com.example.graphqldatapplication.AddProducerMutation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {


    private var mainResponseData: MutableLiveData<Event<BaseResponse<ApolloResponse<AddProducerMutation.Data>>>> =
        MutableLiveData()
    val mainResponseLiveData: LiveData<Event<BaseResponse<ApolloResponse<AddProducerMutation.Data>>>>
        get() = mainResponseData


    fun getProducerAdd(
        name: String,
        city: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            mainResponseData.postValue(Event(BaseResponse.Loading()))
            try {
                val responce = mainRepository.getProducerAdd(name = name, city = city)
                val response = responce.data

                if (response?.addProducer.toString().isNotEmpty()) {
                    mainResponseData.postValue(Event(BaseResponse.Success(responce)))
                    Log.d("MainResponseData Success:", response.toString())
                } else {
                    Log.d("MainResponseData Error:", response.toString())
                }
            } catch (e: Exception) {
                Log.d("MainResponseData Exception:", e.message.toString())
            }
        }

    }

}