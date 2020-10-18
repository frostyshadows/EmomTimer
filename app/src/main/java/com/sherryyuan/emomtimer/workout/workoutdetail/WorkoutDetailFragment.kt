package com.sherryyuan.emomtimer.workout.workoutdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.FragmentWorkoutDetailBinding
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelType.WorkoutTimerViewModelType
import com.sherryyuan.emomtimer.utils.toFormattedString
import com.sherryyuan.emomtimer.workout.WorkoutsViewModel


class WorkoutDetailFragment : Fragment() {

    private val navArgs: WorkoutDetailFragmentArgs by navArgs()
    private val viewModel: WorkoutsViewModel by viewModels()

    private val binding: FragmentWorkoutDetailBinding by lazy {
        FragmentWorkoutDetailBinding.inflate(layoutInflater)
    }

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.apply {
            workoutNameText.text = navArgs.workout.name
            workoutLengthText.text = binding.root.context.getString(
                R.string.x_minutes,
                navArgs.workout.getTotalMinutes().toFormattedString()
            )
            repeatForText.text =
                binding.root.context.getString(R.string.repeat_for_x_rounds, navArgs.workout.numRounds)
        }
        setupExercisesList()
        setupEditButton()
        setupPlayButton()
        setupDeleteButton()
        return binding.root
    }

    private fun setupExercisesList() {
        binding.listExercises.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = WorkoutDetailExercisesAdapter(navArgs.workout.exercises)
        }
    }

    private fun setupPlayButton() {
        binding.playButton.setOnClickListener {
            navController.navigate(
                WorkoutDetailFragmentDirections.actionWorkoutDetailToTimerCountdown(
                    WorkoutTimerViewModelType(navArgs.workout)
                )
            )
        }
    }

    private fun setupEditButton() {
        binding.editButton.setOnClickListener {
            navController.navigate(
                WorkoutDetailFragmentDirections.actionWorkoutDetailToAddOrEditWorkout(
                    navArgs.workout
                )
            )
        }
    }

    // Display an AlertDialog confirming whether user wants to delete the workout, and delete the
    // workout if they select yes.
    private fun setupDeleteButton() {
        binding.deleteButton.setOnClickListener {
            context?.let {
                AlertDialog.Builder(it)
                    .setMessage(R.string.alert_delete_workout)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        viewModel.deleteWorkout(navArgs.workout)
                        navController.popBackStack()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            }
        }
    }
}