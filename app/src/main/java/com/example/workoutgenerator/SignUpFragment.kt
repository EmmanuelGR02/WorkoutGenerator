package com.example.workoutgenerator

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.workoutgenerator.databinding.FragmentSignUpBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var database: Database
    private var birthdate = ""
    private var gender = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSignUpBinding.inflate(inflater, container, false)
       database = Database.getInstance()

        // send user back to sign in layout
        val backBtn = binding.backButton
        backBtn.setOnClickListener {
            val fragment = SignInFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_container, fragment)?.commit()
        }


        // Set the OnClickListener for the selectBirthdateButton
        binding.selectBirthdateButton.setOnClickListener {
            showDatePickerDialog { selectedDate ->
                val calendar = Calendar.getInstance()
                calendar.time = selectedDate

                birthdate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
                binding.selectBirthdateButton.text = birthdate
            }
        }

        // get gender from spinner
        val genderSpinner = binding.gender
        val genderOptions = arrayOf("gender", "MALE", "FEMALE")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        genderSpinner.adapter = adapter

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Get the selected gender from the adapter using the position
                gender = genderOptions[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // Sign up logic
        binding.signUpButton.setOnClickListener {
            signUp()
        }

        return binding.root
    }

    private fun signUp() {
        val signUpBtn = binding.signUpButton

        // Sign Up logic
        signUpBtn.setOnClickListener {
            val name = binding.name.text.toString()
            val lastName = binding.lastName.text.toString()

            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            val reEnteredPswd = binding.reEnteredPassword.text.toString()
            val signUpErrMessage = binding.errMessage

            val userInfo = SignUpActivity(name, lastName, username, password, birthdate, gender)

            userInfo.isValidInputs(signUpErrMessage, reEnteredPswd) { isValid ->
                if (isValid) {
                    database.saveUserInfo(username, userInfo,
                        onSuccess = {
                            // Clears the text from the input boxes
                            binding.name.text.clear()
                            binding.lastName.text.clear()
                            binding.username.text.clear()
                            binding.password.text.clear()
                            binding.reEnteredPassword.text.clear()
                            signUpErrMessage.text = ""

                            // Shows a message that the data was saved
                            Toast.makeText(requireContext(), "Successfully Saved", Toast.LENGTH_LONG).show()
                        },
                        onFailure = {
                            Toast.makeText(requireContext(), "Username already taken", Toast.LENGTH_LONG).show()
                        }
                    )
                } else {
                    Toast.makeText(requireContext(), "Retry Sign Up", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // prompt user calendar to pick the date
    private fun showDatePickerDialog(callback: DateCallback) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDateCalendar = Calendar.getInstance()
                selectedDateCalendar.set(Calendar.YEAR, year)
                selectedDateCalendar.set(Calendar.MONTH, month)
                selectedDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val selectedDate = selectedDateCalendar.time
                callback(selectedDate)
            },
            currentYear,
            currentMonth,
            currentDay
        )
        datePickerDialog.show()
    }

}

typealias DateCallback = (Date) -> Unit
