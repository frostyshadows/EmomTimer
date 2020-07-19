package com.sherryyuan.emomtimer.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

data class Exercise(
    var name: String = "",
    var numSeconds: Int = 0,
    var numReps: Int = 0
) {
    var numSecondsString = if (numSeconds > 0) numSeconds.toString() else ""
    var numRepsString = if (numReps > 0) numReps.toString() else ""
}

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