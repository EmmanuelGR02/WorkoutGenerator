package com.example.workoutgenerator

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
import java.util.Locale
import java.util.Random

class GenerateWorkout : Fragment() {
    private lateinit var binding: FragmentGenerateWorkoutBinding
    private val workoutsSelectedMax = 3
    private val blueWorkoutBtn: MutableList<Button> = mutableListOf()
    private var workoutBtnList: List<Button>? = null

    private val durationMax = 1
    private val orangeDurationBtn: MutableList<Button> = mutableListOf()
    private var durationBtnList: List<Button>? = null

    // initialize buttons
    private lateinit var chestBtn: Button
    private lateinit var backBtn: Button
    private lateinit var legsBtn: Button
    private lateinit var armsBtn: Button
    private lateinit var cardioBtn: Button
    private lateinit var absBtn: Button
    private lateinit var bicepsBtn: Button
    private lateinit var tricepsBtn: Button
    private lateinit var shoulderBtn: Button
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
        bicepsBtn = binding.bicepsBtn
        tricepsBtn = binding.tricepsBtn
        shoulderBtn = binding.shoulderBtn
        // duration buttons
        shortBtn = binding.shortBtn
        mediumBtn = binding.mediumBtn
        longBtn = binding.longBtn

        workoutBtnList = listOf(chestBtn, backBtn, legsBtn, armsBtn, cardioBtn, absBtn, bicepsBtn, tricepsBtn, shoulderBtn)
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
            // handle errors when user makes wrong selection
            if (blueWorkoutBtn.size < 1 && orangeDurationBtn.size < 1) {
                invalidSelection.text = "Please select desired workout and duration"
            } else if (blueWorkoutBtn.size < 1) {
                invalidSelection.text = "You must select at least one workout!"
            } else if (orangeDurationBtn.size < 1){
                invalidSelection.text = "You must select a duration"
            } else if (blueWorkoutBtn.size >= 1 && orangeDurationBtn.size >= 1) {
                invalidSelection.text = ""
                val fragment = ConfirmWorkoutFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.nav_container, fragment)?.commit()
            } else {
                Log.e("GenerateWorkout.kt", "OnCreateView - generate() failed to be called")
            }
        }
        return binding.root
    }

    // main function to generate the workout
    fun generate() {
        // initialize duration/workout button selected
        val duration = orangeDurationBtn.firstOrNull()?.text.toString().uppercase(Locale.ROOT)
        val selectedWorkouts = blueWorkoutBtn.map { it.text.toString().uppercase(Locale.ROOT) }

        if (duration == null || selectedWorkouts.isEmpty()) {
            Log.e("GenerateWorkout.kt", "generate() - Error: Invalid duration or no workout selected")
            return
        }

        // Create a map to associate each workout type with its generation function
        val workoutGenerators = mapOf(
            "CHEST" to ::generateChestWorkout,
            "BACK" to ::generateBackWorkout,
            "LEGS" to ::generateLegWorkout,
            "ARMS" to ::generateArmWorkout,
            "CARDIO" to ::generateCardioWorkout,
            "BICEPS" to ::generateBicepsWorkout,
            "TRICEPS" to ::generateTricepsWorkout,
            "SHOULDERS" to ::generateShoulderWorkout,
            "ABS" to ::generateAbWorkout
        )

        // Generate up to three workouts based on the selected buttons
        val maxSelectedWorkouts = minOf(selectedWorkouts.size, 3)
        for (i in 0 until maxSelectedWorkouts) {
            val workoutType = selectedWorkouts[i]
            workoutGenerators[workoutType]?.invoke(duration)
                ?: Log.e("GenerateWorkout.kt", "generate() - Error: Invalid workout type - $workoutType")
        }
    }


    // generates the amount of workouts based on the duration parameter
    private fun getNumOfWorkouts(duration : String) : Int {
        var numOfWorkouts = 0
        val workoutsSelected = blueWorkoutBtn.size

        if (workoutsSelected == 1 && duration == "LONG") {
            numOfWorkouts = 7
        } else if (workoutsSelected == 2 && duration == "LONG") {
            numOfWorkouts = 3
        } else if (workoutsSelected == 3 && duration == "LONG"){
            numOfWorkouts = 2
        }

        if (workoutsSelected == 1 && duration == "MEDIUM") {
            numOfWorkouts = 5
        } else if (workoutsSelected == 2 && duration == "MEDIUM") {
            numOfWorkouts = 3
        } else if (workoutsSelected == 3 && duration == "MEDIUM") {
            numOfWorkouts = 2
        }

        if (workoutsSelected == 1 && duration == "SHORT") {
            numOfWorkouts = 4
        } else if (workoutsSelected == 2 && duration == "SHORT") {
            numOfWorkouts = 2
        } else if (workoutsSelected == 3 && duration == "SHORT") {
            numOfWorkouts = 1
        }
        return numOfWorkouts
    }

    // generateAlgo function
    // generateAlgo function
    private fun generateAlgo(compoundList : ArrayList<String>, isolationList : ArrayList<String> = arrayListOf(), duration: String) : MutableList<String> {
        val numOfWorkouts = getNumOfWorkouts(duration)
        val randNums = generateRandInts(compoundList.size, numOfWorkouts)

        // separate number of workouts for compound and isolation
        var compoundRand = ArrayList<Int>()
        var isolationRand = ArrayList<Int>()

        // list containing the final workouts
        val workoutsGenerated = mutableListOf<String>()

        if (numOfWorkouts <= 1) {
            if (randNums.isNotEmpty() && randNums[0] < compoundList.size) {
                workoutsGenerated.add(compoundList[randNums[0]])
            } else {
                Log.e("GenerateWorkout", "Invalid index for compoundList")
            }
        } else if (numOfWorkouts == 3) {
            compoundRand = generateRandInts(compoundList.size, 2)
            isolationRand = generateRandInts(isolationList.size, 1)
            for (i in compoundRand) {
                if (i < compoundList.size) {
                    workoutsGenerated.add(compoundList[i])
                } else {
                    Log.e("GenerateWorkout", "Index $i out of bounds for compoundList of size ${compoundList.size}")
                }
            }
            if (isolationRand.isNotEmpty() && isolationRand[0] < isolationList.size) {
                workoutsGenerated.add(isolationList[isolationRand[0]])
            } else {
                Log.e("GenerateWorkout", "Invalid index for isolationList")
            }
        } else {
            compoundRand = generateRandInts(compoundList.size, numOfWorkouts - 1)
            isolationRand = generateRandInts(isolationList.size, 1)

            for (i in compoundRand) {
                if (i < compoundList.size) {
                    workoutsGenerated.add(compoundList[i])
                } else {
                    Log.e("GenerateWorkout", "Index $i out of bounds for compoundList of size ${compoundList.size}")
                }
            }

            if (isolationRand.isNotEmpty() && isolationRand[0] < isolationList.size) {
                workoutsGenerated.add(isolationList[isolationRand[0]])
            } else {
                Log.e("GenerateWorkout", "Invalid index for isolationList")
            }
        }
        return workoutsGenerated
    }




    // generate chest workout
    private fun generateChestWorkout(duration: String) : MutableList<String> {
        // array list containing different compound chest workouts
        val compoundChestWorkouts = arrayListOf(
            "Bench Press", "Incline Bench Press", "Decline Bench Press", "Dumbbell Bench Press",
            "Incline Dumbbell Bench Press", "Decline Dumbbell Bench Press", "Chest Dips", "Weighted Chest Dips",
            "Machine Chest Press", "Smith Machine Bench Press", "Landmine Press", "Barbell Pullovers",
            "Dumbbell Pullovers", "Dumbbell Squeeze Press",
            "Seated Chest Press Machine", "Chest Press Machine", "Incline Chest Press Machine",
            "Decline Chest Press Machine", "Hammer Strength Chest Press", "Smith Machine Incline Bench Press",
            "Smith Machine Decline Bench Press"
        )

        // array containing all the isolation chest workouts
        val isolationChestWorkouts = arrayListOf("Chest Flyes", "Dumbbell Flyes", "Incline Dumbbell Flyes", "Cable Flyes", "Pec Deck Flyes",
            "Push-Ups", "Wide-Grip Push-Ups", "Diamond Push-Ups", "Decline Push-Ups", "Weighted Push-Ups",
            "Machine Flyes", "Incline Machine Flyes", "Decline Machine Flyes", "Close-Grip Push-Ups",
            "Isometric Chest Squeezes", "Cable Crossovers", "Incline Cable Crossovers")


        val workoutsGenerated = generateAlgo(compoundChestWorkouts, isolationChestWorkouts, duration)

        Log.e("GenerateWorkout", "Chest Workout - $workoutsGenerated")
        return workoutsGenerated
    }

    // generate back workout
    private fun generateBackWorkout(duration: String) : MutableList<String> {
        // array list containing compound back Workouts
        val compoundBackWorkouts = arrayListOf(
            "Deadlifts", "Pull-Ups", "Bent-Over Rows", "Lat Pulldowns", "T-Bar Rows",
            "Seated Cable Rows", "Barbell Shrugs", "Wide-Grip Pull-Ups",
            "Inverted Rows", "Lateral Pull-Downs", "Meadows Rows", "Machine Rows",
            "Renegade Rows", "Kettlebell Swings", "Good Mornings", "Rope Climbs", "Cable Rows", "Smith Machine Rows"
        )

        // isolation back workouts
        val isolationBackWorkouts = arrayListOf(
            "Single-Arm Dumbbell Rows", "Pull-Ups", "Cable Face Pulls",
            "Reverse Flyes", "Seated Reverse Flyes", "Rope Pulldowns", "Hyperextensions"

        )


        val workoutsGenerated = generateAlgo(compoundBackWorkouts, isolationBackWorkouts, duration)

        Log.e("GenerateWorkout", "Back Workout - $workoutsGenerated")

        return workoutsGenerated
    }

    // generate legs workout
    private fun generateLegWorkout(duration: String) : MutableList<String> {
        // arrayList containing different compound leg workouts
        val compoundLegWorkouts = arrayListOf(
            "Squats", "Deadlifts", "Leg Press", "Lunges", "Step-Ups",
            "Hack Squats", "Romanian Deadlifts", "Bulgarian Split Squats", "Sumo Squats",
            "Front Squats", "Box Jumps", "Good Mornings", "Kettlebell Swings",
            "Pistol Squats", "Goblet Squats", "Barbell Lunges", "Smith Machine Squats",
            "Walking Lunges", "Single-Leg Press"
        )

        // isolation leg workouts
        val isolationLegWorkouts = arrayListOf(
            "Calf Raises", "Leg Extensions", "Glute Bridges", "Hamstring Curls",
            "Sissy Squats", "Donkey Calf Raises", "Seated Leg Curls", "Cable Pull-Throughs",
            "Leg Raises", "Leg Abduction Machine", "Dumbell RDL", "Barbell RDL"
        )

        val workoutsGenerated = generateAlgo(compoundLegWorkouts, isolationLegWorkouts, duration)

        Log.e("GenerateWorkout", "Leg Workout - $workoutsGenerated")
        return workoutsGenerated
    }


    // generate arms workout
    private fun generateArmWorkout(duration: String) : MutableList<String> {
        // array lists containing different bicep, triceps, and shoulder workouts
        val bicepWorkouts = arrayListOf(
            "Barbell Curls", "Dumbbell Curls", "Hammer Curls", "Preacher Curls", "Concentration Curls",
            "Spider Curls", "Cable Curls", "EZ Bar Curls", "Reverse Curls", "21s",
            "Incline Dumbbell Curls", "Zottman Curls", "Cross Body Curls", "Scott Curls", "Machine Curls",
            "Resistance Band Curls", "Isometric Curls", "Plate Curls", "Close-Grip Pull-Ups",
            "Alternating Dumbbell Curls", "Bicep Blaster Curls", "Body Drag Curls", "Static Hold Curls",
            "Wrist Curls", "Preacher Hammer Curls", "Drag Curls", "Concentration Curls on Machine", "Cable Hammer Curls",
            "Standing Barbell Curls", "Cheat Curls", "Machine Preacher Curls", "Pulley Curls", "Scott Curl Machine",
            "Dumbbell Hammer Curls", "Bicep Curl Machine", "Cable Preacher Curls", "Resistance Band Hammer Curls", "Isometric Hammer Curls"
        )
        val tricepWorkouts = arrayListOf(
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

        // biceps
        val numOfWorkouts = getNumOfWorkouts(duration)
        val nums = generateRandInts(bicepWorkouts.size, numOfWorkouts)
        val workoutsGenerated = mutableListOf<String>()
        for (i in nums) {
            workoutsGenerated.add(bicepWorkouts[i])
        }

        // triceps
        val numOfWorkouts2 = getNumOfWorkouts(duration)
        val nums2 = generateRandInts(tricepWorkouts.size, numOfWorkouts2)
        for (i in nums2) {
            workoutsGenerated.add(tricepWorkouts[i])
        }

        // shoulders
        val numOfWorkouts3 = getNumOfWorkouts(duration)
        val nums3 = generateRandInts(shoulderWorkouts.size, numOfWorkouts3)
        for (i in nums3) {
            workoutsGenerated.add(shoulderWorkouts[i])
        }

        Log.e("GenerateWorkout", "Arms Workout - $workoutsGenerated")
        return workoutsGenerated
    }

    // generate biceps workout
    private fun generateBicepsWorkout(duration: String) : MutableList<String> {
        val bicepWorkouts = arrayListOf(
            "Barbell Curls", "Dumbbell Curls", "Hammer Curls", "Preacher Curls", "Concentration Curls",
            "Spider Curls", "Cable Curls", "EZ Bar Curls", "Reverse Curls", "21s",
            "Incline Dumbbell Curls", "Zottman Curls", "Cross Body Curls", "Scott Curls", "Machine Curls",
            "Resistance Band Curls", "Isometric Curls", "Plate Curls", "Close-Grip Pull-Ups",
            "Alternating Dumbbell Curls", "Bicep Blaster Curls", "Body Drag Curls", "Static Hold Curls",
            "Wrist Curls", "Preacher Hammer Curls", "Drag Curls", "Concentration Curls on Machine", "Cable Hammer Curls",
            "Standing Barbell Curls", "Cheat Curls", "Machine Preacher Curls", "Pulley Curls", "Scott Curl Machine",
            "Dumbbell Hammer Curls", "Bicep Curl Machine", "Cable Preacher Curls", "Resistance Band Hammer Curls", "Isometric Hammer Curls"
        )


        val numOfWorkouts = getNumOfWorkouts(duration)
        val nums = generateRandInts(bicepWorkouts.size, numOfWorkouts)
        val workoutsGenerated = mutableListOf<String>()
        for (i in nums) {
            workoutsGenerated.add(bicepWorkouts[i])
        }
        Log.e("GenerateWorkout", "Bicep Workout - $workoutsGenerated")
        return workoutsGenerated
    }

    // generate triceps workout
    private fun generateTricepsWorkout(duration: String) : MutableList<String> {
        val tricepWorkouts = arrayListOf(
            "Tricep Dips", "Tricep Pushdowns", "Skull Crushers", "Close-Grip Bench Press", "Overhead Tricep Extensions",
            "Diamond Push-Ups", "Rope Pushdowns", "Tricep Kickbacks", "Bench Dips", "French Press",
            "Single-Arm Tricep Pushdowns", "Reverse Grip Tricep Pushdowns", "Tricep Bench Dips", "Kneeling Tricep Extensions", "Lying Tricep Extensions",
            "Cable Tricep Extensions", "Tricep Pull-Ups", "Bodyweight Tricep Extensions", "Dumbbell Tricep Extensions", "Tricep Cable Kickbacks",
            "Bent-Over Dumbbell Tricep Extensions", "Close-Grip Push-Ups", "Tricep Taps", "Close-Grip Dumbbell Press", "Skull Crusher Machine",
            "V-Bar Pushdowns", "Plate Tricep Extensions", "Lying Tricep Press", "Cable Overhead Tricep Extensions", "Reverse Grip Tricep Push-Ups",
            "Close-Grip Smith Machine Bench Press", "Tricep Machine", "Cable Rope Overhead Tricep Extensions", "Cable Lying Tricep Extensions", "Tricep Isolation Machine"
        )

        val numOfWorkouts = getNumOfWorkouts(duration)
        val nums = generateRandInts(tricepWorkouts.size, numOfWorkouts)
        val workoutsGenerated = mutableListOf<String>()
        for (i in nums) {
            workoutsGenerated.add(tricepWorkouts[i])
        }
        Log.e("GenerateWorkout", "Triceps Workout - $workoutsGenerated")
        return workoutsGenerated
    }

    // generate shoulder workout
    private fun generateShoulderWorkout(duration: String) : MutableList<String> {
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

        val numOfWorkouts = getNumOfWorkouts(duration)
        val nums = generateRandInts(shoulderWorkouts.size, numOfWorkouts)
        val workoutsGenerated = mutableListOf<String>()
        for (i in nums) {
            workoutsGenerated.add(shoulderWorkouts[i])
        }
        Log.e("GenerateWorkout", "Shoulder Workout - $workoutsGenerated")
        return workoutsGenerated
    }

    // generate cardio workout
    private fun generateCardioWorkout(duration: String) : MutableList<String> {
        val cardioWorkouts = arrayListOf(
            "Treadmill Running", "Elliptical Machine", "Stationary Bike", "Rowing Machine",
            "Stair Climber", "Arc Trainer", "Adaptive Motion Trainer", "Jacob's Ladder",
            "Assault Bike", "StepMill", "VersaClimber", "Sled Push/Pull", "Battle Ropes",
            "Jump Rope", "Swimming", "Circuit Training with Cardio Stations", "Stairmaster"
        )

        val numOfWorkouts = getNumOfWorkouts(duration)
        val nums = generateRandInts(cardioWorkouts.size, numOfWorkouts)
        val workoutsGenerated = mutableListOf<String>()
        for (i in nums) {
            workoutsGenerated.add(cardioWorkouts[i])
        }
        Log.e("GenerateWorkout", "Cardio Workout - $workoutsGenerated")
        return workoutsGenerated

    }

    // generate abs workout
    private fun generateAbWorkout(duration: String) : MutableList<String> {
        // list containing ab workouts
        val abWorkouts = arrayListOf(
            "Crunches", "Leg Raises", "Russian Twists", "Planks", "Bicycle Crunches",
            "Hanging Leg Raises", "Mountain Climbers", "Flutter Kicks", "Sit-Ups", "Side Planks",
            "V-Ups", "Toe Touches", "Lying Leg Raises", "Oblique Crunches", "Scissor Kicks",
            "Ab Wheel Rollouts", "Reverse Crunches", "Jackknife Sit-Ups", "Dead Bugs", "Standing Cable Crunches",
            "Medicine Ball Slams", "L-Sit Holds", "Hanging Knee Raises", "Windshield Wipers", "Stability Ball Rollouts",
            "Long Arm Crunches", "Plank Variations", "Cable Woodchoppers", "Bosu Ball Exercises", "Knee-to-Elbow Planks",
            "Russian Twist with Medicine Ball", "Captain's Chair Leg Raises", "Seated Leg Tucks", "Cable Crunch Twists", "Hanging Windshield Wipers",
            "Stir the Pot Planks", "Decline Sit-Ups", "Rope Crunches", "Dragon Flags", "Pulse Ups"
        )

        val numOfWorkouts = getNumOfWorkouts(duration)
        val nums = generateRandInts(abWorkouts.size, numOfWorkouts)
        val workoutsGenerated = mutableListOf<String>()
        for (i in nums) {
            workoutsGenerated.add(abWorkouts[i])
        }
        Log.e("GenerateWorkout", "Abs Workout - $workoutsGenerated")
        return workoutsGenerated
    }

    // generate random numbers. A parameter will determine how many random numbers are needed
    // depending on the duration of the workout
    private fun generateRandInts(arrayLen: Int, randNums: Int): ArrayList<Int> {
        val list = arrayListOf<Int>()

        while (list.size < randNums) {
            val rand = Random().nextInt(arrayLen)
            list.add(rand)
        }
        return list
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

        if (clickedButton in orangeDurationBtn) {
            orangeDurationBtn.remove(clickedButton)
            clickedButton.background = grayColor.toDrawable()
        } else {
            if (orangeDurationBtn.size >= durationMax) {
                val firstBlueButton = orangeDurationBtn.removeAt(0)
                firstBlueButton.background = grayColor.toDrawable()
            }
            orangeDurationBtn.add(clickedButton)
            clickedButton.background = orangeColor.toDrawable()
        }
    }
}
