package com.example.graphqldataapplication.fragment.teas.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.graphqldataapplication.R
import com.example.graphqldataapplication.databinding.FragmentTeasBinding
import com.example.graphqldataapplication.fragment.teas.adapter.TeasAdapter
import com.example.graphqldataapplication.fragment.teas.repository.TeasRepository
import com.example.graphqldataapplication.fragment.teas.teasfactory.TeasViewModelFactory
import com.example.graphqldataapplication.fragment.teas.viewmodel.TeasViewModel
import com.example.graphqldataapplication.interfaceclass.RecyclerViewItemClick
import com.example.graphqldataapplication.utils.BaseResponse
import com.example.graphqldatapplication.TeasQuery


class TeasFragment : Fragment() {

    private lateinit var binding: FragmentTeasBinding
    private lateinit var teasViewModel: TeasViewModel
    private lateinit var teasAdapter: TeasAdapter
    private var uniqueTeasList: ArrayList<TeasQuery.Tea?> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val teasRepository = TeasRepository()

        teasViewModel = ViewModelProvider(
            this, TeasViewModelFactory(teasRepository)
        )[TeasViewModel::class.java]


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_teas, container, false)

        setLiveDataObservers()
        teasViewModel.getTeas()

        return binding.root
    }





    @SuppressLint("NotifyDataSetChanged")
    private fun setLiveDataObservers() {
        teasViewModel.teasResponseLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponse.Loading -> {
                    binding.pbProgressBar.visibility = View.VISIBLE

                }

                is BaseResponse.Success -> {
                    binding.pbProgressBar.visibility = View.GONE

                    val teas = response.data?.data?.teas


                    Log.d("response", "setLiveDataObserversResponse: ${response.data?.data?.teas} ")

                    if (!teas.isNullOrEmpty()) {
                        val uniqueTeasMap = HashMap<String, TeasQuery.Tea>()

                        for (tea in teas) {
                            val teaId = tea?.id
                            if (teaId != null && teaId != "1") {
                                if (!uniqueTeasMap.containsKey(teaId)) {
                                    uniqueTeasMap[teaId] = tea
                                }
                            }
                        }

                        uniqueTeasList = ArrayList(uniqueTeasMap.values)

                        teasAdapter =
                            TeasAdapter(
                                uniqueTeasList,
                                object : RecyclerViewItemClick {
                                    override fun itemClick(position: Int) {
                                        val builder = AlertDialog.Builder(requireContext())
                                        builder.setTitle("Delete Alert!!!!!")
                                        builder.setMessage("Do You Want To Delete??")

                                        builder.setPositiveButton("Yes") { _, _ ->
                                            binding.pbProgressBar.visibility = View.VISIBLE

                                            uniqueTeasList.let {
                                                it[position]?.id?.let { it1 ->
                                                    teasViewModel.deleteTea(id = it1)
                                                }

                                            }

                                            teasAdapter.removeItem(position)

                                            Log.d("delete", "delete:${response.data.data?.teas} ")


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
                                        val selectedTea = uniqueTeasList[position]
                                        selectedTea?.id?.let {
                                            val bundle = Bundle()
                                            bundle.putString("id", it)
                                            findNavController().navigate(
                                                R.id.action_teasFragment_to_updateTeasFragment,
                                                bundle
                                            )
                                        }

                                    }
                                })
                        binding.rvData.adapter = teasAdapter
                    }
                }

                is BaseResponse.Error -> {
                    binding.pbProgressBar.visibility = View.GONE
                }
            }
        }

        teasViewModel.teasDeleteResponseLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponse.Loading -> {
                    binding.pbProgressBar.visibility = View.VISIBLE

                }

                is BaseResponse.Success -> {
                    binding.pbProgressBar.visibility = View.GONE

                    if (response.data?.data?.deleteTea == true) {

                        Log.d("delete", "deleteTeaResponse: ${response.data.data?.deleteTea} ")

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