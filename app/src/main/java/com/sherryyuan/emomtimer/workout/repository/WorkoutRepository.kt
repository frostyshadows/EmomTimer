package com.sherryyuan.emomtimer.workout.repository

import androidx.lifecycle.LiveData
import com.sherryyuan.emomtimer.models.Workout
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject

class WorkoutRepository : KoinComponent {

    private val roomDao: WorkoutDao = get<ApplicationDatabase>().workoutDao()
    private val exerciseNameStorage by inject<ExerciseNamesStorage>()

    fun getWorkouts(): LiveData<List<Workout>> = roomDao.getAll()

    suspend fun addWorkout(workout: Workout) = roomDao.insertWorkout(workout)

    suspend fun addWorkouts(workouts: List<Workout>) = roomDao.insertWorkouts(workouts).also {
        exerciseNameStorage.addExerciseNames(
            workouts.flatMap { workout ->
                workout.exercises.map { it.name }
            }
        )
    }

    suspend fun updateWorkout(workout: Workout) =
        roomDao.updateWorkout(workout.name, workout.numRounds, workout.exercises, workout.id).also {
            exerciseNameStorage.addExerciseNames(workout.exercises.map { it.name })
        }

    suspend fun deleteWorkout(workout: Workout) = roomDao.deleteWorkout(workout)
}