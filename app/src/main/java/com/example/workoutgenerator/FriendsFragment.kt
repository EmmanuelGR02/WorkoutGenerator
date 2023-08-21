package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.example.workoutgenerator.databinding.FragmentFriendsBinding

class FriendsFragment : Fragment() {
    private lateinit var binding: FragmentFriendsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

        val scrollView = binding.scrollView
        val friendContainer = binding.friendContainer

        val myFriendsList: ArrayList<String> = ArrayList()

        User(currentUser).getFriends { friendsList ->
            myFriendsList.clear()
            myFriendsList.addAll(friendsList)

            // Fetch and add friend layouts
            fetchAndAddFriendLayouts(myFriendsList, friendContainer, scrollView)
        }

        return view
    }

//    fun createFriendLayout(imageResource: Int, friendName: String): View {
//        val inflater = LayoutInflater.from(requireContext())
//        val friendLayout = inflater.inflate(R.layout.friend_layout_item, null)
//
//        val friendAvatar = friendLayout.findViewById<ImageView>(R.id.friendAvatar)
//        val friendNameTextView = friendLayout.findViewById<TextView>(R.id.friendName)
//
//        friendAvatar.setImageResource(imageResource)
//        friendNameTextView.text = friendName
//
//        return friendLayout
//    }

    private fun fetchAndAddFriendLayouts(
        friendsList: List<String>,
        friendContainer: LinearLayout,
        scrollView: ScrollView
    ) {
        friendContainer.removeAllViews() // Clear existing views

        val inflater = LayoutInflater.from(requireContext())

        for (friend in friendsList) {
            Friend(friend).getName { tempName ->
                val imageResource = R.drawable.main_logo // Replace with actual image resource

                // Create a new instance of the friend layout
                val friendLayout = inflater.inflate(R.layout.friend_layout_item, null)

                val friendAvatar = friendLayout.findViewById<ImageView>(R.id.friendAvatar)
                val friendNameTextView = friendLayout.findViewById<TextView>(R.id.friendName)

                friendAvatar.setImageResource(imageResource)
                friendNameTextView.text = tempName.toString()

                // Add the friend's layout to the container
                friendContainer.addView(friendLayout)
            }
        }

        // Add the container to the scroll view after all friend layouts have been added
        scrollView.addView(friendContainer)
    }
}
