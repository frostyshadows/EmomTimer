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
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.FragmentWorkoutDetailBinding
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelType.WorkoutTimerViewModelType
import com.sherryyuan.emomtimer.workout.WorkoutsViewModel


class WorkoutDetailFragment : Fragment() {

    private val navArgs: WorkoutDetailFragmentArgs by navArgs()
    private val viewModel: WorkoutsViewModel by viewModels()

    private val binding: FragmentWorkoutDetailBinding by lazy {
        FragmentWorkoutDetailBinding.inflate(layoutInflater)
    }

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var workoutDetailAdapter: WorkoutDetailExercisesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.apply {
            workoutName.text = navArgs.workout.name
            workoutLength.text = binding.root.context.getString(
                R.string.x_minutes_total,
                navArgs.workout.getTotalMinutes()
            )
            repeatFor.text =
                binding.root.context.getString(R.string.repeat_for_x_sets, navArgs.workout.numSets)
        }
        setupExercisesList()
        setupEditButton()
        setupPlayButton()
        setupDeleteButton()
        return binding.root
    }

    private fun setupExercisesList() {
        viewManager = LinearLayoutManager(context)
        workoutDetailAdapter = WorkoutDetailExercisesAdapter(navArgs.workout.exercises)

        binding.listExercises.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = workoutDetailAdapter
        }
    }

    private fun setupPlayButton() {
        binding.playButton.setOnClickListener {
            findNavController().navigate(
                WorkoutDetailFragmentDirections.actionWorkoutDetailFragmentToTimerCountdownFragment(
                    WorkoutTimerViewModelType(navArgs.workout)
                )
            )
        }
    }

    private fun setupEditButton() {
        binding.editButton.setOnClickListener {
            findNavController().navigate(
                WorkoutDetailFragmentDirections.actionWorkoutDetailFragmentToAddOrEditWorkoutFragment(
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
                        findNavController().popBackStack()
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