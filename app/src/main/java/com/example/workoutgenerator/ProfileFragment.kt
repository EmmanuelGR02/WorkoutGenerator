package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workoutgenerator.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        // display setCurrStats layout or just currStats layout
        val userStatsInputs = binding.userStatsInputs
        val currStatsLayout = binding.currStatsLayout

        Database.getInstance().isUserStatsEmpty(currentUser) { tempBool ->
            if (tempBool) {
                userStatsInputs.visibility = View.INVISIBLE
                currStatsLayout.visibility = View.VISIBLE
                setCurrentStats()
            } else {
                userStatsInputs.visibility = View.VISIBLE

                val submitBtn = binding.currStatsBtn

                // Set the users stats
                submitBtn?.setOnClickListener {
                    val benchPR = binding.setBenchPR.text.toString()
                    val squatPR = binding.setSquatPR.text.toString()
                    val deadliftPR = binding.setDeadliftPR.text.toString()
                    val weight = binding.setWeight.text.toString()
                    val height = binding.setHeight.text.toString()

                    Database.getInstance().saveUserStats(currentUser, benchPR, squatPR, deadliftPR, weight, height)
                    userStatsInputs.visibility = View.INVISIBLE
                    currStatsLayout.visibility = View.VISIBLE
                    setCurrentStats()
                }
            }
        }

        // user instance
        val user = User(currentUser)

        // Buttons to navigate through the main fragments
        val friendsBtn = binding.friendsBtn
        val workoutBtn = binding.workoutBtn
        friendsBtn.setOnClickListener {
            val fragment = FriendsFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }
        workoutBtn.setOnClickListener {
            val fragment = WorkoutGeneratorFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        // Set the values of the name, lastname and username text views
        val nameView = binding.name
        val lastnameView = binding.lastname
        val usernameView = binding.username

        user.getName {tempName ->
            nameView.text = tempName.toString()
        }
        user.getLastname { tempLastname ->
            lastnameView.text = tempLastname.toString()
        }
        usernameView.text = currentUser

        // sent user to setting fragment
        val settingsBtn = binding.settingBtn
        settingsBtn?.setOnClickListener {
            val fragment = SettingsFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        return view
    }

    // if the user does not have any stats saved, it will display a layout to take in their stats and save them
    // if the user already has stats saved, it will display a layout with all of their stats
    private fun displaySetCurrStats(){
        val userStatsInputs = binding.userStatsInputs
        val currStatsLayout = binding.currStatsLayout

        Database.getInstance().isUserStatsEmpty(currentUser) { tempBool ->
            if (tempBool) {
                userStatsInputs.visibility = View.INVISIBLE
                currStatsLayout.visibility = View.VISIBLE
                setCurrentStats()
            } else {
                userStatsInputs.visibility = View.VISIBLE

                val submitBtn = binding.currStatsBtn

                // Set the users stats
                submitBtn?.setOnClickListener {
                    val benchPR = binding.setBenchPR.text.toString()
                    val squatPR = binding.setSquatPR.text.toString()
                    val deadliftPR = binding.setDeadliftPR.text.toString()
                    val weight = binding.setWeight.text.toString()
                    val height = binding.setHeight.text.toString()

                    Database.getInstance().saveUserStats(currentUser, benchPR, squatPR, deadliftPR, weight, height)
                    userStatsInputs.visibility = View.INVISIBLE
                    currStatsLayout.visibility = View.VISIBLE
                    setCurrentStats()
                }
            }
        }
    }


    // Set the values for the users current stats
    private fun setCurrentStats() {
        val benchPR = binding.benchPR
        val squatPR = binding.squatPR
        val deadliftPR = binding.deadliftPR
        val age = binding.age
        val weight = binding.weight
        val height = binding.height
        val currentWorkout = binding.currWorkout
        val prevWorkout = binding.lastWorkout

        val user = User(currentUser)

        // set values
        user.getBenchPR { tempPR ->
            benchPR.text = "B-pr: $tempPR"
        }
        user.getSquatPR { tempPR ->
            squatPR.text = "S-pr: $tempPR"
        }
        user.getDeadliftPR { tempPR ->
            deadliftPR.text = "D-pr: $tempPR"
        }
        user.getAge { tempAge ->
            age.text = "Age: $tempAge"
        }
        user.getWeight { tempWeight ->
            weight.text = "Weight: $tempWeight"
        }
        user.getHeight { tempHeight ->
            height.text = "Height: $tempHeight"
        }
        user.getLastWorkout { tempWorkout ->
            prevWorkout.text = "Previous: $tempWorkout"
        }
        user.getCurrentWorkout { tempWorkout->
            currentWorkout.text = "Current: $tempWorkout"
        }
    }

}
