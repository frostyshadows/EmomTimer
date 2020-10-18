package com.sherryyuan.emomtimer.workout.repository

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE WorkoutNew (id INTEGER NOT NULL PRIMARY KEY, name TEXT NOT NULL, numRounds INTEGER NOT NULL, exercises TEXT NOT NULL)"
        )
        database.execSQL(
            "INSERT INTO WorkoutNew (id, name, numRounds, exercises) SELECT id, name, numSets, exercises FROM Workout"
        )
        database.execSQL("DROP TABLE Workout")
        database.execSQL("ALTER TABLE WorkoutNew RENAME TO Workout")
    }
}