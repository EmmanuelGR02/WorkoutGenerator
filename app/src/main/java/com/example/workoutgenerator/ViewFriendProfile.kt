package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workoutgenerator.databinding.FragmentViewFriendProfileBinding
import com.example.workoutgenerator.databinding.FriendProfileBinding

class ViewFriendProfile : Fragment() {
    private lateinit var binding: FriendProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FriendProfileBinding.inflate(inflater, container, false)

        val usernameView = binding.friendUsername
        val backBtn = binding.backButton

        backBtn.setOnClickListener {
            val fragment = FriendsFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        // Retrieve friend's username from arguments
        val friendUsername = arguments?.getString("friendUsername")

        // display the username for the current friend
        usernameView.text = friendUsername


        // Use the friend's username to populate the UI
        friendsStats(friendUsername)

        return binding.root
    }

    // fill out the layout with friends stats
    private fun friendsStats(username: String?) {
        val friendObject = Friend(username)
        val benchPR = binding.benchPR
        val squatPR = binding.squatPR
        val deadliftPR = binding.deadliftPR
        val ageView = binding.age
        val weight = binding.weight
        val height = binding.height

        // Use the passed username to get friend's stats
        friendObject.getBenchPR { benchPRValue ->
            benchPR.text = "Bench PR: $benchPRValue lbs"
        }

        friendObject.getSquatPR { squatPRValue ->
            squatPR.text = "Bench PR: $squatPRValue lbs"
        }

        friendObject.getDeadliftPR { deadliftPRValue ->
            deadliftPR.text = "Bench PR: $deadliftPRValue lbs"
        }

        friendObject.getAge { age ->
            ageView.text = age.toString()
        }

    }

}
