package com.example.workoutgenerator

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.workoutgenerator.databinding.FragmentFriendsBinding

class FriendsFragment : Fragment() {
    private lateinit var binding: FragmentFriendsBinding
    // initializers

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val view = binding.root

        val profileBtn = binding.profileBtn
        val workoutBtn = binding.workoutBtn
        val addFriendBtn = binding.addFriendBtn

        addFriendBtn.setOnClickListener {
            val fragment = AddFriendsFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        profileBtn.setOnClickListener {
            val fragment = ProfileFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        workoutBtn.setOnClickListener {
            val fragment = WorkoutGeneratorFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
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

        return view
    }

    @SuppressLint("MissingInflatedId")
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

            // Create a new instance of the friend layout
            val friendLayout = inflater.inflate(R.layout.friend_layout_item, friendContainer, false)

            // initialize needed views
            val workoutTextView = friendLayout.findViewById<TextView>(R.id.LatestWorkoutLength)
            val durationTextView = friendLayout.findViewById<TextView>(R.id.workoutLength)
            val songTextView = friendLayout.findViewById<TextView>(R.id.dailySong)
            val dateTextView = friendLayout.findViewById<TextView>(R.id.datePosted)
            val captionTextView = friendLayout.findViewById<TextView>(R.id.caption)
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

            // set view values
            // Workout
            friendObject.getLatestWorkoutInfo("workout") { workout ->
                val fullText = "Workout: $workout"

                val spannableString = SpannableString(fullText)
                val colorSpan = ForegroundColorSpan(resources.getColor(R.color.dark_blue)) // Replace with the desired color resource
                val boldSpan = StyleSpan(Typeface.BOLD)

                // add color
                spannableString.setSpan(
                    colorSpan,
                    "Workout: ".length,
                    fullText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                // make text bold
                spannableString.setSpan(
                    boldSpan,
                    "Workout: ".length,
                    fullText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                workoutTextView.text = spannableString
            }

            // Duration
            friendObject.getLatestWorkoutInfo("duration") { duration ->
                val fullText = "Duration: $duration"

                val spannableString = SpannableString(fullText)
                val colorSpan = ForegroundColorSpan(resources.getColor(R.color.dark_orange)) // Replace with the desired color resource
                val boldSpan = StyleSpan(Typeface.BOLD)

                // add color
                spannableString.setSpan(
                    colorSpan,
                    "Duration: ".length,
                    fullText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                // make text bold
                spannableString.setSpan(
                    boldSpan,
                    "Duration: ".length,
                    fullText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                durationTextView.text = spannableString
            }

            // Song
            friendObject.getLatestWorkoutInfo("song") { song ->
                val fullText = "Daily song: $song"

                val spannableString = SpannableString(fullText)
                val colorSpan = ForegroundColorSpan(resources.getColor(R.color.dark_blue)) // Replace with the desired color resource
                val boldSpan = StyleSpan(Typeface.BOLD)

                // add color
                spannableString.setSpan(
                    colorSpan,
                    "Daily song: ".length,
                    fullText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                // make text bold
                spannableString.setSpan(
                    boldSpan,
                    "Daily song: ".length,
                    fullText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                songTextView.text = spannableString
            }

            // Date
            friendObject.getLatestWorkoutInfo("date") { date ->
                val fullText = "Date: $date"

                val spannableString = SpannableString(fullText)
                val colorSpan = ForegroundColorSpan(resources.getColor(R.color.dark_orange)) // Replace with the desired color resource
                val boldSpan = StyleSpan(Typeface.BOLD)

                // add color
                spannableString.setSpan(
                    colorSpan,
                    "Date: ".length,
                    fullText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                // make text bold
                spannableString.setSpan(
                    boldSpan,
                    "Date: ".length,
                    fullText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                dateTextView.text = spannableString
            }

            // Caption
            friendObject.getLatestWorkoutInfo("caption") { caption ->
                captionTextView.text = "$caption"
            }

            val friendAvatar = friendLayout.findViewById<ImageView>(R.id.friendImage)
            val friendNameTextView = friendLayout.findViewById<TextView>(R.id.friendUsername)

            friendAvatar.setImageResource(imageResource)
            friendNameTextView.text = friendObject.getUsername()

            // Add the friend's layout to the container
            friendContainer.addView(friendLayout)
        }
    }
}


