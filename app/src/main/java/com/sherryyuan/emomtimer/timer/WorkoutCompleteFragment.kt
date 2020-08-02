package com.sherryyuan.emomtimer.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sherryyuan.emomtimer.databinding.FragmentWorkoutCompleteBinding

class WorkoutCompleteFragment : Fragment() {

    private val binding: FragmentWorkoutCompleteBinding by lazy {
        FragmentWorkoutCompleteBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupDoneButton()
        return binding.root
    }

    private fun setupDoneButton() {
        binding.doneButton.setOnClickListener {
            findNavController().navigate(
                WorkoutCompleteFragmentDirections.actionWorkoutCompleteBackToWorkouts()
            )
        }
    }
}