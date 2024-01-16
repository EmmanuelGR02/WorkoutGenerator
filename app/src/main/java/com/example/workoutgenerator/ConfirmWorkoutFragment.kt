package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workoutgenerator.databinding.FragmentConfirmWorkoutBinding

class ConfirmWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentConfirmWorkoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Create an instance of GenerateWorkout
        val generateWorkoutInstance = GenerateWorkout()

        // Call the generate function from GenerateWorkout
        generateWorkoutInstance.generate()
    }
}
