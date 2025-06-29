package com.drfitness.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drfitness.app.databinding.ItemExerciseBinding

class ExerciseAdapter(private val exercises: List<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    // This class holds the view for a single item
    class ExerciseViewHolder(val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root)

    // Creates a new ViewHolder instance when the RecyclerView needs one
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    // Returns the total number of items in the list
    override fun getItemCount(): Int {
        return exercises.size
    }

    // Binds the data from an exercise object to the views in the ViewHolder
    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.binding.textViewExerciseName.text = exercise.name
        holder.binding.textViewSetsAndReps.text = "${exercise.sets} Sets x ${exercise.reps}"

        // TODO: Load the exercise.imageUrl into holder.binding.imageViewExercise using a library like Glide or Coil
    }
}