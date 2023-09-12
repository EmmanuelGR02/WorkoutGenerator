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

        return view
    }

}
