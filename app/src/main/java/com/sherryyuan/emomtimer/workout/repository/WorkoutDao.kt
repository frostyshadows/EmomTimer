package com.sherryyuan.emomtimer.workout.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout

@Dao
interface WorkoutDao {

    @Query(value = "SELECT * FROM Workout")
    fun getAll(): LiveData<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout)

    @Query(value = "UPDATE Workout SET name= :name, numSets= :numSets, exercises = :exercises WHERE id = :id")
    suspend fun updateWorkout(name: String, numSets: Int, exercises: List<Exercise>, id: Int)

    @Delete
    suspend fun deleteWorkout(workout: Workout)
}