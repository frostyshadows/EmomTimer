package com.sherryyuan.emomtimer.workout.workoutdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.SECONDS_PER_MINUTE
import com.sherryyuan.emomtimer.databinding.ItemDetailExerciseBinding
import com.sherryyuan.emomtimer.models.Exercise

class WorkoutDetailExercisesAdapter(
    private val exercises: List<Exercise>
) : RecyclerView.Adapter<WorkoutDetailExercisesAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemDetailExerciseBinding = ItemDetailExerciseBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail_exercise, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.binding.exerciseDescriptionText.text = holder.binding.root.context.getString(
            R.string.x_minute_of_y_exercise,
            exercise.numSeconds.toDouble() / SECONDS_PER_MINUTE,
            exercise.numReps,
            exercise.name
        )
    }

    override fun getItemCount() = exercises.size
}