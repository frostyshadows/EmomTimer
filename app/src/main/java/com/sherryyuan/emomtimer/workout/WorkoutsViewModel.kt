package com.sherryyuan.emomtimer.workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sherryyuan.emomtimer.models.Workout

class WorkoutsViewModel : ViewModel(), WorkoutContract.ViewModel {

    override val workouts: LiveData<List<Workout>>
        get() = _workouts

    private val _workouts: MutableLiveData<List<Workout>> = MutableLiveData()

    init {
       _workouts.value = listOf()
    }
}