package com.example.graphqldataapplication.fragment.producer.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.graphqldataapplication.fragment.producer.repository.ProducerRepository
import com.example.graphqldataapplication.fragment.teas.repository.TeasRepository
import com.example.graphqldataapplication.utils.BaseResponse
import com.example.graphqldatapplication.DeleteProducerMutation
import com.example.graphqldatapplication.DeleteTeasMutation
import com.example.graphqldatapplication.ProductListQuery
import com.example.graphqldatapplication.TeasQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProducerViewModel(private val producerRepository: ProducerRepository) : ViewModel() {


    private var producerResponseData: MutableLiveData<BaseResponse<ApolloResponse<ProductListQuery.Data>>> =
        MutableLiveData()
    val producerResponseLiveData: LiveData<BaseResponse<ApolloResponse<ProductListQuery.Data>>>
        get() = producerResponseData


    fun getProducer() {
        viewModelScope.launch(Dispatchers.IO) {
            producerResponseData.postValue(BaseResponse.Loading())
            try {
                val responce = producerRepository.getProducer()
                val response = responce.data

                if (response?.producers?.isNotEmpty() == true) {
                    producerResponseData.postValue(BaseResponse.Success(responce))
                    Log.d("TeasResponseData Success:", response.toString())
                } else {
                    Log.d("TeasResponseData Error:", response.toString())
                }
            } catch (e: Exception) {
                Log.d("TeasResponseData Exception:", e.message.toString())
            }
        }

    }


    private var producerDeleteResponseData: MutableLiveData<BaseResponse<ApolloResponse<DeleteProducerMutation.Data>>> =
        MutableLiveData()
    val producerDeleteResponseLiveData: LiveData<BaseResponse<ApolloResponse<DeleteProducerMutation.Data>>>
        get() = producerDeleteResponseData


    fun deleteProducer( id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            producerDeleteResponseData.postValue(BaseResponse.Loading())
            try {
                val responce = producerRepository.deleteProducer(id=id)
                val response = responce.data?.deleteProducer

                if (response.toString().isNotEmpty()) {
                    producerDeleteResponseData.postValue(BaseResponse.Success(responce))
                    Log.d("ProducerDeleteResponseData Success:", response.toString())
                } else {
                    Log.d("ProducerDeleteResponseData Error:", response.toString())
                }
            } catch (e: Exception) {
                Log.d("ProducerDeleteResponseData Exception:", e.message.toString())
            }
        }

    }




}