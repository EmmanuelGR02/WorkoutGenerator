package com.example.workoutgenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workoutgenerator.databinding.FragmentRequestsFragmentBinding
import com.example.workoutgenerator.databinding.FriendProfileBinding

class RequestsFragment : Fragment() {
    private lateinit var binding: FragmentRequestsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

}