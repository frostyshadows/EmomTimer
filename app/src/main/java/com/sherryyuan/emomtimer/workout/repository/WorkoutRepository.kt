package com.sherryyuan.emomtimer.workout.repository

import androidx.lifecycle.LiveData
import com.sherryyuan.emomtimer.ApplicationDatabase
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.workout.WorkoutContract
import org.koin.core.KoinComponent
import org.koin.core.get

class WorkoutRepository: WorkoutContract.Repository, KoinComponent {

    private val roomDao: WorkoutDao = get<ApplicationDatabase>().workoutDao()

    override fun getWorkouts(): LiveData<List<Workout>> = roomDao.getAll()

}