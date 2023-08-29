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
        val benchPR = binding.benchPR

        // Use the passed username to get friend's stats
        Friend(username).getBenchPR { benchPRValue ->
            benchPR.text = "Bench PR: $benchPRValue"
        }
    }

}
