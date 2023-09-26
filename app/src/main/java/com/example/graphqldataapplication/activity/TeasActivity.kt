package com.example.graphqldataapplication.activity

import android.content.ContentValues
import android.content.Intent
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
import com.example.graphqldataapplication.adapter.GraphQlTeasAdapter
import com.example.graphqldataapplication.databinding.ActivityTeasBinding
import com.example.graphqldataapplication.interfaceclass.RecyclerViewItemClick
import com.example.graphqldataapplication.networkdata.ApolloClient
import com.example.graphqldatapplication.DeleteTeasMutation
import com.example.graphqldatapplication.TeasQuery
import kotlinx.coroutines.launch
import java.util.ArrayList

class TeasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeasBinding
    private lateinit var apolloClient: ApolloClient

    private lateinit var graphQlTeasAdapter: GraphQlTeasAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teas)
        binding.pbProgressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val data = ApolloClient.apolloClient().query(TeasQuery()).execute()
                Log.d("Data", "onCreateView: ${data.data?.teas}")

                val responseData = data.data?.teas?.filterNotNull()

                if (responseData != null) {
                    binding.pbProgressBar.visibility = View.GONE

                    apolloClient = ApolloClient
                    graphQlTeasAdapter = GraphQlTeasAdapter(responseData as ArrayList<TeasQuery.Tea>, object :
                        RecyclerViewItemClick {
                        override fun itemClick(position: Int) {

                            val builder = AlertDialog.Builder(this@TeasActivity)
                            builder.setTitle("Delete Alert!!!!!")
                            builder.setMessage("Do You Want To Delete??")

                            builder.setPositiveButton("Yes") { _, _ ->
                                binding.pbProgressBar.visibility = View.VISIBLE

                                lifecycleScope.launch {
                                    val response =
                                        executeDeleteTeasMutation(responseData[position].id)

                                    if (response.data?.deleteTea == true) {
                                        binding.pbProgressBar.visibility = View.GONE
                                        Log.i(
                                            ContentValues.TAG,
                                            "onCreate: ${response.data?.deleteTea}"
                                        )
                                        Toast.makeText(
                                            this@TeasActivity,
                                            "Tea Delete SuccessFully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        graphQlTeasAdapter.removeItem(position)
                                        graphQlTeasAdapter.notifyItemChanged(position)

                                    } else {

                                        binding.pbProgressBar.visibility = View.GONE
                                        Toast.makeText(
                                            this@TeasActivity,
                                            "Data Not Found",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }

                            builder.setNegativeButton("No") { _, _ ->
                                Toast.makeText(
                                    this@TeasActivity, "No", Toast.LENGTH_SHORT
                                ).show()
                            }

                            builder.setNeutralButton("Maybe") { _, _ ->
                                Toast.makeText(
                                    this@TeasActivity, "Maybe", Toast.LENGTH_SHORT
                                ).show()
                            }
                            builder.show()

                        }

                        override fun itemTeasEditClick(position: Int) {
                            val intent =
                                Intent(this@TeasActivity, UpdateTeasActivity::class.java)
                            intent.putExtra("teaId", responseData[position].id)
                            startActivity(intent)
                        }
                    })
                    binding.rvData.adapter = graphQlTeasAdapter
                }

            } catch (e: ApolloException) {
                Log.e("GraphQL Exception", "Error executing GraphQL query: ${e.message}")
            }
        }
    }

    private suspend fun executeDeleteTeasMutation(
        id: String?
    ): ApolloResponse<DeleteTeasMutation.Data> {
        val deleteProducer = DeleteTeasMutation(id.toString())
        apolloClient = ApolloClient
        return apolloClient.apolloClient().mutation(deleteProducer).execute()
    }

}