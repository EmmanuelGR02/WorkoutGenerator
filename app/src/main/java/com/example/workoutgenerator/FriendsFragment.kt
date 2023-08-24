package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.workoutgenerator.databinding.FragmentFriendsBinding

class FriendsFragment : Fragment() {
    private lateinit var binding: FragmentFriendsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val view = binding.root

        val profileBtn = binding.profileBtn
        val workoutBtn = binding.workoutBtn

        profileBtn.setOnClickListener {
            val fragment = ProfileFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        workoutBtn.setOnClickListener {
            val fragment = WorkoutGeneratorFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        val friendContainer = binding.friendContainer

        val myFriendsList: ArrayList<String> = ArrayList()

        User(currentUser).getFriends { friendsList ->
            myFriendsList.clear()
            myFriendsList.addAll(friendsList)

            // Fetch and add friend layouts
            fetchAndAddFriendLayouts(myFriendsList, friendContainer)
        }

        return view
    }

    private fun fetchAndAddFriendLayouts(
        friendsList: List<String>,
        friendContainer: LinearLayout
    ) {
        // Clear existing views outside the loop
        friendContainer.removeAllViews()

        val inflater = LayoutInflater.from(requireContext())

        for (friend in friendsList) {
            Friend(friend).getName { tempName ->
                val imageResource = R.drawable.main_logo // Replace with actual image resource

                // Create a new instance of the friend layout
                val friendLayout = inflater.inflate(R.layout.friend_layout_item, friendContainer, false)

                val friendAvatar = friendLayout.findViewById<ImageView>(R.id.friendImage)
                val friendNameTextView = friendLayout.findViewById<TextView>(R.id.friendUsername)

                friendAvatar.setImageResource(imageResource)
                friendNameTextView.text = tempName.toString()

                // Add the friend's layout to the container
                friendContainer.addView(friendLayout)
            }
        }
    }
}


