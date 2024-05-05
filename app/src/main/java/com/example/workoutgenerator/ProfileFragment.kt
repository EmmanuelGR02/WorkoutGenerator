package com.example.workoutgenerator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workoutgenerator.databinding.FragmentProfileBinding
import kotlin.math.roundToInt

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val weatherAPI = WeatherAPI()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        // displaySetCurrStats()

        // user instance
        val user = User(currentUser)

        // Buttons to navigate through the main fragments
        val friendsBtn = binding.friendsBtn
        friendsBtn?.setOnClickListener {
            val fragment = FriendsFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }
        binding.workoutBtn?.setOnClickListener {
            val fragment = WorkoutGeneratorFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        // Set the values of the name, lastname and username text views
        val nameView = binding.name
        val lastnameView = binding.lastname
        val usernameView = binding.username
        val currWeatherView = binding.currentWeather
        val currHumidityView = binding.currentHumidity
        val currFeelsLikeView = binding.currentFeelsLike
        val currLowView = binding.currentLow
        val currHighView = binding.currentHigh
        val currWeatherTypeView = binding.weatherType
        val currUVindexView = binding.currentUVindex
        val currVisibilityView = binding.currentVisibility


        // fetch current weather
        weatherAPI.getCurrentWeather { temp ->
            temp?.let {
                currWeatherView.text = "$temp\u2109"
            }
        }

        // fetch current feels like temp
        weatherAPI.getCurrentFeelsLikeTemperature { temp ->
            temp?.let {
                Log.e("ProfileFragment.kt", "Feels like temp -> $temp")
                currFeelsLikeView.text = "Feels Like: $temp\u2109"
            }
        }

        // fetch current humidity
        weatherAPI.getCurrentHumidity { humidity ->
            humidity?.let {
                currHumidityView.text = "Humidity: ${humidity.roundToInt()}%"
            }
        }

        // fetch current low
        weatherAPI.getCurrentLowTemperature { low ->
            low?.let {
                currLowView.text = "L: $low\u2109"
            }
        }

        // fetch current high
        weatherAPI.getCurrentHighTemperature { high ->
            high?.let {
                currHighView.text = "H: $high\u2109"
            }
        }

        // fetch current weather type
        weatherAPI.getCurrentWeatherType { type ->
            type?.let {
                var realType = ""
                if (type == "Clouds") {
                    realType = "Cloudy"
                }
                currWeatherTypeView.text = "$realType"
            }
        }

        // fetch current UVIndex
        weatherAPI.getCurrentUVIndex{ index ->
            index?.let {
                Log.e("ProfileFragment.kt", "UV Index -> $index")
                currUVindexView.text = "UV Index: $index"
            }
        }

        // fetch current visibility
        weatherAPI.getCurrentVisibility { visibility ->
            visibility?.let {
                currVisibilityView.text = "Visibility: $visibility mi"
            }
        }


        user.getName { tempName ->
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


//    private fun displaySetCurrStats(){
//        val userStatsInputs = binding.userStatsInputs
//        val currStatsLayout = binding.currStatsLayout
//
//        Database.getInstance().isUserStatsEmpty(currentUser) { tempBool ->
//            if (tempBool) {
//                userStatsInputs.visibility = View.INVISIBLE
//                currStatsLayout.visibility = View.VISIBLE
//                setCurrentStats()
//            } else {
//                userStatsInputs.visibility = View.VISIBLE
//
//                val submitBtn = binding.currStatsBtn
//
//                // Set the users stats
//                submitBtn?.setOnClickListener {
//                    val benchPR = binding.setBenchPR.text.toString()
//                    val squatPR = binding.setSquatPR.text.toString()
//                    val deadliftPR = binding.setDeadliftPR.text.toString()
//                    val weight = binding.setWeight.text.toString()
//                    val height = binding.setHeight.text.toString()
//
//                    Database.getInstance().saveUserStats(currentUser, benchPR, squatPR, deadliftPR, weight, height)
//                    userStatsInputs.visibility = View.INVISIBLE
//                    currStatsLayout.visibility = View.VISIBLE
//                    setCurrentStats()
//                }
//            }
//        }
//    }
//
//
//    // Set the values for the users current stats
//    private fun setCurrentStats() {
//        val benchPR = binding.benchPR
//        val squatPR = binding.squatPR
//        val deadliftPR = binding.deadliftPR
//        val age = binding.age
//        val weight = binding.weight
//        val height = binding.height
//        val currentWorkout = binding.currWorkout
//        val prevWorkout = binding.lastWorkout
//
//        val user = User(currentUser)
//
//        // set values
//        user.getBenchPR { tempPR ->
//            benchPR.text = "B-pr: $tempPR lbs"
//
//        }
//        user.getSquatPR { tempPR ->
//            squatPR.text = "S-pr: $tempPR lbs"
//        }
//        user.getDeadliftPR { tempPR ->
//            deadliftPR.text = "D-pr: $tempPR lbs"
//        }
//        user.getAge { tempAge ->
//            age.text = "Age: $tempAge"
//        }
//        user.getWeight { tempWeight ->
//            weight.text = "Weight: $tempWeight lbs"
//        }
//        user.getHeight { tempHeight ->
//            height.text = "Height: $tempHeight"
//        }
//        user.getPrevWorkout { tempWorkout ->
//            prevWorkout.text = "Previous: $tempWorkout"
//        }
//        user.getCurrentWorkout { tempWorkout->
//            currentWorkout.text = "Current: $tempWorkout"
//        }
//    }

}
