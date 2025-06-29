package com.drfitness.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.drfitness.app.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the NavHostFragment from the layout
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as androidx.navigation.fragment.NavHostFragment

        // Get the NavController from the NavHostFragment
        val navController = navHostFragment.navController

        // This connects the BottomNavigationView to the NavController.
        // Tapping an icon in the bar will now navigate to the fragment with the matching ID.
        binding.navView.setupWithNavController(navController)
    }
}