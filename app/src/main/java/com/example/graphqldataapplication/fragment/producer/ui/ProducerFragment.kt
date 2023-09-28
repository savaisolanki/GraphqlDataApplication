package com.example.graphqldataapplication.fragment.producer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.graphqldataapplication.R
import com.example.graphqldataapplication.databinding.FragmentProducerBinding
import com.example.graphqldataapplication.fragment.producer.adapter.ProducerAdapter
import com.example.graphqldataapplication.fragment.producer.producerfactory.ProducerViewModelFactory
import com.example.graphqldataapplication.fragment.producer.repository.ProducerRepository
import com.example.graphqldataapplication.fragment.producer.viewmodel.ProducerViewModel
import com.example.graphqldataapplication.interfaceclass.RecyclerViewItemClick
import com.example.graphqldataapplication.utils.BaseResponse
import com.example.graphqldatapplication.ProductListQuery


class ProducerFragment : Fragment() {
    private lateinit var binding: FragmentProducerBinding
    private lateinit var producerViewModel: ProducerViewModel
    private lateinit var producerAdapter: ProducerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val producerRepository = ProducerRepository()

        producerViewModel = ViewModelProvider(
            this, ProducerViewModelFactory(producerRepository)
        )[ProducerViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_producer, container, false)
        setLiveDataObservers()
        producerViewModel.getProducer()
        return binding.root
    }


    private fun setLiveDataObservers() {

        producerViewModel.producerResponseLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponse.Loading -> {
                    binding.pbProgressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.pbProgressBar.visibility = View.GONE

                    if (!response.data?.data?.producers.isNullOrEmpty()) {

                        producerAdapter =
                            ProducerAdapter(response.data?.data?.producers as ArrayList<ProductListQuery.Producer?>,
                                object : RecyclerViewItemClick {
                                    override fun itemClick(position: Int) {
                                        val builder = AlertDialog.Builder(requireContext())
                                        builder.setTitle("Delete Alert!!!!!")
                                        builder.setMessage("Do You Want To Delete??")

                                        builder.setPositiveButton("Yes") { _, _ ->
                                            binding.pbProgressBar.visibility = View.VISIBLE

                                            (response.data.data?.producers as ArrayList<ProductListQuery.Producer?>)[position]?.id?.let {
                                                producerViewModel.deleteProducer(
                                                    it
                                                )
                                            }

                                            producerAdapter.removeItem(position)

                                        }

                                        builder.setNegativeButton("No") { _, _ ->
                                            Toast.makeText(
                                                requireContext(), "No", Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        builder.setNeutralButton("Maybe") { _, _ ->
                                            Toast.makeText(
                                                requireContext(), "Maybe", Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        builder.show()


                                    }

                                    override fun itemTeasEditClick(position: Int) {
                                    }
                                })
                        binding.rvData.adapter = producerAdapter

                    }

                }

                is BaseResponse.Error -> {
                    binding.pbProgressBar.visibility = View.GONE
                }
            }

        }

        producerViewModel.producerDeleteResponseLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponse.Loading -> {
                    binding.pbProgressBar.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.pbProgressBar.visibility = View.GONE

                    if (response.data?.data?.deleteProducer.toString().isNotEmpty()) {
                        Toast.makeText(
                            requireContext(), "Delete Tea Successfully", Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            requireContext(), "Delete Tea UnSuccessfully", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is BaseResponse.Error -> {
                    binding.pbProgressBar.visibility = View.GONE
                }
            }
        }
    }


}