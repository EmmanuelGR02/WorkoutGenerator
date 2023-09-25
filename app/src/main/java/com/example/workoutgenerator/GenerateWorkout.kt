package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workoutgenerator.databinding.FragmentGenerateWorkoutBinding
import java.util.Random

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
        // array list containing different chest workouts
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
        // array list containing back Workouts
        val backWorkouts = arrayListOf(
            "Deadlifts", "Pull-Ups", "Bent-Over Rows", "Lat Pulldowns", "T-Bar Rows",
            "Seated Cable Rows", "Face Pulls", "Single-Arm Dumbbell Rows", "Chin-Ups",
            "Deadlift Variations (Sumo, Romanian, etc.)", "Hyperextensions", "Barbell Shrugs",
            "Cable Face Pulls", "Wide-Grip Pull-Ups", "Inverted Rows", "Lateral Pull-Downs",
            "Meadows Rows", "Pull-Up Variations (Weighted, Assisted, etc.)", "Machine Rows",
            "Renegade Rows", "Kettlebell Swings", "Farmer's Walks", "Good Mornings",
            "Bulgarian Split Squats", "Rope Climbs", "Reverse Flyes", "Cable Rows",
            "Smith Machine Rows", "Seated Reverse Flyes", "Band Pull-Aparts"
        )


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
        // arrayList containing different leg workouts
        val legWorkouts = arrayListOf(
            "Squats", "Deadlifts", "Leg Press", "Lunges", "Step-Ups",
            "Hack Squats", "Romanian Deadlifts", "Bulgarian Split Squats", "Calf Raises",
            "Leg Extensions", "Glute Bridges", "Box Jumps", "Sumo Squats",
            "Front Squats", "Good Mornings", "Kettlebell Swings", "Wall Sits",
            "Pistol Squats", "Hamstring Curls", "Goblet Squats", "Sissy Squats",
            "Barbell Lunges", "Donkey Calf Raises", "Seated Leg Curls", "Smith Machine Squats",
            "Cable Pull-Throughs", "Walking Lunges", "Single-Leg Press", "Leg Raises",
            "Leg Abduction Machine"
        )

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
        // array lists containing different bicep, triceps, and shoulder workouts
        val bicepWorkouts = arrayListOf(
            "Barbell Curls", "Dumbbell Curls", "Hammer Curls", "Preacher Curls", "Concentration Curls",
            "Spider Curls", "Cable Curls", "EZ Bar Curls", "Reverse Curls", "21s",
            "Incline Dumbbell Curls", "Zottman Curls", "Cross Body Curls", "Scott Curls", "Machine Curls",
            "Resistance Band Curls", "Isometric Curls", "Plate Curls", "Close-Grip Pull-Ups", "Chin-Ups",
            "Alternating Dumbbell Curls", "Bicep Blaster Curls", "21s with Resistance Bands", "Body Drag Curls", "Static Hold Curls",
            "Wrist Curls", "Preacher Hammer Curls", "Drag Curls", "Concentration Curls on Machine", "Cable Hammer Curls",
            "Standing Barbell Curls", "Cheat Curls", "Machine Preacher Curls", "Pulley Curls", "Scott Curl Machine",
            "Dumbbell Hammer Curls", "Bicep Curl Machine", "Cable Preacher Curls", "Resistance Band Hammer Curls", "Isometric Hammer Curls"
        )
        val tricepsWorkouts = arrayListOf(
            "Tricep Dips", "Tricep Pushdowns", "Skull Crushers", "Close-Grip Bench Press", "Overhead Tricep Extensions",
            "Diamond Push-Ups", "Rope Pushdowns", "Tricep Kickbacks", "Bench Dips", "French Press",
            "Single-Arm Tricep Pushdowns", "Reverse Grip Tricep Pushdowns", "Tricep Bench Dips", "Kneeling Tricep Extensions", "Lying Tricep Extensions",
            "Cable Tricep Extensions", "Tricep Pull-Ups", "Bodyweight Tricep Extensions", "Dumbbell Tricep Extensions", "Tricep Cable Kickbacks",
            "Bent-Over Dumbbell Tricep Extensions", "Close-Grip Push-Ups", "Tricep Taps", "Close-Grip Dumbbell Press", "Skull Crusher Machine",
            "V-Bar Pushdowns", "Plate Tricep Extensions", "Lying Tricep Press", "Cable Overhead Tricep Extensions", "Reverse Grip Tricep Push-Ups",
            "Close-Grip Smith Machine Bench Press", "Tricep Machine", "Cable Rope Overhead Tricep Extensions", "Cable Lying Tricep Extensions", "Tricep Isolation Machine"
        )
        val shoulderWorkouts = arrayListOf(
            "Military Press", "Dumbbell Shoulder Press", "Arnold Press", "Push Press", "Seated Dumbbell Press",
            "Lateral Raises", "Front Raises", "Rear Delt Raises", "Upright Rows", "Shrugs",
            "Face Pulls", "Barbell Shrugs", "Clean and Press", "Cuban Press", "Lateral Raise Machine",
            "Machine Shoulder Press", "Landmine Press", "Dumbbell Shrugs", "Smith Machine Press", "Behind-the-Neck Press",
            "Bent-Over Lateral Raises", "Machine Rear Delt Rows", "Barbell High Pulls", "Kettlebell Press", "Handstand Push-Ups",
            "Standing Military Press", "Dumbbell Upright Rows", "Seated Military Press", "Plate Front Raises", "Cable Lateral Raises",
            "Dumbbell Clean and Press", "Single-Arm Dumbbell Press", "Reverse Pec Deck Flyes", "Kettlebell Swings", "Cable Face Pulls",
            "Machine Shrugs", "Smith Machine Shrugs", "Lateral Raise Machine", "Single-Arm Landmine Press", "Machine Lateral Raises"
        )

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
        val cardioWorkouts = arrayListOf(
            "Running", "Cycling", "Swimming", "Jumping Rope", "High-Intensity Interval Training (HIIT)",
            "Rowing", "Elliptical Trainer", "Stair Climbing", "Kickboxing", "Dancing",
            "Aerobics", "Sprinting", "Boxing", "CrossFit", "Power Walking",
            "Hiking", "Skating (Rollerblading or Ice Skating)", "Jumping Jacks", "Burpees", "Circuit Training",
            "Tabata Workouts", "Sled Pushes", "Battle Ropes", "Mountain Climbers", "Sprints with Resistance Bands",
            "Shadow Boxing", "Kick Sprints", "Swimming Laps", "Stationary Bike Intervals", "Cardio Kickboxing",
            "Zumba", "Bodyweight Circuits", "Jumping Squats", "Mountain Biking", "Rock Climbing",
            "Plyometric Exercises", "Racquet Sports (e.g., Tennis, Badminton)", "Calisthenics", "Running Stairs", "Trail Running"
        )

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
        // list containing ab workouts
        val abWorkouts = arrayListOf(
            "Crunches", "Leg Raises", "Russian Twists", "Planks", "Bicycle Crunches",
            "Hanging Leg Raises", "Mountain Climbers", "Flutter Kicks", "Sit-Ups", "Side Planks",
            "V-Ups", "Toe Touches", "Lying Leg Raises", "Oblique Crunches", "Scissor Kicks",
            "Ab Wheel Rollouts", "Reverse Crunches", "Jackknife Sit-Ups", "Dead Bugs", "Standing Cable Crunches",
            "Medicine Ball Slams", "L-Sit Holds", "Hanging Knee Raises", "Windshield Wipers", "Stability Ball Rollouts",
            "Long Arm Crunches", "Plank Variations (e.g., Spiderman Planks)", "Cable Woodchoppers", "Bosu Ball Exercises", "Knee-to-Elbow Planks",
            "Russian Twist with Medicine Ball", "Captain's Chair Leg Raises", "Seated Leg Tucks", "Cable Crunch Twists", "Hanging Windshield Wipers",
            "Stir the Pot Planks", "Decline Sit-Ups", "Rope Crunches", "Dragon Flags", "Pulse Ups"
        )

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

    // generate random numbers. A parameter will determine how many random numbers are needed
    // depending on the duration of the workout
    private fun generateRandNums(arrayLen: Int, randNums: Int) {
        for ()
           val rand = Random().nextInt((randNums + 1) - 0) + 0
    }
}