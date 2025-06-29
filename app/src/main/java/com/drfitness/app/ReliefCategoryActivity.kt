package com.drfitness.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drfitness.app.databinding.ActivityReliefCategoryBinding

class ReliefCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReliefCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReliefCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button listener
        binding.buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Set up listeners for each relief category
        setupButtonClickListeners()
    }

    private fun setupButtonClickListeners() {
        binding.buttonStrength.setOnClickListener { onCategorySelected("Strength") }
        binding.buttonAerobic.setOnClickListener { onCategorySelected("Aerobic") }
        binding.buttonBalance.setOnClickListener { onCategorySelected("Balance") }
        binding.buttonStretching.setOnClickListener { onCategorySelected("Stretching") }
        binding.buttonYoga.setOnClickListener { onCategorySelected("Yoga") }
    }

    private fun onCategorySelected(category: String) {
        // TODO: This is where we will eventually show a list of exercises
        // for the selected category. We can reuse the WorkoutDetailActivity for this.
        // For now, we'll show a temporary message.
        android.widget.Toast.makeText(this, "Selected Category: $category", android.widget.Toast.LENGTH_SHORT).show()
    }
}