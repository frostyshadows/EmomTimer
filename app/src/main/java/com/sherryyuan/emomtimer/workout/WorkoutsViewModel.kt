package com.sherryyuan.emomtimer.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sherryyuan.emomtimer.models.Workout
import org.koin.core.KoinComponent
import org.koin.core.inject

class WorkoutsViewModel : ViewModel(), WorkoutContract.ViewModel, KoinComponent {

    private val repository: WorkoutContract.Repository by inject()

    override val workouts: LiveData<List<Workout>> = repository.getWorkouts()
}