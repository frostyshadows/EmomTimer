package com.sherryyuan.emomtimer.workout.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sherryyuan.emomtimer.models.ExerciseConverters
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.workout.repository.WorkoutDao

@Database(entities = [Workout::class], version = 2)
@TypeConverters(ExerciseConverters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
}