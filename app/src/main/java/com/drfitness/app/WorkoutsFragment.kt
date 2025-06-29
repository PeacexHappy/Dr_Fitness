package com.drfitness.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.drfitness.app.databinding.FragmentWorkoutsBinding

class WorkoutsFragment : Fragment() {

    private var _binding: FragmentWorkoutsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Create Mock Data
        val screenData = createMockData()

        // 2. Setup the main RecyclerView
        val mainAdapter = WorkoutsAdapter(screenData)
        binding.workoutsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.workoutsRecyclerView.adapter = mainAdapter
    }

    private fun createMockData(): List<WorkoutScreenItem> {
        val featuredList = listOf(
            FeaturedWorkout(
                "New: Advanced Abs",
                "https://images.pexels.com/photos/4752861/pexels-photo-4752861.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "Advanced",
                "Abs"
            ),
            FeaturedWorkout(
                "Popular: Beginner Full Body",
                "https://images.pexels.com/photos/2261477/pexels-photo-2261477.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "Beginner",
                "Full Body"
            ),
            FeaturedWorkout(
                "Challenge: 30-Min Cardio",
                "https://images.pexels.com/photos/3621104/pexels-photo-3621104.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                "Intermediate",
                "Cardio"
            )
        )

        val categoryList = listOf(
            WorkoutCategory(
                "Upper Body",
                "https://images.pexels.com/photos/3768916/pexels-photo-3768916.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            ),
            WorkoutCategory(
                "Lower Body",
                "https://images.pexels.com/photos/1552249/pexels-photo-1552249.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            ),
            WorkoutCategory(
                "Core",
                "https://images.pexels.com/photos/3775131/pexels-photo-3775131.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            ),
            WorkoutCategory(
                "Cardio",
                "https://images.pexels.com/photos/863977/pexels-photo-863977.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            )
        )

        return listOf(
            WorkoutScreenItem.Header("Featured Workouts"),
            WorkoutScreenItem.FeaturedCarousel(featuredList),
            WorkoutScreenItem.Header("Categories"),
            WorkoutScreenItem.CategoryGrid(categoryList)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}