package com.sherryyuan.emomtimer.workout.addoreditworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.ItemAddExerciseBinding
import com.sherryyuan.emomtimer.models.Exercise

class AddOrEditWorkoutExercisesAdapter(
    private val exercises: MutableList<Exercise>
) : RecyclerView.Adapter<AddOrEditWorkoutExercisesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAddExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            binding.apply {
                this.exercise = exercise
                minuteOfText.text = binding.root.context.getString(
                    if (exercise.numSeconds > 1) R.string.minutes_of else R.string.minute_of
                )
                deleteButton.setOnClickListener {
                    exercises.remove(exercise)
                    notifyDataSetChanged()
                }
            }
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