package com.sherryyuan.emomtimer.workout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.ItemWorkoutBinding
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelType.WorkoutTimerViewModelType
import com.sherryyuan.emomtimer.utils.toFormattedString

class WorkoutsAdapter(
    var workouts: List<Workout>,
    private val navController: NavController
) : RecyclerView.Adapter<WorkoutsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemWorkoutBinding = ItemWorkoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemWorkoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workout = workouts[position]
        holder.binding.apply {
            root.setOnClickListener {
                navController.navigate(
                    WorkoutsFragmentDirections.actionWorkoutsToWorkoutDetail(workout)
                )
            }
            titleText.text = workout.name
            timeText.text =
                timeText.context.getString(
                    R.string.x_minutes,
                    workout.getTotalMinutes().toFormattedString()
                )
            playButton.setOnClickListener {
                navController.navigate(
                    WorkoutsFragmentDirections.actionWorkoutsToTimerCountdown(
                        WorkoutTimerViewModelType(workout)
                    )
                )
            }
        }
    }

    override fun getItemCount() = workouts.size
}
