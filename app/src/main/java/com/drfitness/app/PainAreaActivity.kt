package com.drfitness.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drfitness.app.databinding.ActivityPainAreaBinding

class PainAreaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPainAreaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPainAreaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button listener
        binding.buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Set up listeners for each pain area
        setupButtonClickListeners()
    }

    private fun setupButtonClickListeners() {
        binding.buttonShoulderPain.setOnClickListener { onPainAreaSelected("Shoulder") }
        binding.buttonNeckPain.setOnClickListener { onPainAreaSelected("Neck") }
        binding.buttonBackPain.setOnClickListener { onPainAreaSelected("Back") }
        binding.buttonKneePain.setOnClickListener { onPainAreaSelected("Knee") }
        binding.buttonJointPain.setOnClickListener { onPainAreaSelected("Joints") }
    }

    private fun onPainAreaSelected(painArea: String) {
        // TODO: This is where we will eventually show a list of exercises
        // for the selected pain area, similar to the workout details screen.
        // For now, we can just show a temporary message.
        android.widget.Toast.makeText(this, "Selected: $painArea Pain", android.widget.Toast.LENGTH_SHORT).show()
    }
}