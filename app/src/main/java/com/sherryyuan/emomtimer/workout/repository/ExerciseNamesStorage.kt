package com.sherryyuan.emomtimer.workout.repository

import android.content.Context
import android.content.SharedPreferences
import com.sherryyuan.emomtimer.PREFS_NAME

private const val PREF_EXERCISE_NAMES = "exercise_names"

class ExerciseNamesStorage(context: Context) {

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun addExerciseNames(exerciseNames: List<String>) {
        val currentExerciseNames = sharedPrefs.getStringSet(PREF_EXERCISE_NAMES, hashSetOf())
        currentExerciseNames?.addAll(exerciseNames)
        val editor = sharedPrefs.edit()
        editor.putStringSet(PREF_EXERCISE_NAMES, currentExerciseNames)
        editor.apply()
        editor.commit()
    }

    fun getAllExerciseNames(): Set<String> =
        sharedPrefs.getStringSet(PREF_EXERCISE_NAMES, hashSetOf()) ?: hashSetOf()
}