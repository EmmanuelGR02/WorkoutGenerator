package com.example.workoutgenerator

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.example.workoutgenerator.databinding.FragmentGenerateWorkoutBinding
import java.util.Random

class GenerateWorkout : Fragment() {
    private lateinit var binding: FragmentGenerateWorkoutBinding
    private val twoButtons = arrayListOf<String>()
    private val twoButtonsNonString = arrayListOf<Button>()

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
        val chestBtn = binding.chestBtn
        val backBtn = binding.backBtn
        val legsBtn = binding.legsBtn
        val armsBtn = binding.armsBtn
        val cardioBtn = binding.cardioBtn
        val absBtn = binding.absBtn

        // duration buttons
        val shortBtn = binding.shortBtn
        val mediumBtn = binding.mediumBtn
        val longBtn = binding.longBtn

        val durationButtons = listOf(shortBtn, mediumBtn, longBtn)

        // change background of workout colors when selected
        val workoutButtons = listOf(chestBtn, backBtn, legsBtn, armsBtn, cardioBtn, absBtn)
        for (button in workoutButtons) {
            button.setOnClickListener {
                changeWorkoutBgColor(button, workoutButtons)
            }
        }


        // call main fun to generate the workout
        val generateBtn = binding.generateBtn
        generateBtn?.setOnClickListener {
            if (areSelected()) {
                generate()
            } else {
                Log.e("GenerateWorkout.kt", "OnCreateView - Invalid button selection")
            }
        }

        return binding.root
    }


    // main function to generate the workout
    private fun generate() {
        // initialize the workout buttons
        val chestBtn = binding.chestBtn
        val backBtn = binding.backBtn
        val legsBtn = binding.legsBtn
        val armsBtn = binding.armsBtn
        val cardioBtn = binding.cardioBtn
        val absBtn = binding.absBtn

        val shortBtn = binding.shortBtn
        val mediumBtn = binding.mediumBtn
        val longBtn = binding.longBtn

        val durationButtons = listOf(shortBtn, mediumBtn, longBtn)

        // list of the buttons
        val btnList = ArrayList<Button>()
        btnList.add(chestBtn)
        btnList.add(backBtn)
        btnList.add(legsBtn)
        btnList.add(armsBtn)
        btnList.add(cardioBtn)
        btnList.add(absBtn)


        // list of the buttons that are currently selected
        val selectedBtns = ArrayList<Button>()

        // check which of the buttons are selected
        for (button in btnList) {
            val bgColor = button.background
            if (bgColor.constantState == ContextCompat.getColor(requireContext(), R.color.dark_blue).toDrawable().constantState) {
                selectedBtns.add(button)
            }
        }

        // initialize the two selected buttons
        val button1 = ""
        val button2 = ""

        var bool = false

        // check if there were 2 or 1 button selected
        if (selectedBtns.size > 0) {
            val button1 = selectedBtns[0]
            val button2 = selectedBtns[1]
            bool = true
        } else {
            val button1 = selectedBtns[0]
            bool = false
        }

        // logic to make the workouts
        if (button1 == "CHEST" || button2 == "CHEST") {
            generateChestWorkout(durationBtnSelected(), bool)
        }

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
        // initialize buttons
        val shortBtn = binding.shortBtn
        val mediumBtn = binding.mediumBtn
        val longBtn = binding.longBtn

        // list containing all the duration buttons
        val btnList = ArrayList<Button>()
        btnList.add(shortBtn)
        btnList.add(mediumBtn)
        btnList.add(longBtn)

        var selectedBtn = ""
        for (button in btnList) {
            if (button.background.toString() == "dark_orange") {
                selectedBtn = button.text.toString()
            }
        }

        return selectedBtn
    }

    // function that checks if the user selected any buttons
    // prevents the app from crashing if the user didn't select any
    // it also changes the buttons background color when pressed
    // only two buttons from the workout layout are allowed to change their bg
    // and only one from the duration layout
    private fun areSelected(): Boolean {
        var bool = false
        // initialize the workout buttons
        val chestBtn = binding.chestBtn
        val backBtn = binding.backBtn
        val legsBtn = binding.legsBtn
        val armsBtn = binding.armsBtn
        val cardioBtn = binding.cardioBtn
        val absBtn = binding.absBtn

        // duration buttons
        val shortBtn = binding.shortBtn
        val mediumBtn = binding.mediumBtn
        val longBtn = binding.longBtn

        // get their current background colors
        // list of the buttons
        val btnList = ArrayList<Button>()
        btnList.add(chestBtn)
        btnList.add(backBtn)
        btnList.add(legsBtn)
        btnList.add(armsBtn)
        btnList.add(cardioBtn)
        btnList.add(absBtn)
        btnList.add(shortBtn)
        btnList.add(mediumBtn)
        btnList.add(longBtn)

        chestBtn?.setOnClickListener {
            chestBtn.background = ContextCompat.getColor(requireContext(), R.color.dark_blue).toDrawable()
        }

        // list of the buttons that are currently selected
        val selectedBtns = ArrayList<String>()

        // check which of the buttons are selected
        for (button in btnList) {
            val bgColor = button.background.toString()
            print(bgColor)
            if (bgColor == "dark_blue") {
                selectedBtns.add(button.text.toString())
            }
        }

        bool = selectedBtns.size == 2
        return bool
    }

    // function to change the background color of the buttons when pressed
    // only two buttons from the workout button can be selected
    private fun changeWorkoutBgColor(clickedButton: Button, workoutButtons: List<Button>) {
        val blueColor = ContextCompat.getColor(requireContext(), R.color.dark_blue)
        val whiteColor = ContextCompat.getColor(requireContext(), R.color.white)

        if (twoButtons.size == 2) {
            twoButtonsNonString[0].background = whiteColor.toDrawable()
            twoButtonsNonString.remove(twoButtonsNonString[0])
            val tempName = twoButtonsNonString[0].text.toString()
            twoButtons.remove(tempName)
            Log.e("GenerateWorkout", "changeWorkoutBgColor - Size is 2!! $tempName")
        }

        val buttonName = clickedButton.text.toString()
        if (!(twoButtons.contains(buttonName))) {
            clickedButton.background = blueColor.toDrawable()
            twoButtons.add(buttonName)
            twoButtonsNonString.add(clickedButton)
        } else if (twoButtons.contains(buttonName)) {
            clickedButton.background = whiteColor.toDrawable()
            twoButtons.remove(buttonName)
            twoButtonsNonString.remove(clickedButton)
        }
        val arrSize = twoButtons.size
        Log.e("GenerateWorkout", "changeWorkoutBgColor - $twoButtons, $arrSize")

    }

    // function to change the background color of the duration buttons (only one button can be selected)
    private fun changeDurationBgColor(clickedButton: Button, durationButtons: List<Button>) {
        val orangeColor = ContextCompat.getColor(requireContext(), R.color.dark_orange)
        val grayColor = ContextCompat.getColor(requireContext(), R.color.dark_gray)


    }

}