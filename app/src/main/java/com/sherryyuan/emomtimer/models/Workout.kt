package com.sherryyuan.emomtimer.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sherryyuan.emomtimer.utils.SECONDS_PER_MINUTE
import java.io.Serializable

@Entity
data class Workout(
    val name: String,
    val numRounds: Int,
    val exercises: List<Exercise>
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun getTotalMinutes(): Double =
        exercises.sumBy { it.numSeconds }.toDouble() / SECONDS_PER_MINUTE * numRounds
}