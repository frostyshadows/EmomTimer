package com.sherryyuan.emomtimer.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sherryyuan.emomtimer.minutesToTimeString
import java.io.Serializable

@Entity
@TypeConverters(ExerciseConverters::class)
data class Workout(
    val name: String,
    val numSets: Int,
    val exercises: List<Exercise>
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun getFormattedTime(): String = exercises.sumByDouble { it.numMinutes.toNonNullDouble() }.toString()
}