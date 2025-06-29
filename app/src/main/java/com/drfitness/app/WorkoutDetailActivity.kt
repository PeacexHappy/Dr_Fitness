package com.drfitness.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.drfitness.app.databinding.ActivityWorkoutDetailBinding

class WorkoutDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from the previous screen
        val selectedLevel = intent.getStringExtra("USER_LEVEL")
        val selectedBodyPart = intent.getStringExtra("BODY_PART")

        if (selectedLevel == null || selectedBodyPart == null) {
            // Handle error case where data is missing
            Toast.makeText(this, "Workout information not found.", Toast.LENGTH_LONG).show()
            finish() // Close this activity
            return
        }

        // Set the title
        binding.textViewTitle.text = "$selectedLevel $selectedBodyPart"

        // Find the workout plan from our mock database
        val workoutPlan = WorkoutDatabase.getPlan(selectedLevel, selectedBodyPart)

        if (workoutPlan != null) {
            // If a plan is found, set up the RecyclerView
            val adapter = ExerciseAdapter(workoutPlan.exercises)
            binding.recyclerViewExercises.adapter = adapter
        } else {
            // If no plan is found for the combination
            Toast.makeText(this, "No workout plan available for this selection yet.", Toast.LENGTH_LONG).show()
        }

        // Back button listener
        binding.buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}