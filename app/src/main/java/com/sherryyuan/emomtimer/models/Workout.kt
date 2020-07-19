package com.sherryyuan.emomtimer.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Workout(
    val name: String,
    val numSets: Int,
    val exercises: List<Exercise>
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun getFormattedTime(): String = exercises.sumBy { it.numSeconds }.toString()
}