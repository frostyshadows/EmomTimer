package com.sherryyuan.emomtimer.workout.repository

import androidx.lifecycle.LiveData
import com.sherryyuan.emomtimer.ApplicationDatabase
import com.sherryyuan.emomtimer.models.Workout
import org.koin.core.KoinComponent
import org.koin.core.get

class WorkoutRepository : KoinComponent {

    private val roomDao: WorkoutDao = get<ApplicationDatabase>().workoutDao()

    fun getWorkouts(): LiveData<List<Workout>> = roomDao.getAll()

    suspend fun addWorkout(workout: Workout) = roomDao.insertWorkout(workout)

    suspend fun updateWorkout(workout: Workout) =
        roomDao.updateWorkout(workout.name, workout.numSets, workout.exercises, workout.id)

    suspend fun deleteWorkout(workout: Workout) = roomDao.deleteWorkout(workout)
}