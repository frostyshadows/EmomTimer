package com.sherryyuan.emomtimer.workout

import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.models.Workout
import io.reactivex.Single

interface WorkoutContract {

    interface View {
        val viewModel: ViewModel
    }

    interface ViewModel {
        var workouts: MutableLiveData<List<Workout>>
    }

    interface Repository {
        fun getWorkouts(): Single<List<Workout>>
    }
}