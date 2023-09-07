package com.example.workoutgenerator

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workoutgenerator.databinding.FragmentRequestsFragmentBinding
import com.example.workoutgenerator.databinding.FriendProfileBinding

class RequestsFragment : Fragment() {
    private lateinit var binding: FragmentRequestsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestsFragmentBinding.inflate(inflater, container, false)

        val friendsBtn = binding.friends
        val requestsBtn = binding.friendRequests

        // change the "friends" and "requests" buttons color when clicked
        friendsBtn.setOnClickListener {
            val fragment = AddFriendsFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        return binding.root
    }

}