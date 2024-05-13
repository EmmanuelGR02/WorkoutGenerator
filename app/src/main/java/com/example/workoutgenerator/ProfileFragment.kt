package com.example.workoutgenerator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.workoutgenerator.databinding.FragmentProfileBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val weatherAPI = WeatherAPI()
    private val spotifyAPI = SpotifyAPI()
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set the values of the name, lastname and username text views
        val nameView = binding.name
        val lastnameView = binding.lastname
        val usernameView = binding.username

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewPager
        val viewPager: ViewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = ViewPagerAdapter(childFragmentManager)
    }

    private inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> WeatherFragment()
                1 -> MusicFragment()
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Weather"
                1 -> "Music"
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }
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
