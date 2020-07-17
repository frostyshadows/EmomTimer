package com.sherryyuan.emomtimer.workout

import androidx.lifecycle.LiveData
import com.sherryyuan.emomtimer.models.Workout

interface WorkoutContract {

    interface View {
        val viewModel: ViewModel
    }

    interface ViewModel {
        val workouts: LiveData<List<Workout>>
    }

    interface Repository {
        fun getWorkouts(): LiveData<List<Workout>>
    }
}