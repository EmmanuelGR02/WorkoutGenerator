package com.example.workoutgenerator

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.compose.ui.text.toUpperCase
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.example.workoutgenerator.databinding.FragmentGenerateWorkoutBinding
import java.util.Locale
import java.util.Random

class GenerateWorkout : Fragment() {
    private lateinit var binding: FragmentGenerateWorkoutBinding
    private val workoutsSelectedMax = 2
    private val blueWorkoutBtn: MutableList<Button> = mutableListOf()
    private var workoutBtnList: List<Button>? = null

    private val durationMax = 1
    private val blueDurationBtn: MutableList<Button> = mutableListOf()
    private var durationBtnList: List<Button>? = null

    // initialize buttons
    private lateinit var chestBtn: Button
    private lateinit var backBtn: Button
    private lateinit var legsBtn: Button
    private lateinit var armsBtn: Button
    private lateinit var cardioBtn: Button
    private lateinit var absBtn: Button
    private lateinit var shortBtn: Button
    private lateinit var mediumBtn: Button
    private lateinit var longBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenerateWorkoutBinding.inflate(inflater, container, false)

        // take user back to workout generator fragment
        val backButton = binding.backButton
        backButton?.setOnClickListener {
            val fragment = WorkoutGeneratorFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        // initialize the workout buttons
        chestBtn = binding.chestBtn
        backBtn = binding.backBtn
        legsBtn = binding.legsBtn
        armsBtn = binding.armsBtn
        cardioBtn = binding.cardioBtn
        absBtn = binding.absBtn
        // duration buttons
        shortBtn = binding.shortBtn
        mediumBtn = binding.mediumBtn
        longBtn = binding.longBtn

        workoutBtnList = listOf(chestBtn, backBtn, legsBtn, armsBtn, cardioBtn, absBtn)
        durationBtnList = listOf(shortBtn, mediumBtn, longBtn)

        // change background of workout colors when selected
        for (button in workoutBtnList!!) {
            button.setOnClickListener {
                changeWorkoutBgColor(button)
            }
        }

        // change bg color of duration buttons as needed
        for (button in durationBtnList!!) {
            button.setOnClickListener {
                changeDurationBgColor(button)
            }
        }

        var invalidSelection = binding.wrongSelection

        // call main fun to generate the workout
        val generateBtn = binding.generateBtn
        generateBtn?.setOnClickListener {
            val areSelected = areSelected()
            if (areSelected == -2) {
                invalidSelection.text = "You must select at least one workout!"
            } else if (areSelected == -1) {
                invalidSelection.text = "You must select the duration!"
            } else if (areSelected == 0) {
                invalidSelection.text = "You must select at least one workout, and the duration!"
            } else {
                generate()
            }
        }
        return binding.root
    }


    // main function to generate the workout
    private fun generate() {
        // handle user not selecting any of the workout button, duration buttons, or both


        // list of the buttons that are currently selected
        val selectedBtns = ArrayList<Button>()

        // check which of the buttons are selected
        for (button in workoutBtnList!!) {
            val bgColor = button.background
            if (bgColor.constantState == ContextCompat.getColor(requireContext(), R.color.dark_blue).toDrawable().constantState) {
                selectedBtns.add(button)
            }
        }

        var d = ""

        if (blueDurationBtn.isEmpty()) {
            Log.e("GenerateWorkout.kt", "generate - blueDurationList empty")
        } else {
            d = blueDurationBtn[0].text.toString().uppercase(Locale.getDefault())
            Log.e("GenerateWorkout.kt", "generate - $d")

            var bool = false

            // logic to make the workouts
            if (blueWorkoutBtn.contains(chestBtn)) {
                generateChestWorkout(d, bool)
            }
        }
    }

    // generate chest workout
    private fun generateChestWorkout(duration: String, bool: Boolean) {
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
        val rand = Random().nextInt((30 + 1) - 0) + 0
        val workout = chestWorkouts[rand]

        if (duration == "SHORT") {
            if (bool) {
                Log.e("GenerateWorkout.kt", "$workout $duration")
            } else if (!bool) {
                Log.e("GenerateWorkout.kt", "$workout $duration")
            }

        } else if (duration == "MEDIUM") {
            if (bool) {

            } else if (!bool) {

            }

        } else if (duration == "LONG") {
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
    private fun generateRandInts(arrayLen: Int, randNums: Int): ArrayList<Int> {
        val list = arrayListOf<Int>()

        while (randNums >= 0) {
            val rand = Random().nextInt((arrayLen + 1) - 0) + 0
            list.add(rand)
        }
        return list
    }

    // returns an array list of the workouts depending on the workout passed and the random ints array from "generateRandInts()"
    private fun getWorkouts(list: ArrayList<Int>):ArrayList<String>  {
        val list = ArrayList<String>()

        return list
    }

    // function that will check which of the duration button was selected
    // we check if the button is selected or not by getting the current background color
    private fun durationBtnSelected(): String {
        var selectedBtn = ""
        for (button in durationBtnList!!) {
            if (button.background.toString() == "dark_orange") {
                selectedBtn = button.text.toString()
            }
        }
        return selectedBtn
    }

    // Function to help handle correct button selection
    // returns -2 if user did not select any of the workout buttons
    // returns -1 if the user did not select any of the duration buttons
    // returns 0 is the user did not select any of the duration and workout buttons
    // returns 1 if user gave correct selections
    private fun areSelected(): Int {
        var n = if (workoutsSelectedMax < 1) {
            -2
        } else if (durationMax < 1) {
            -1
        } else if (durationMax < 1 && workoutsSelectedMax < 1) {
            0
        } else {
            1
        }
        return n
    }

    // function to change the background color of the buttons when pressed
    // only two buttons from the workout button can be selected
    private fun changeWorkoutBgColor(clickedButton: Button) {
        val blueColor = ContextCompat.getColor(requireContext(), R.color.dark_blue)
        val whiteColor = ContextCompat.getColor(requireContext(), R.color.white)

        if (clickedButton in blueWorkoutBtn) {
            blueWorkoutBtn.remove(clickedButton)
            clickedButton.background = whiteColor.toDrawable()
        } else {
            if (blueWorkoutBtn.size >= workoutsSelectedMax) {
                val firstBlueBtn = blueWorkoutBtn.removeAt(0)
                firstBlueBtn.background = whiteColor.toDrawable()
            }
            blueWorkoutBtn.add(clickedButton)
            clickedButton.background = blueColor.toDrawable()
        }
    }

    // function to change the background color of the duration buttons (only one button can be selected)
    private fun changeDurationBgColor(clickedButton: Button) {
        val orangeColor = ContextCompat.getColor(requireContext(), R.color.dark_orange)
        val grayColor = ContextCompat.getColor(requireContext(), R.color.light_gray)

        if (clickedButton in blueDurationBtn) {
            blueDurationBtn.remove(clickedButton)
            clickedButton.background = grayColor.toDrawable()
        } else {
            if (blueDurationBtn.size >= durationMax) {
                val firstBlueButton = blueDurationBtn.removeAt(0)
                firstBlueButton.background = grayColor.toDrawable()
            }
            blueDurationBtn.add(clickedButton)
            clickedButton.background = orangeColor.toDrawable()
        }
    }
}
