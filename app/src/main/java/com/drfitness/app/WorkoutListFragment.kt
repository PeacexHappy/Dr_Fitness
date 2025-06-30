package com.drfitness.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.drfitness.app.databinding.FragmentWorkoutListBinding

class WorkoutListFragment : Fragment() {

    private var _binding: FragmentWorkoutListBinding? = null
    private val binding get() = _binding!!

    private val args: WorkoutListFragmentArgs by navArgs()
    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkoutListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupRecyclerView()

        // --- THIS IS THE NEW LOGIC ---
        // Check the navigation argument to decide whether to show the filters.
        if (args.showFilters) {
            binding.chipGroupFilter.isVisible = true
            setupFilterChips()
        } else {
            // If we're in the physio section, hide the filters and load the default plan.
            binding.chipGroupFilter.isVisible = false
            loadDefaultPhysioPlan()
        }
    }

    private fun setupUI() {
        binding.textViewCategoryTitle.text = args.categoryName
        binding.backArrow.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter(listOf())
        binding.recyclerViewWorkoutPlans.adapter = exerciseAdapter
    }

    private fun setupFilterChips() {
        // This function is now only called for Gym Workouts
        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isEmpty()) {
                exerciseAdapter.updateExercises(listOf())
                return@setOnCheckedStateChangeListener
            }
            val selectedChipId = checkedIds.first()
            val selectedLevel = when (selectedChipId) {
                R.id.chip_intermediate -> "Intermediate"
                R.id.chip_advanced -> "Advanced"
                else -> "Beginner" // Default to Beginner
            }
            loadExercisesForLevel(selectedLevel)
        }
        // Default to selecting the "Beginner" chip for gym workouts
        binding.chipGroupFilter.check(R.id.chip_beginner)
    }

    // NEW function for physio section
    private fun loadDefaultPhysioPlan() {
        // For physio, we assume there's one "Beginner" level plan.
        loadExercisesForLevel("Beginner")
    }

    // Helper function to load exercises to avoid code duplication
    private fun loadExercisesForLevel(level: String) {
        val plan = WorkoutPlanRepository.getPlan(level = level, category = args.categoryName)
        exerciseAdapter.updateExercises(plan?.exercises ?: listOf())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}