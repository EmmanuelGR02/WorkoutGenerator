package com.example.workoutgenerator

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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

        // change the "friends" and "requests" buttons color when clicked
        friendsBtn.setOnClickListener {
            val fragment = AddFriendsFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        // linear layout where all the friends items will go
        val friendContainer = binding.friendContainer
        val myFriendsList: ArrayList<String> = ArrayList()

        Database.getInstance().getRequests(currentUser) { requests ->
            myFriendsList.clear()
            myFriendsList.addAll(requests)

            // Fetch and add friend layouts
            displayRequests(myFriendsList, friendContainer)
        }

        return binding.root
    }


    private fun displayRequests(
        requestsList: List<String>,
        friendContainer: LinearLayout
    ) {
        // Clear existing views outside the loop
        friendContainer.removeAllViews()

        val inflater = LayoutInflater.from(requireContext())

        for (request in requestsList) {
            val friendObject = Friend(request)

            // Create a new instance of the friends_item_addfriend layout
            val friendLayout = inflater.inflate(
                R.layout.friend_request_item_layout,
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

            // accept request
            val addBtn = friendLayout.findViewById<Button>(R.id.addFriendBtn)
            addBtn?.setOnClickListener {
                Database.getInstance().addFriend(request)
                Database.getInstance().removeRequest(request)
            }

            // deny request
            val denyRequestBtn = friendLayout.findViewById<Button>(R.id.denyRequestBtn)
            denyRequestBtn?.setOnClickListener {
                Database.getInstance().removeRequest(request)
            }

            // Add the friend's layout to the container
            friendContainer.addView(friendLayout)
        }
    }

}