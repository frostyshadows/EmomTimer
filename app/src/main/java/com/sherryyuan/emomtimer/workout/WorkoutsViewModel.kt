package com.sherryyuan.emomtimer.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.workout.repository.WorkoutRepository
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class WorkoutsViewModel : ViewModel(), KoinComponent {

    private val repository: WorkoutRepository by inject()

    val workouts: LiveData<List<Workout>> = repository.getWorkouts()

    fun saveWorkout(newWorkout: Workout, prevWorkout: Workout?) {
        viewModelScope.launch {
            // If we have a workout in the navArgs, we know we're editing an existing
            // workout, so update the existing workout instead of add a new one.
            if (prevWorkout != null) {
                newWorkout.id = prevWorkout.id
                repository.updateWorkout(newWorkout)
            } else {
                repository.addWorkout(newWorkout)
            }
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.deleteWorkout(workout)
        }
    }
}