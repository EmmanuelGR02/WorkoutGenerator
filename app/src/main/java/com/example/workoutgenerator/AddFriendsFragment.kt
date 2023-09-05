package com.example.workoutgenerator

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.workoutgenerator.databinding.FragmentAddFriendsBinding

class AddFriendsFragment : Fragment() {

    private lateinit var binding: FragmentAddFriendsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddFriendsBinding.inflate(inflater, container, false)

        val backBtn = binding.backButton

        // Go back to previous fragment
        backBtn.setOnClickListener {
            val fragment = FriendsFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
            Log.e("test", "Back button working")
        }

        // linear layout where all the friends items will go
        val friendContainer = binding.friendContainer
        val myFriendsList: ArrayList<String> = ArrayList()

        User(currentUser).getFriends { friendsList ->
            myFriendsList.clear()
            myFriendsList.addAll(friendsList)

            // Fetch and add friend layouts
            fetchAndAddFriendLayouts(myFriendsList, friendContainer)
        }

        return binding.root
    }

    private fun fetchAndAddFriendLayouts(
        friendsList: List<String>,
        friendContainer: LinearLayout
    ) {
        // Clear existing views outside the loop
        friendContainer.removeAllViews()

        val inflater = LayoutInflater.from(requireContext())

        for (friend in friendsList) {
            val friendObject = Friend(friend)

            val imageResource = R.drawable.main_logo // Replace with actual image resource

            // Create a new instance of the friend_layout_single layout
            val friendLayout = inflater.inflate(
                R.layout.friends_item_addfriend,
                friendContainer,
                false
            )

            val friendPfpBtn = friendLayout.findViewById<Button>(R.id.pfpButton)

            // take the user to the users friend profile when clicked on their pfp
            // Send the friends username as an argument to the "ViewFriendProfile" fragment
            friendPfpBtn.setOnClickListener {
                val fragment = ViewFriendProfile()

                val bundle = Bundle()
                bundle.putString("friendUsername", friendObject.getUsername())
                fragment.arguments = bundle

                val fragmentManager = requireActivity().supportFragmentManager
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.nav_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            val friendNameTextView = friendLayout.findViewById<TextView>(R.id.friendUsername)

            friendNameTextView.text = friendObject.getUsername()

            // Add the friend's layout to the container
            friendContainer.addView(friendLayout)
        }
    }
}
