package com.sherryyuan.emomtimer.workout.addnewworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.ItemAddExerciseBinding
import com.sherryyuan.emomtimer.models.Exercise

class AddNewWorkoutExercisesAdapter(
    private val exercises: List<Exercise>
) : RecyclerView.Adapter<AddNewWorkoutExercisesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAddExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            binding.exercise = exercise
            binding.minuteOfText.text = binding.root.context.getString(
                if (exercise.numSeconds > 1) R.string.minutes_of else R.string.minute_of
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemAddExerciseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount() = exercises.size
}