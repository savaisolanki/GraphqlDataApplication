package com.example.graphqldataapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.graphqldataapplication.R
import com.example.graphqldataapplication.databinding.ActivityGraphQlBinding

class GraphQlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGraphQlBinding
    var navController: NavController? = null
    private lateinit var navHostFrag: NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_graph_ql)

        navHostFrag =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFrag.navController


    }
}