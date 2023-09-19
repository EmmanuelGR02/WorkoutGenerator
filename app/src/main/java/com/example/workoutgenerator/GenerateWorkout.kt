package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workoutgenerator.databinding.FragmentGenerateWorkoutBinding

class GenerateWorkout : Fragment() {
    private lateinit var binding: FragmentGenerateWorkoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenerateWorkoutBinding.inflate(inflater, container, false)

        // take user back to workout generator fragment
        val bacBtn = binding.backButton
        bacBtn?.setOnClickListener {
            val fragment = WorkoutGeneratorFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        return binding.root
    }


    // generate chest workout
    fun generateChestWorkout(duration: String, bool: Boolean) {
        val chestWorkouts = arrayListOf(
            "Bench Press", "Incline Bench Press", "Decline Bench Press", "Dumbbell Bench Press",
            "Incline Dumbbell Bench Press", "Decline Dumbbell Bench Press", "Chest Flyes", "Dumbbell Flyes",
            "Incline Dumbbell Flyes", "Cable Flyes", "Pec Deck Flyes", "Push-Ups", "Wide-Grip Push-Ups",
            "Diamond Push-Ups", "Decline Push-Ups", "Weighted Push-Ups", "Chest Dips", "Weighted Chest Dips",
            "Machine Chest Press", "Smith Machine Bench Press", "Landmine Press", "Barbell Pullovers",
            "Dumbbell Pullovers", "Cable Crossovers", "Incline Cable Crossovers", "Dumbbell Squeeze Press",
            "Seated Chest Press Machine", "Chest Press Machine", "Incline Chest Press Machine",
            "Decline Chest Press Machine", "Hammer Strength Chest Press", "Machine Flyes", "Incline Machine Flyes",
            "Decline Machine Flyes", "Smith Machine Incline Bench Press", "Smith Machine Decline Bench Press",
            "Push-Up Variations (e.g., wide grip, close grip)", "Isometric Chest Squeezes", "Chest Pulsing",
            "Chest Stretching Exercises"
        )

        var randIntOne = 0
        var randIntTwo = 0

        if (duration == "short") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "medium") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "long") {
            if (bool) {

            } else if (!bool) {

            }
        }
    }


    // generate back workout
    fun generateBackWorkout(duration: String, bool: Boolean) {


        if (duration == "short") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "medium") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "long") {
            if (bool) {

            } else if (!bool) {

            }
        }
    }

    // generate legs workout
    fun generateLegWorkout(duration: String, bool: Boolean) {


        if (duration == "short") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "medium") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "long") {
            if (bool) {

            } else if (!bool) {

            }
        }
    }

    // generate arms workout
    fun generateArmWorkout(duration: String, bool: Boolean) {


        if (duration == "short") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "medium") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "long") {
            if (bool) {

            } else if (!bool) {

            }
        }
    }

    // generate cardio workout
    fun generateCardioWorkout(duration: String, bool: Boolean){


        if (duration == "short") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "medium") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "long") {
            if (bool) {

            } else if (!bool) {

            }
        }
    }

    // generate abs workout
    fun generateAbWorkout(duration: String, bool: Boolean) {


        if (duration == "short") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "medium") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "long") {
            if (bool) {

            } else if (!bool) {

            }
        }
    }
}