package com.example.workoutgenerator

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        val friendsBtn = binding.friends
        val requestsBtn = binding.friendRequests

        friendsBtn.setOnClickListener {
        }

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

        val editText = binding.editText

        // Set a text change listener to the EditText
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed, but required to implement the interface
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Called when the text in the EditText changes
                val searchText = s.toString().trim() // Get the current input text
                filterAndDisplayFriends(searchText) // Filter and display friends based on input
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed, but required to implement the interface
            }
        })

        return binding.root
    }

    private fun filterAndDisplayFriends(searchText: String) {
        val friendContainer = binding.friendContainer
        friendContainer.removeAllViews() // Clear existing views

        User(currentUser).getFriends { friendsList ->
            // Iterate through the list of friends
            for (friendUsername in friendsList) {
                // Check if the username contains the search text (case-insensitive)
                if (friendUsername.contains(searchText, ignoreCase = true)) {
                    // Create a view for the matching friend
                    val friendLayout = layoutInflater.inflate(
                        R.layout.friends_item_addfriend, // Your friend layout
                        friendContainer,
                        false
                    )

                    // Initialize the friendLayout (set image, username, etc.)

                    // Add the friend's layout to the container
                    friendContainer.addView(friendLayout)
                }
            }
        }
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
