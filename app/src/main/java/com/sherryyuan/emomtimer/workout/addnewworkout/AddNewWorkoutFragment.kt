package com.sherryyuan.emomtimer.workout.addnewworkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.databinding.FragmentAddNewWorkoutBinding
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.workout.WorkoutContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class AddNewWorkoutFragment : Fragment(), KoinComponent {

    private val exercises: MutableList<Exercise> = mutableListOf(Exercise())

    private val repository: WorkoutContract.Repository by inject()

    private val binding: FragmentAddNewWorkoutBinding by lazy {
        FragmentAddNewWorkoutBinding.inflate(layoutInflater)
    }

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapterAddNewWorkout: AddNewWorkoutExercisesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root.also {
            setupExercisesList()
            setupAddExerciseButton()
            setupCloseButton()
            setupSaveButton()
        }
    }

    private fun setupExercisesList() {
        viewManager = LinearLayoutManager(context)
        viewAdapterAddNewWorkout = AddNewWorkoutExercisesAdapter(exercises)

        binding.listExercises.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapterAddNewWorkout
        }
    }

    private fun setupAddExerciseButton() {
        binding.addExerciseButton.setOnClickListener {
            exercises.add(Exercise())
            viewAdapterAddNewWorkout.notifyDataSetChanged()
        }
    }

    private fun setupCloseButton() {
        binding.closeButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            val title: String? = binding.titleText.text?.toString()
            val numSets: Int = binding.setsCountText.text.toString().toIntOrNull() ?: 0
            val filledExercises =
                exercises.filter { it.name.isNotEmpty() && it.numSeconds > 0 && it.numReps > 0 }
            if (!title.isNullOrBlank() && filledExercises.isNotEmpty() && numSets > 0) {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.saveWorkout(Workout(title, numSets, filledExercises))
                }
            }
            findNavController().popBackStack()
        }
    }
}