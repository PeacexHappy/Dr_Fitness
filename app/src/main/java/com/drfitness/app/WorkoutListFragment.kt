// In com/drfitness/app/WorkoutListFragment.kt
package com.drfitness.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.drfitness.app.databinding.FragmentWorkoutListBinding

class WorkoutListFragment : Fragment() {

    private var _binding: FragmentWorkoutListBinding? = null
    private val binding get() = _binding!!

    private val args: WorkoutListFragmentArgs by navArgs()
    private lateinit var exerciseAdapter: ExerciseAdapter // Our new adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkoutListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupRecyclerView()
        setupFilterChips()
    }

    private fun setupUI() {
        binding.textViewCategoryTitle.text = args.categoryName
        binding.backArrow.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setupRecyclerView() {
        // Initialize the adapter with an empty list. The filter will populate it.
        exerciseAdapter = ExerciseAdapter(listOf())
        binding.recyclerViewWorkoutPlans.adapter = exerciseAdapter
        // Make sure the item layout for this RecyclerView is set to item_exercise_row.xml
        // (This can be done in the XML with tools:listitem)
    }

    private fun setupFilterChips() {
        // This listener correctly handles the list of checked IDs
        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            // If the list is empty, it means the user deselected the chip.
            // In this case, we can choose to do nothing or clear the list.
            if (checkedIds.isEmpty()) {
                exerciseAdapter.updateExercises(listOf())
                return@setOnCheckedStateChangeListener
            }

            // Get the first (and only) selected chip's ID from the list.
            val selectedChipId = checkedIds.first()

            val selectedLevel = when (selectedChipId) {
                R.id.chip_intermediate -> "Intermediate"
                R.id.chip_advanced -> "Advanced"
                R.id.chip_beginner -> "Beginner"
                else -> null // We don't use the "All" chip from this screen's design
            }

            // If a level is selected, fetch the plan and update the list
            selectedLevel?.let { level ->
                val plan = WorkoutPlanRepository.getPlan(level = level, category = args.categoryName)
                exerciseAdapter.updateExercises(plan?.exercises ?: listOf())
            }
        }

        // Default to selecting the "Beginner" chip when the screen first loads
        binding.chipGroupFilter.check(R.id.chip_beginner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}