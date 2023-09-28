package com.example.workoutgenerator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workoutgenerator.databinding.FragmentProfileBinding
import com.example.workoutgenerator.databinding.FragmentWorkoutGeneratorBinding

class WorkoutGeneratorFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutGeneratorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutGeneratorBinding.inflate(inflater, container, false)
        val view = binding.root

        val profileBtn = binding.profileBtn
        val friendsBtn = binding.friendsBtn

        profileBtn.setOnClickListener {
            val fragment = ProfileFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        friendsBtn.setOnClickListener {
            val fragment = FriendsFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        // if the current user doesn't have a workout going on,
        // display a button that when clicked, it takes them to the generate workout fragment
        val generateWorkoutBtn = binding.generateWorkoutBtn
        generateWorkoutBtn?.setOnClickListener {
            val fragment = GenerateWorkout()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        return view
    }

}
