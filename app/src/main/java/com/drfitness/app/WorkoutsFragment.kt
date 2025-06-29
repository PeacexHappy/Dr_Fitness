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

        // 1. Prepare the data for the screen
        val screenData = buildScreenData()

        // 2. Set up the main RecyclerView
        val adapter = WorkoutsAdapter(screenData)

        // THE FIX IS HERE: Use the correct ID from your XML file
        binding.workoutsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.workoutsRecyclerView.adapter = adapter
    }

    // THIS IS THE CORRECTED FUNCTION
    private fun buildScreenData(): List<WorkoutScreenItem> {
        // These URLs are from Pexels and are known to work in apps without special configs.
        val featured = listOf(
            FeaturedWorkout(
                "New: Advanced Abs",
                "https://images.pexels.com/photos/1431282/pexels-photo-1431282.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
            ),
            FeaturedWorkout(
                "Popular: Leg Day",
                "https://images.pexels.com/photos/2261477/pexels-photo-2261477.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
            )
        )

        val categories = listOf(
            WorkoutCategory(
                "Upper Body",
                "https://images.pexels.com/photos/3768916/pexels-photo-3768916.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
            ),
            WorkoutCategory(
                "Lower Body",
                "https://images.pexels.com/photos/703014/pexels-photo-703014.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
            ),
            WorkoutCategory(
                "Core",
                "https://images.pexels.com/photos/4752861/pexels-photo-4752861.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
            ),
            WorkoutCategory(
                "Cardio",
                "https://images.pexels.com/photos/3621168/pexels-photo-3621168.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
            )
        )

        // Assemble the final list for the adapter
        return listOf(
            WorkoutScreenItem.Header("Featured Workouts"),
            WorkoutScreenItem.FeaturedCarousel(featured),
            WorkoutScreenItem.Header("Categories"),
            WorkoutScreenItem.CategoryGrid(categories)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}