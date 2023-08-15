package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.workoutgenerator.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var database: Database
    var currentUser = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        signIn()
        return binding.root
    }

    private fun signIn() {
        val username = binding.logInUsername.toString()
        val password = binding.logInPassword.toString()
        val errMessage = binding.signInErrMessage
        val logIn = LoginActivity(username, password)
        currentUser = username

        logIn.isLoginValid(errMessage) { isValid ->
            if (isValid) {
                // Navigate to welcome page if credentials are valid
                val fragment = WelcomePageFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.nav_container, fragment)?.commit()
            }
        }
    }

}
