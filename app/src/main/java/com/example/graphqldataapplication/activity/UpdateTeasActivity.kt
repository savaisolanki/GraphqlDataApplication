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
import com.example.graphqldataapplication.databinding.ActivityUpdateTeasBinding
import com.example.graphqldataapplication.networkdata.ApolloClient
import com.example.graphqldatapplication.UpdateTeasMutation
import kotlinx.coroutines.launch

class UpdateTeasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTeasBinding

    private lateinit var apolloClient: ApolloClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_teas)
        val teaId = intent.getStringExtra("teaId")


        binding.tvTeaId.setText(teaId)
        binding.tvTeaId.isFocusable = false
        binding.tvTeaId.isFocusableInTouchMode = false

        binding.btnUpdateTea.setOnClickListener {

            val id = binding.tvTeaId.text.toString()
            val name = binding.tvTeaName.text.toString()
            val desc = binding.tvTeasDesc.text.toString()
            val price = binding.tvTeasPrice.text.toString()


            if (validateInput(name, desc) && isPriceValid(price)) {
                binding.pbProgressBar.visibility = View.VISIBLE


                lifecycleScope.launch {
                    val response =
                        executeUpdateTeaMutation(id, name, desc, price.toDouble())

                    if (response.data?.updateTea != null) {
                        Log.i("Data", "onCreate: ${response.data?.updateTea}")
                        val intent = Intent(this@UpdateTeasActivity, TeasActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        Toast.makeText(
                            this@UpdateTeasActivity,
                            "Update Tea SuccessFully",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            this@UpdateTeasActivity,
                            "Data Not Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            } else {
                Toast.makeText(
                    this,
                    "Name & Desc & Price Is Not Valid ",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        }
    }

    private fun validateInput(name: String, desc: String): Boolean {
        return name.isNotEmpty() && desc.isNotEmpty()
    }

    private suspend fun executeUpdateTeaMutation(
        id: String,
        name: String,
        desc: String,
        price: Double
    ): ApolloResponse<UpdateTeasMutation.Data> {
        val updateProducer = UpdateTeasMutation(id = id, name, desc, price)
        apolloClient = ApolloClient
        return apolloClient.apolloClient().mutation(updateProducer).execute()

    }

    override fun onResume() {
        super.onResume()
        binding.pbProgressBar.visibility = View.GONE
    }

    override fun onRestart() {
        super.onRestart()
        binding.pbProgressBar.visibility = View.GONE
    }


    override fun onPause() {
        super.onPause()
        binding.tvTeaId.text?.clear()
        binding.tvTeaName.text?.clear()
        binding.tvTeasDesc.text?.clear()
        binding.tvTeasPrice.text?.clear()

    }

    private fun isPriceValid(priceText: String): Boolean {
        return priceText.toDoubleOrNull() != null
    }

}