package com.example.graphqldataapplication.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.api.ApolloResponse
import com.example.graphqldataapplication.R
import com.example.graphqldataapplication.databinding.ActivityProductBinding
import com.example.graphqldataapplication.networkdata.ApolloClient
import com.example.graphqldatapplication.AddProducerMutation
import kotlinx.coroutines.launch

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private lateinit var apolloClient: ApolloClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)



        binding.btnAddProducer.setOnClickListener {

            val name = binding.tvName.text.toString()
            val city = binding.tvCity.text.toString()

            if (validateInput(name, city)) {
                binding.pbProgressBar.visibility=View.VISIBLE


                lifecycleScope.launch {
                    val response = executeAddProducerMutation(name, city)

                    if(response.data?.addProducer != null) {
                        Log.i("Data", "onCreate: ${response.data?.addProducer}")
                        val intent = Intent(this@ProductActivity, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@ProductActivity, "Data Not Found", Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(this, "Name && City Is Not Valid ", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnSeeProducer.setOnClickListener {
            binding.pbProgressBar.visibility=View.VISIBLE
            val intent = Intent(this@ProductActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnSeeTea.setOnClickListener {
            binding.pbProgressBar.visibility=View.VISIBLE
            val intent = Intent(this@ProductActivity, TeasActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        binding.pbProgressBar.visibility=View.GONE
    }

    override fun onPause() {
        super.onPause()
        binding.tvTeaId.text?.clear()
        binding.tvTeaName.text?.clear()
        binding.tvTeasDesc.text?.clear()
        binding.tvTeasPrice.text?.clear()
        binding.tvName.text?.clear()
        binding.tvCity.text?.clear()
    }

    override fun onRestart() {
        super.onRestart()
        binding.pbProgressBar.visibility=View.GONE

    }

    private suspend fun executeAddProducerMutation(
        name: String,
        city: String
    ): ApolloResponse<AddProducerMutation.Data> {
        val addProducer = AddProducerMutation(name, city)
        apolloClient = ApolloClient
        return apolloClient.apolloClient().mutation(addProducer).execute()

    }

    private fun validateInput(name: String, city: String): Boolean {
        return name.isNotEmpty() && city.isNotEmpty()
    }
}