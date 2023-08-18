package com.example.workoutgenerator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.workoutgenerator.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var database: Database
    private lateinit var binding: FragmentSignInBinding
    var currentUser = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        // Attach click listener to the sign-in button
        binding.signInBtn.setOnClickListener {
            signIn()
            Log.e("signIn", "sign in button clicked")
        }

        return binding.root
    }

    private fun signIn() {
        val username = binding.logInUsername.text.toString()
        val password = binding.logInPassword.text.toString()
        val errMessage = binding.signInErrMessage
        val logIn = LoginActivity(username, password)
        currentUser = username

        logIn.isLoginValid(errMessage) { isValid ->
            if (isValid) {
                // Inform the MainActivity to perform the fragment transaction
                (requireActivity() as MainActivity).replaceFragment(WelcomePageFragment())
            } else {
                Log.e("signIn", "Sign in failed")
            }
        }

    }
}
