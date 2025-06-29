package com.drfitness.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drfitness.app.databinding.ActivityBodyPartBinding

class BodyPartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBodyPartBinding
    private var selectedLevel: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyPartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the selected level passed from GymLevelActivity
        selectedLevel = intent.getStringExtra("USER_LEVEL")

        // Update the title with the selected level
        binding.textViewTitle.text = "$selectedLevel Workouts"

        // Back button listener
        binding.buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Set up listeners for each body part
        setupButtonClickListeners()
    }

    private fun setupButtonClickListeners() {
        binding.buttonFullBody.setOnClickListener { onBodyPartSelected("Full Body") }
        binding.buttonBicepTricep.setOnClickListener { onBodyPartSelected("Bicep/Triceps") }
        binding.buttonLegs.setOnClickListener { onBodyPartSelected("Legs") }
        binding.buttonShoulder.setOnClickListener { onBodyPartSelected("Shoulder") }
        binding.buttonBackMuscle.setOnClickListener { onBodyPartSelected("Back") }
        binding.buttonAbs.setOnClickListener { onBodyPartSelected("Abs") }
    }

    private fun onBodyPartSelected(bodyPart: String) {
        val intent = Intent(this, WorkoutDetailActivity::class.java)
        // Pass both the level (which we received earlier) and the newly selected body part
        intent.putExtra("USER_LEVEL", selectedLevel)
        intent.putExtra("BODY_PART", bodyPart)
        startActivity(intent)
    }
}