// In com/drfitness/app/HomeActivity.kt
package com.drfitness.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.drfitness.app.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- THIS IS THE ROBUST AND CORRECT METHOD ---

        // 1. Find the NavHostFragment from the layout using its ID.
        // This is safer because it retrieves the fragment itself first.
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // 2. Get the NavController from the NavHostFragment.
        val navController: NavController = navHostFragment.navController

        // 3. This line connects the BottomNavigationView to the NavController.
        // It correctly handles clicks and icon highlighting.
        binding.navView.setupWithNavController(navController)
    }
}