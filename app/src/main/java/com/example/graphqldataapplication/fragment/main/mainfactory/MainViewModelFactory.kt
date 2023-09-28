package com.example.graphqldataapplication.fragment.main.mainfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.graphqldataapplication.fragment.main.repository.MainRepository
import com.example.graphqldataapplication.fragment.main.viewmodel.MainViewModel


@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val mainRepository: MainRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mainRepository) as T
    }
}