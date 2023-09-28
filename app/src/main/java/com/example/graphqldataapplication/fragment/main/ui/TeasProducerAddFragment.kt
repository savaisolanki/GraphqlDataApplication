package com.example.graphqldataapplication.fragment.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.graphqldataapplication.R
import com.example.graphqldataapplication.databinding.FragmentTeasProducerAddBinding
import com.example.graphqldataapplication.fragment.main.mainfactory.MainViewModelFactory
import com.example.graphqldataapplication.fragment.main.repository.MainRepository
import com.example.graphqldataapplication.fragment.main.viewmodel.MainViewModel
import com.example.graphqldataapplication.utils.BaseResponse


class TeasProducerAddFragment : Fragment() {

    private lateinit var binding: FragmentTeasProducerAddBinding
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainRepository = MainRepository()

        mainViewModel = ViewModelProvider(
            this, MainViewModelFactory(mainRepository)
        )[MainViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_teas_producer_add, container, false)

        setLiveDataObservers()


        binding.btnSeeTea.setOnClickListener {
            val action =
                TeasProducerAddFragmentDirections.actionTeasProducerAddFragmentToTeasFragment()
            findNavController().navigate(action)
        }

        binding.btnSeeProducer.setOnClickListener {
            val action =
                TeasProducerAddFragmentDirections.actionTeasProducerAddFragmentToProducerFragment()
            findNavController().navigate(action)
        }

        binding.btnAddProducer.setOnClickListener {
            validation()
        }

        return binding.root
    }


    private fun validation(): Boolean {

        when {
            binding.etName.text.isNullOrEmpty() -> {
                binding.etName.error = "Please Enter  Name"
                binding.etName.requestFocus()
                return false

            }

            binding.etCity.text.isNullOrEmpty() -> {
                binding.etCity.error = "Please Enter City"
                binding.etCity.requestFocus()
                return false
            }


            else -> {

                mainViewModel.getProducerAdd(
                    name = binding.etName.text.toString(),
                    city = binding.etCity.text.toString(),
                )

            }
        }
        return true
    }


    private fun setLiveDataObservers() {

        mainViewModel.mainResponseLiveData.observe(viewLifecycleOwner) { response ->
            if (!response.hasBeenHandled) {
                response.getContentIfNotHandled()?.let {
                    when (it) {
                        is BaseResponse.Loading -> {
                            binding.pbProgressBar.visibility = View.VISIBLE
                        }

                        is BaseResponse.Success -> {
                            binding.pbProgressBar.visibility = View.GONE

                            if (it.toString().isNotEmpty()) {
                                val action =
                                    TeasProducerAddFragmentDirections.actionTeasProducerAddFragmentToProducerFragment()
                                findNavController().navigate(action)

                                binding.etName.text?.clear()
                                binding.etCity.text?.clear()
                            }
                        }

                        is BaseResponse.Error -> {
                            binding.pbProgressBar.visibility = View.GONE
                        }
                    }
                }

            }
        }
    }

}