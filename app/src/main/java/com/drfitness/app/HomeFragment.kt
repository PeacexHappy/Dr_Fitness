// In com/drfitness/app/HomeFragment.kt
package com.drfitness.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.drfitness.app.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gymLink.setOnClickListener {


            // Find the BottomNavigationView in the parent activity (HomeActivity).
            val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.nav_view)

            // Programmatically set the selected item to the 'Workouts' tab.
            // The NavController in HomeActivity will automatically handle the navigation.
            bottomNav?.selectedItemId = R.id.navigation_workouts
        }
        binding.cardPhysiotherapy.setOnClickListener {
            // This triggers the new action we defined in the navigation graph.
            findNavController().navigate(R.id.action_navigation_home_to_painAreaFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}