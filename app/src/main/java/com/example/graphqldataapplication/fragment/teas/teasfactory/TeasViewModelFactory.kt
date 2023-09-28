package com.example.graphqldataapplication.fragment.teas.teasfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.graphqldataapplication.fragment.teas.repository.TeasRepository
import com.example.graphqldataapplication.fragment.teas.viewmodel.TeasViewModel


@Suppress("UNCHECKED_CAST")
class TeasViewModelFactory(private val teasRepository: TeasRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TeasViewModel(teasRepository) as T
    }
}