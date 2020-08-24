package com.sherryyuan.emomtimer.workout.workoutdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.ItemDetailExerciseBinding
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.utils.SECONDS_PER_MINUTE
import com.sherryyuan.emomtimer.utils.toFormattedString

class WorkoutDetailExercisesAdapter(
    private val exercises: List<Exercise>
) : RecyclerView.Adapter<WorkoutDetailExercisesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemDetailExerciseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount() = exercises.size

    inner class ViewHolder(private val binding: ItemDetailExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            binding.exerciseDescriptionText.text = binding.root.context.resources.getQuantityString(
                R.plurals.x_minutes_of_y_exercise,
                (exercise.numSeconds.toDouble() / SECONDS_PER_MINUTE).toInt(),
                (exercise.numSeconds.toDouble() / SECONDS_PER_MINUTE).toFormattedString(),
                exercise.numReps,
                exercise.name
            )
        }
    }
}