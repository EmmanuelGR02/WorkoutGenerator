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
import android.widget.LinearLayout
import android.widget.TextView
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


        // add linear layout of friends workout of the day stats
        val friendObject = Friend(friendUsername)
        val friendDailyWorkoutContainer = binding.friendContainer
        fetchAndAddFriendLayout(friendObject, friendDailyWorkoutContainer)

        // Use the friend's username to populate the UI
        friendsStats(friendUsername)

        return binding.root
    }

    // fill out the layout with friends stats
    @SuppressLint("SetTextI18n")
    private fun friendsStats(username: String?) {
        val friendObject = Friend(username)
        val benchPR = binding.benchPR
        val squatPR = binding.squatPR
        val deadliftPR = binding.deadliftPR
        val ageView = binding.age
        val weightView = binding.weightHeight

        // Use the passed username to get friend's stats
        friendObject.getBenchPR { benchPRValue ->
            benchPR.text = "Bench PR: $benchPRValue lbs"
        }

        friendObject.getSquatPR { squatPRValue ->
            squatPR.text = "Squat PR: $squatPRValue lbs"
        }

        friendObject.getDeadliftPR { deadliftPRValue ->
            deadliftPR.text = "Dead-lift PR: $deadliftPRValue lbs"
        }

        friendObject.getAge { age ->
            ageView.text = "Age: $age"
        }

        friendObject.getWeight { weight ->
            val weightText = weight ?: "N/A"
            friendObject.getHeight { height ->
                val heightText = height ?: "N/A"
                weightView.text = "Weight: $weightText lbs   Height: $heightText"
            }
        }
    }

    private fun fetchAndAddFriendLayout(
        friendObject: Friend,
        friendContainer: LinearLayout
    ) {
        // Clear existing views outside the loop
        friendContainer.removeAllViews()

        val inflater = LayoutInflater.from(requireContext())

        // Create a new instance of the friend_layout_single layout
        val friendLayout = inflater.inflate(
            R.layout.friend_profile_item, // Replace with your layout resource
            friendContainer,
            false
        )

        // initialize needed views
        val workoutTextView = friendLayout.findViewById<TextView>(R.id.LatestWorkoutLength)
        val durationTextView = friendLayout.findViewById<TextView>(R.id.workoutLength)
        val songTextView = friendLayout.findViewById<TextView>(R.id.dailySong)
        val dateTextView = friendLayout.findViewById<TextView>(R.id.datePosted)
        val captionTextView = friendLayout.findViewById<TextView>(R.id.caption)

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

        // Add the friend_layout_single layout to the container
        friendContainer.addView(friendLayout)
    }

}
