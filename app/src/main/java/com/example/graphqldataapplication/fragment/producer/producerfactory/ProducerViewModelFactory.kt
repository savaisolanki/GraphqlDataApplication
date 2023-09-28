package com.example.graphqldataapplication.fragment.producer.producerfactory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.graphqldataapplication.fragment.producer.repository.ProducerRepository
import com.example.graphqldataapplication.fragment.producer.viewmodel.ProducerViewModel
import com.example.graphqldataapplication.fragment.teas.repository.TeasRepository
import com.example.graphqldataapplication.fragment.teas.viewmodel.TeasViewModel


@Suppress("UNCHECKED_CAST")
class ProducerViewModelFactory(private val producerRepository: ProducerRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProducerViewModel(producerRepository) as T
    }
}