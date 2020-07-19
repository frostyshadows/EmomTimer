package com.sherryyuan.emomtimer

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sherryyuan.emomtimer.models.ExerciseConverters
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.workout.repository.WorkoutDao

@Database(entities = [Workout::class], version = 1)
@TypeConverters(ExerciseConverters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
}