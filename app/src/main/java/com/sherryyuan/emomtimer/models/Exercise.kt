package com.sherryyuan.emomtimer.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class Exercise(
    var name: String = "",
    var numMinutes: String = "",
    var numReps: String = ""
)

fun String.toNonNullDouble(): Double = this.toDoubleOrNull() ?: 0.0
fun String.toNonNullInt(): Int = this.toIntOrNull() ?: 0

class ExerciseConverters {
    private val gson = Gson()

    @TypeConverter
    fun toExerciseListType(data: String): List<Exercise> {
        val listType: Type =
            object : TypeToken<List<Exercise>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromExerciseListType(exercises: List<Exercise>): String {
        return gson.toJson(exercises)
    }
}