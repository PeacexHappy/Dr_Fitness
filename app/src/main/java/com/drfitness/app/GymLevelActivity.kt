package com.drfitness.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drfitness.app.databinding.ActivityGymLevelBinding

class GymLevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGymLevelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGymLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button listener
        binding.buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Listeners for each level card
        binding.cardBeginner.setOnClickListener {
            navigateToBodyPartScreen("Beginner")
        }

        binding.cardIntermediate.setOnClickListener {
            navigateToBodyPartScreen("Intermediate")
        }

        binding.cardAdvanced.setOnClickListener {
            navigateToBodyPartScreen("Advanced")
        }
    }

    // Add this new function inside the class
    private fun navigateToBodyPartScreen(level: String) {
        val intent = Intent(this, BodyPartActivity::class.java)
        intent.putExtra("USER_LEVEL", level)
        startActivity(intent)
    }
}