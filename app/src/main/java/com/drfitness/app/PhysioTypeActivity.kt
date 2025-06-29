package com.drfitness.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drfitness.app.databinding.ActivityPhysioTypeBinding

class PhysioTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhysioTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhysioTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Make the back button go to the previous screen
        binding.buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Prepare the "Daily Pain Relief" card to be clicked
        binding.cardDailyRelief.setOnClickListener {
            val intent = Intent(this, ReliefCategoryActivity::class.java)
            startActivity(intent)
        }

        // Prepare the "Physiotherapy Exercises" card to be clicked
        binding.cardPhysioExercises.setOnClickListener {
            val intent = Intent(this, PainAreaActivity::class.java)
            startActivity(intent)
        }
    }
}