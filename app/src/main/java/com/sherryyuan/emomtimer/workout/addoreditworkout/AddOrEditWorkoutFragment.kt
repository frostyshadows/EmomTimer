package com.sherryyuan.emomtimer.workout.addoreditworkout

import android.app.AlertDialog
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
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.FragmentAddOrEditWorkoutBinding
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.utils.SECONDS_PER_MINUTE
import com.sherryyuan.emomtimer.workout.WorkoutsViewModel
import org.koin.core.KoinComponent

class AddOrEditWorkoutFragment : Fragment(), KoinComponent {

    private val navArgs: AddOrEditWorkoutFragmentArgs by navArgs()
    private val viewModel: WorkoutsViewModel by viewModels()

    private val exercises: MutableList<Exercise> by lazy {
        navArgs.workout?.exercises?.toMutableList() ?: mutableListOf()
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
        navArgs.workout?.let { setupWorkoutDetails(it) }
        setupExercisesList()
        setupAddExerciseButton()
        setupSaveButton()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideSoftKeyboard()
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
            layoutManager = viewManager
            adapter = viewAdapterAddOrEditWorkout
        }
    }

    private fun setupAddExerciseButton() {
        binding.addExerciseButton.setOnClickListener {
            exercises.add(Exercise(numSeconds = SECONDS_PER_MINUTE))
            viewAdapterAddOrEditWorkout.notifyItemInserted(exercises.indices.last)
            binding.listExercises.smoothScrollToPosition(viewAdapterAddOrEditWorkout.itemCount)
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            val title: String? = binding.titleText.text?.toString()
            val numSets: Int = binding.setsCountText.text.toString().toIntOrNull() ?: 0
            val filledExercises =
                exercises.filter { it.name.isNotEmpty() && it.numSeconds > 0 && it.numReps > 0 }
            if (!title.isNullOrBlank() && numSets > 0 && filledExercises.isNotEmpty()) {
                saveWorkout(title, numSets, filledExercises)
            } else {
                showMissingFieldsDialog()
            }
        }
    }

    private fun saveWorkout(title: String, numSets: Int, filledExercises: List<Exercise>) {
        val workout = Workout(title, numSets, filledExercises)
        viewModel.saveWorkout(newWorkout = workout, prevWorkout = navArgs.workout)
        hideSoftKeyboard()
        findNavController().navigate(
            AddOrEditWorkoutFragmentDirections.actionAddOrEditWorkoutBackToWorkouts()
        )
    }

    private fun showMissingFieldsDialog() {
        activity?.let {
            AlertDialog.Builder(it)
                .setMessage(R.string.alert_fill_out_fields)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
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