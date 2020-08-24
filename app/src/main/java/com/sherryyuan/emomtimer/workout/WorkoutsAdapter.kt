package com.sherryyuan.emomtimer.workout

import android.view.LayoutInflater
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemWorkoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            create(navController)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(workouts[position])
    }

    override fun getItemCount() = workouts.size

    inner class ViewHolder(private val binding: ItemWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var workout: Workout

        fun create(navController: NavController) {
            binding.root.setOnClickListener {
                navController.navigate(
                    WorkoutsFragmentDirections.actionWorkoutsToWorkoutDetail(workout)
                )
            }
            binding.playButton.setOnClickListener {
                navController.navigate(
                    WorkoutsFragmentDirections.actionWorkoutsToTimerCountdown(
                        WorkoutTimerViewModelType(workout)
                    )
                )
            }
        }

        fun bind(workout: Workout) {
            this.workout = workout
            binding.apply {
                titleText.text = workout.name
                timeText.text = timeText.context.getString(
                    R.string.x_minutes,
                    workout.getTotalMinutes().toFormattedString()
                )
            }
        }
    }
}
