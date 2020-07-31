package com.sherryyuan.emomtimer.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sherryyuan.emomtimer.SECONDS_PER_MINUTE
import java.io.Serializable

@Entity
data class Workout(
    val name: String,
    val numSets: Int,
    val exercises: List<Exercise>
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun getTotalMinutes(): Int = exercises.sumBy { it.numSeconds } / SECONDS_PER_MINUTE * numSets
}