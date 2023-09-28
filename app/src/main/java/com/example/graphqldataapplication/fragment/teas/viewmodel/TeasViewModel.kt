package com.example.graphqldataapplication.fragment.teas.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.graphqldataapplication.fragment.teas.repository.TeasRepository
import com.example.graphqldataapplication.utils.BaseResponse
import com.example.graphqldatapplication.DeleteTeasMutation
import com.example.graphqldatapplication.TeasQuery
import com.example.graphqldatapplication.UpdateTeasMutation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeasViewModel(private val teasRepository: TeasRepository) : ViewModel() {


    private var teasResponseData: MutableLiveData<BaseResponse<ApolloResponse<TeasQuery.Data>>> =
        MutableLiveData()
    val teasResponseLiveData: LiveData<BaseResponse<ApolloResponse<TeasQuery.Data>>>
        get() = teasResponseData


    fun getTeas() {
        viewModelScope.launch(Dispatchers.IO) {
            teasResponseData.postValue(BaseResponse.Loading())
            try {
                val responce = teasRepository.getTeas()
                val response = responce.data

                if (response?.teas?.isNotEmpty() == true) {
                    teasResponseData.postValue(BaseResponse.Success(responce))

                    Log.d("TeasResponseData Success:", response.toString())
                } else {
                    Log.d("TeasResponseData Error:", response.toString())
                }
            } catch (e: Exception) {
                Log.d("TeasResponseData Exception:", e.message.toString())
            }
        }

    }


    private var teasUpdateResponseData: MutableLiveData<BaseResponse<ApolloResponse<UpdateTeasMutation.Data>>> =
        MutableLiveData()
    val teasUpdateResponseLiveData: LiveData<BaseResponse<ApolloResponse<UpdateTeasMutation.Data>>>
        get() = teasUpdateResponseData


    fun getUpdate(
        id: String,
        name: String,
        desc: String,
        price: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            teasUpdateResponseData.postValue(BaseResponse.Loading())
            try {
                val responce =
                    teasRepository.updateTeas(id = id, name = name, desc = desc, price = price)
                val response = responce.data?.updateTea

                if (response?.id?.isNotEmpty() == true) {
                    teasUpdateResponseData.postValue(BaseResponse.Success(responce))
                    Log.d("TeasUpdateResponseData Success:", response.toString())
                } else {
                    Log.d("TeasUpdateResponseData Error:", response.toString())
                }
            } catch (e: Exception) {
                Log.d("TeasUpdateResponseData Exception:", e.message.toString())
            }
        }

    }


    private var teasDeleteResponseData: MutableLiveData<BaseResponse<ApolloResponse<DeleteTeasMutation.Data>>> =
        MutableLiveData()
    val teasDeleteResponseLiveData: LiveData<BaseResponse<ApolloResponse<DeleteTeasMutation.Data>>>
        get() = teasDeleteResponseData


    fun deleteTea(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            teasDeleteResponseData.postValue(BaseResponse.Loading())
            try {
                val responce = teasRepository.deleteTea(id = id)
                val response = responce.data?.deleteTea

                if (response.toString().isNotEmpty()) {
                    teasDeleteResponseData.postValue(BaseResponse.Success(responce))

                    Log.d("TeasDeleteResponseData Success:", response.toString())
                } else {
                    Log.d("TeasDeleteResponseData Error:", response.toString())
                }
            } catch (e: Exception) {
                Log.d("TeasDeleteResponseData Exception:", e.message.toString())
            }
        }

    }


}