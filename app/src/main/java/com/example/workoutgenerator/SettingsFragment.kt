package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workoutgenerator.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        // Send user back to log in fragment when clicked
        val logoutBtn = binding.logOutBtn
        logoutBtn?.setOnClickListener {
            val fragment = SignInFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        // back button logic
        val backBtn = binding.backButton
        backBtn?.setOnClickListener {
            val fragment = ProfileFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        return binding.root
    }

}