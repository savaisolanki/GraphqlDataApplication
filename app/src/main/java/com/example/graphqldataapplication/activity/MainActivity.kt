package com.example.graphqldataapplication.activity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.example.graphqldataapplication.R
import com.example.graphqldataapplication.adapter.GraphQlAdapter
import com.example.graphqldataapplication.databinding.ActivityMainBinding
import com.example.graphqldataapplication.interfaceclass.RecyclerViewItemClick
import com.example.graphqldataapplication.networkdata.ApolloClient
import com.example.graphqldatapplication.DeleteProducerMutation
import com.example.graphqldatapplication.ProductListQuery
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var apolloClient: ApolloClient

    private lateinit var graphQlAdapter: GraphQlAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.pbProgressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val data = ApolloClient.apolloClient().query(ProductListQuery()).execute()
                Log.d("Data", "onCreateView: ${data.data?.producers}")

                val responseData = data.data?.producers

                binding.pbProgressBar.visibility = View.GONE

                apolloClient = ApolloClient
                graphQlAdapter = responseData?.let {
                    GraphQlAdapter(it as ArrayList<ProductListQuery.Producer?>, object : RecyclerViewItemClick {
                        override fun itemClick(position: Int) {

                            val builder = AlertDialog.Builder(this@MainActivity)
                            builder.setTitle("Delete Alert!!!!!")
                            builder.setMessage("Do You Want To Delete??")

                            builder.setPositiveButton("Yes") { _, _ ->
                                binding.pbProgressBar.visibility = View.VISIBLE

                                lifecycleScope.launch {
                                    val response =
                                        executeDeleteProducerMutation(responseData[position]?.id)

                                    if (response.data?.deleteProducer == true) {
                                        binding.pbProgressBar.visibility = View.GONE
                                        Log.i(TAG, "onCreate: ${response.data?.deleteProducer}")
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Producer Delete SuccessFully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        graphQlAdapter.removeItem(position)
                                        graphQlAdapter.notifyItemChanged(position)

                                    } else {

                                        binding.pbProgressBar.visibility = View.GONE
                                        Toast.makeText(
                                            this@MainActivity, "Data Not Found", Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }

                            builder.setNegativeButton("No") { _, _ ->
                                Toast.makeText(
                                    this@MainActivity, "No", Toast.LENGTH_SHORT
                                ).show()
                            }

                            builder.setNeutralButton("Maybe") { _, _ ->
                                Toast.makeText(
                                    this@MainActivity, "Maybe", Toast.LENGTH_SHORT
                                ).show()
                            }
                            builder.show()

                        }

                        override fun itemTeasEditClick(position: Int) {

                        }
                    })
                }!!
                binding.rvData.adapter = graphQlAdapter

            } catch (e: ApolloException) {
                Log.e("GraphQL Exception", "Error executing GraphQL query: ${e.message}")
            }
        }
    }


    private suspend fun executeDeleteProducerMutation(
        id: String?
    ): ApolloResponse<DeleteProducerMutation.Data> {
        val deleteProducer = DeleteProducerMutation(id.toString())
        apolloClient = ApolloClient
        return apolloClient.apolloClient().mutation(deleteProducer).execute()
    }
}