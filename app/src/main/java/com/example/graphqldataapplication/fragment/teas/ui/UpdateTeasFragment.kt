package com.example.graphqldataapplication.fragment.teas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.graphqldataapplication.R
import com.example.graphqldataapplication.databinding.FragmentUpdateTeasBinding
import com.example.graphqldataapplication.fragment.teas.repository.TeasRepository
import com.example.graphqldataapplication.fragment.teas.teasfactory.TeasViewModelFactory
import com.example.graphqldataapplication.fragment.teas.viewmodel.TeasViewModel
import com.example.graphqldataapplication.utils.BaseResponse

class UpdateTeasFragment : Fragment() {

    private lateinit var binding: FragmentUpdateTeasBinding
    private lateinit var teasViewModel: TeasViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val teasRepository = TeasRepository()

        teasViewModel = ViewModelProvider(
            this, TeasViewModelFactory(teasRepository)
        )[TeasViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_update_teas, container, false)


        binding.etTeaId.isFocusable = false
        binding.etTeaId.isFocusableInTouchMode = false
        if (arguments != null) {
            binding.etTeaId.setText(arguments?.getString("id"))
        }
        binding.ivArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnUpdateTea.setOnClickListener {
            validation()
        }

        setLiveDataObservers()

        return binding.root
    }


    private fun validation(): Boolean {

        when {
            binding.etTeaName.text.isNullOrEmpty() -> {
                binding.etTeaName.error = "Please Enter Tea Name"
                binding.etTeaName.requestFocus()
                return false

            }

            binding.etTeasDesc.text.isNullOrEmpty() -> {
                binding.etTeasDesc.error = "Please Enter Tea Description"
                binding.etTeasDesc.requestFocus()
                return false
            }

            binding.etTeasPrice.text.isNullOrEmpty() -> {
                binding.etTeasPrice.error = "Please Enter Tea Price"
                binding.etTeasPrice.requestFocus()
                return false
            }

            !isPriceValid(binding.etTeasPrice.text.toString()) -> {
                binding.etTeasPrice.error = "Please Enter Valid Tea Price"
                binding.etTeasPrice.requestFocus()
                return false
            }

            else -> {

                teasViewModel.getUpdate(
                    id = binding.etTeaId.text.toString(),
                    name = binding.etTeaName.text.toString(),
                    desc = binding.etTeasDesc.text.toString(),
                    price = binding.etTeasPrice.text.toString().toDouble()
                )

            }
        }
        return true
    }

    private fun isPriceValid(priceText: String): Boolean {
        return priceText.toDoubleOrNull() != null
    }


    private fun setLiveDataObservers() {

        teasViewModel.teasUpdateResponseLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponse.Loading -> {
                    binding.pbProgressBar.visibility = View.VISIBLE

                }

                is BaseResponse.Success -> {
                    binding.pbProgressBar.visibility = View.GONE

                    if (response.data?.data?.updateTea?.id?.isNotEmpty() == true) {
                        Toast.makeText(
                            requireContext(),
                            "Tea Updated SuccessFully",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        findNavController().popBackStack()

                    }

                }

                is BaseResponse.Error -> {
                    binding.pbProgressBar.visibility = View.GONE
                }
            }

        }

    }


}