package com.sherryyuan.emomtimer.workout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout

class WorkoutsViewModel : ViewModel(), WorkoutContract.ViewModel {
    override var workouts: MutableLiveData<List<Workout>> = MutableLiveData()

    init {
        // workouts.value = emptyList()
        workouts.value = listOf(Workout("Cardio", 4, listOf(Exercise("Squats", 60, 30))))
    }
}