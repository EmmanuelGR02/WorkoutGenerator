package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.workoutgenerator.databinding.FragmentFriendsBinding
import com.example.workoutgenerator.databinding.FragmentProfileBinding

class FriendsFragment : Fragment() {
    private lateinit var binding: FragmentFriendsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val view = binding.root

        val profileBtn = binding.profileBtn
        val workoutBtn = binding.workoutBtn

        profileBtn.setOnClickListener {
            val fragment = ProfileFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        workoutBtn.setOnClickListener {
            val fragment = WorkoutGeneratorFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        return view
    }

}
