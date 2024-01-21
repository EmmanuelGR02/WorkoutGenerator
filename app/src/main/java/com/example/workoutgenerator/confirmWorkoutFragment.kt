package com.example.workoutgenerator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.workoutgenerator.databinding.FragmentConfirmWorkoutBinding

class ConfirmWorkoutFragment : Fragment() {
    private lateinit var binding: FragmentConfirmWorkoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmWorkoutBinding.inflate(inflater, container, false)

        val workoutContainer = binding.workoutContainer
        val tempWorkoutList = ArrayList<String>()


        Database.getInstance().getTempGenerateWorkout(currentUser) { tempWorkout ->
            Log.e("jshf", "$tempWorkout")
            tempWorkoutList.clear()
            tempWorkoutList.addAll(tempWorkout)

            fetchAndAddTempWorkouts(tempWorkoutList, workoutContainer)
        }

        // back button logic
        val backBtn = binding.backButton
        backBtn?.setOnClickListener {
            val fragment = GenerateWorkout()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        return binding.root
    }

    @SuppressLint("MissingInflatedId")
    private fun fetchAndAddTempWorkouts(
        tempWorkouts : ArrayList<String>,
        container : LinearLayout
    ) {
        // Clear existing views outside the loop
        container.removeAllViews()

        val inflater = LayoutInflater.from(requireContext())

        for (workout in tempWorkouts) {
            val workoutLayout = inflater.inflate(R.layout.regen_workout_item, container, false)

            val workoutView = workoutLayout.findViewById<TextView>(R.id.workoutNameTextView)
            workoutView.text = workout
            container.addView(workoutLayout)
        }

    }
}
