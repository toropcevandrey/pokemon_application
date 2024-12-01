package com.example.pokemon_application.features.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.pokemon_application.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navView: BottomNavigationView
    private lateinit var  navHostFragment : NavHostFragment
    private lateinit var  navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        navView = findViewById(R.id.nav_view)
        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener{ item ->
            if(item.itemId != navController.currentDestination?.id){
                navController.popBackStack(item.itemId, inclusive = false, saveState = true)
                navController.navigate(item.itemId)
            }
            true
        }
    }
}