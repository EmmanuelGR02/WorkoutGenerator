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
import com.example.workoutgenerator.databinding.FragmentProfileBinding

class FriendsFragment : Fragment() {
    private lateinit var binding: FragmentFriendsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val view = binding.root

        createFriendLayouts()

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

        return view
    }

    private fun createFriendLayouts() {
        val scrollView = binding.scrollView
        val friendContainer = binding.friendContainer

        val myFriendsList: ArrayList<String> = ArrayList()

        User(currentUser).getFriends { friendsList ->
            myFriendsList.clear()
            myFriendsList.addAll(friendsList)

            // After fetching friends list, fetch and add friend layouts
            fetchAndAddFriendLayouts(myFriendsList, friendContainer, scrollView)
        }
    }

    private fun fetchAndAddFriendLayouts(
        friendsList: List<String>,
        friendContainer: LinearLayout,
        scrollView: ScrollView
    ) {
        var friendCount = 0

        for (friend in friendsList) {
            Friend(friend).getName { tempName ->
                val friendLayout = layoutInflater.inflate(R.layout.friend_layout_item, null)

                val friendAvatar = friendLayout.findViewById<ImageView>(R.id.friendAvatar)
                val friendName = friendLayout.findViewById<TextView>(R.id.friendName)

                // Set friend's information in the layout
                friendName.text = tempName.toString()

                // Remove any existing parent before adding the view to the container
                val parent = friendLayout.parent as? ViewGroup
                parent?.removeView(friendLayout)

                // Add the friend's layout to the container
                friendContainer.addView(friendLayout)

                // Check if all friend layouts have been added
                friendCount++
                if (friendCount == friendsList.size) {
                    // All friend layouts added, now add the container to the scroll view
                    scrollView.addView(friendContainer)
                }
            }

        }
    }


}
