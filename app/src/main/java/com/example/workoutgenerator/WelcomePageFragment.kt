package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.workoutgenerator.databinding.FragmentWelcomePageBinding
import com.example.workoutgenerator.databinding.WelcomeLayoutBinding

class WelcomePageFragment : Fragment() {
    private lateinit var binding: FragmentWelcomePageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomePageBinding.inflate(inflater, container, false)
        val view = binding.root
        val welcomeMsg = binding.welcomeMessage
        val getStartedBtn = binding.getStartedBtn
        val backBtn = binding.welcomeBackButton
        backToSignIn(backBtn)

        // welcome the user and check if today its their birthday
        User(currentUser).welcomeText { text ->
            welcomeMsg.text = text
        }
        getStartedBtn.setOnClickListener {
            val fragment = ProfileFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_container, fragment)?.commit()
        }

        // Inflate the layout for this fragment
        return view
    }

    // Back to Log In Page
    private fun backToSignIn(button: Button) {
        button.setOnClickListener {
            val fragment = SignInFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_container, fragment)?.commit()
        }
    }
}

