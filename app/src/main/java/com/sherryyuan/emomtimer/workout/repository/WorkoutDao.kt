package com.sherryyuan.emomtimer.workout.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sherryyuan.emomtimer.models.Workout

@Dao
interface WorkoutDao {

    @Query(value = "SELECT * FROM Workout")
    fun getAll(): LiveData<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout)
}