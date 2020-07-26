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
import com.sherryyuan.emomtimer.workout.WorkoutsViewModel


class WorkoutDetailFragment : Fragment() {

    private val viewModel: WorkoutsViewModel by viewModels()
    private val args: WorkoutDetailFragmentArgs by navArgs()

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
        return binding.root.also {
            setupExercisesList()
            setupEditButton()
            setupDeleteButton()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            workoutName.text = args.workout.name
            workoutLength.text =
                view.context.getString(R.string.x_minutes_total, args.workout.getTotalMinutes())
            repeatFor.text =
                view.context.getString(R.string.repeat_for_x_sets, args.workout.numSets)
        }
    }

    private fun setupExercisesList() {
        viewManager = LinearLayoutManager(context)
        workoutDetailAdapter = WorkoutDetailExercisesAdapter(args.workout.exercises)

        binding.listExercises.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = workoutDetailAdapter
        }
    }

    private fun setupPlayButton() {
        binding.playButton.setOnClickListener {
            findNavController().navigate(
                WorkoutDetailFragmentDirections.actionWorkoutDetailFragmentToTimerCountdownFragment()
            )
        }
    }

    private fun setupEditButton() {
        binding.editButton.setOnClickListener {
            findNavController().navigate(
                WorkoutDetailFragmentDirections.actionWorkoutDetailFragmentToAddOrEditWorkoutFragment(
                    args.workout
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
                        viewModel.deleteWorkout(args.workout)
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