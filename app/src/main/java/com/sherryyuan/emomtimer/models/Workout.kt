package com.sherryyuan.emomtimer.models

import androidx.room.Entity
import com.sherryyuan.emomtimer.secondsToTimeString
import java.io.Serializable

@Entity
data class Workout(
    val name: String,
    val numSets: Int,
    val exercises: List<Exercise>
) : Serializable {
    fun getFormattedTime(): String =
        secondsToTimeString(exercises.sumBy { it.numSeconds })
}