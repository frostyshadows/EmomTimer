package com.sherryyuan.emomtimer.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sherryyuan.emomtimer.secondsToTimeString
import java.io.Serializable

@Entity
@TypeConverters(ExerciseConverters::class)
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val numSets: Int,
    val exercises: List<Exercise>
) : Serializable {

    fun getFormattedTime(): String =
        secondsToTimeString(exercises.sumBy { it.numSeconds })
}