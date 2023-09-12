package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.workoutgenerator.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        // display setCurrStats layout or just currStats layout
        displaySetCurrStats()

        // user instance
        val user = User(currentUser)

        // Buttons to navigate through the main fragments
        val friendsBtn = binding.friendsBtn
        val workoutBtn = binding.workoutBtn
        friendsBtn.setOnClickListener {
            val fragment = FriendsFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }
        workoutBtn.setOnClickListener {
            val fragment = WorkoutGeneratorFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        // Set the values of the name, lastname and username text views
        val nameView = binding.name
        val lastnameView = binding.lastname
        val usernameView = binding.username

        user.getName {tempName ->
            nameView.text = tempName.toString()
        }
        user.getLastname { tempLastname ->
            lastnameView.text = tempLastname.toString()
        }
        usernameView.text = currentUser

        // sent user to setting fragment
        val settingsBtn = binding.settingBtn
        settingsBtn?.setOnClickListener {
            val fragment = SettingsFragment()
            (requireActivity() as MainActivity).navigateToFragment(fragment)
        }

        return view
    }

    // if the user does not have any stats saved, it will display a layout to take in their stats and save them
    // if the user already has stats saved, it will display a layout with all of their stats
    private fun displaySetCurrStats(){
        val userStatsInputs = binding.userStatsInputs

        Database.getInstance().isUserStatsEmpty(currentUser) { tempBool ->
            if (tempBool) {
                userStatsInputs.visibility = View.INVISIBLE
            } else {
                userStatsInputs.visibility = View.VISIBLE
            }
        }
    }

}
