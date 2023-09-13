package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workoutgenerator.databinding.FragmentSignInBinding

var currentUser = ""
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        // Attach click listener to the sign-in button
        binding.signInBtn.setOnClickListener {
            signIn()
        }
        // send user to sign up fragment
        binding.logInSignUpBtn.setOnClickListener {
            fragmentReplace(SignUpFragment())
        }

        return binding.root
    }

    private fun signIn() {
        val username = binding.logInUsername.text.toString()
        val password = binding.logInPassword.text.toString()
        val errMessage = binding.signInErrMessage
        val logIn = LoginActivity(username, password)

        currentUser = username

        if (username.isBlank() || password.isBlank()) {
            errMessage.text = "Inputs cannot be blank"
        } else {
            logIn.isLoginValid(errMessage) { isValid ->
                if (isValid) {
                    fragmentReplace(WelcomePageFragment())
                } else {
                    errMessage.text = "Something went wrong, please try again"
                }
            }
        }
    }

    // Function that sends user to specific fragment... For less code repetition
    private fun fragmentReplace(destination: Fragment) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_container, destination)?.commit()
    }

}
