package com.sherryyuan.emomtimer.workout.workoutdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.FragmentWorkoutDetailBinding
import kotlinx.android.synthetic.main.fragment_workout_detail.*
import kotlinx.android.synthetic.main.item_detail_exercise.*

class WorkoutDetailFragment : Fragment() {

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
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            workoutName.text = args.workout.name
            workoutLength.text =
                view.context.getString(R.string.x_minutes_total, args.workout.getFormattedTime())
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
}