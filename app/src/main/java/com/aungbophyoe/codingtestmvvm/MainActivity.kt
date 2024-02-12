package com.aungbophyoe.codingtestmvvm

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.aungbophyoe.codingtestmvvm.databinding.ActivityMainBinding
import com.aungbophyoe.core.ViewBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity (override val layoutRes: Int = R.layout.activity_main) : ViewBindingActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController
    private lateinit var navGraph : NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.apply {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController

            val graphInflater = navHostFragment.navController.navInflater
            navGraph = graphInflater.inflate(R.navigation.app_navigation)
            navController = navHostFragment.navController
            navController.graph = navGraph
        }
    }
}