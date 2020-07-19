package com.sherryyuan.emomtimer.workout.addoreditworkout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.databinding.FragmentAddOrEditWorkoutBinding
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.workout.WorkoutsViewModel
import org.koin.core.KoinComponent

class AddOrEditWorkoutFragment : Fragment(), KoinComponent {

    private val viewModel: WorkoutsViewModel by viewModels()
    private val args: AddOrEditWorkoutFragmentArgs by navArgs()

    private val exercises: MutableList<Exercise> by lazy {
        args.workout?.exercises?.toMutableList() ?: mutableListOf()
    }

    private val binding: FragmentAddOrEditWorkoutBinding by lazy {
        FragmentAddOrEditWorkoutBinding.inflate(layoutInflater)
    }

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapterAddOrEditWorkout: AddOrEditWorkoutExercisesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root.also {
            args.workout?.let { setupWorkoutDetails(it) }
            setupExercisesList()
            setupAddExerciseButton()
            setupSaveButton()
        }
    }

    private fun setupWorkoutDetails(workout: Workout) {
        binding.apply {
            titleText.setText(workout.name)
            setsCountText.setText(workout.numSets.toString())
        }
    }

    private fun setupExercisesList() {
        viewManager = LinearLayoutManager(context)
        viewAdapterAddOrEditWorkout = AddOrEditWorkoutExercisesAdapter(exercises)

        binding.listExercises.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapterAddOrEditWorkout
        }
    }

    private fun setupAddExerciseButton() {
        binding.addExerciseButton.setOnClickListener {
            exercises.add(Exercise())
            viewAdapterAddOrEditWorkout.notifyDataSetChanged()
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            saveWorkout()
            hideSoftKeyboard()
            findNavController().navigate(
                AddOrEditWorkoutFragmentDirections
                    .actionAddOrEditWorkoutFragmentBackToWorkoutsFragment()
            )
        }
    }

    // If all the workout details are filled, save the workout in the repository.
    private fun saveWorkout() {
        val title: String? = binding.titleText.text?.toString()
        val numSets: Int = binding.setsCountText.text.toString().toIntOrNull() ?: 0
        val filledExercises =
            exercises.filter { it.name.isNotEmpty() && it.numSeconds > 0 && it.numReps > 0 }
        if (!title.isNullOrBlank() && filledExercises.isNotEmpty() && numSets > 0) {
            val workout = Workout(title, numSets, filledExercises)
            viewModel.saveWorkout(newWorkout = workout, prevWorkout = args.workout)
        }
    }

    private fun hideSoftKeyboard() {
        activity?.let { activity ->
            val view: View? = activity.currentFocus
            if (view != null) {
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}