package com.drfitness.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.drfitness.app.databinding.ItemExerciseRowBinding

class ExerciseAdapter(private var exercises: List<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(val binding: ItemExerciseRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.binding.textExerciseName.text = exercise.name
        holder.binding.textSetsReps.text = "${exercise.sets} Sets x ${exercise.reps}"
        holder.binding.imageExercise.load(exercise.imageUrl) {
            crossfade(true)
            error(R.drawable.ic_launcher_background) // Placeholder in case of error
        }

        holder.itemView.setOnClickListener {
            // We now pass the entire list of exercises and the specific position that was clicked.
            val action = WorkoutListFragmentDirections.actionWorkoutListFragmentToExerciseDetailFragment(
                exercises = exercises.toTypedArray(), // Convert List to Array for navigation
                clickedPosition = position
            )
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = exercises.size

    // This function is how we update the list when the user taps a new filter chip.
    fun updateExercises(newExercises: List<Exercise>) {
        exercises = newExercises
        notifyDataSetChanged()
    }
}