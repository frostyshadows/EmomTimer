package com.sherryyuan.emomtimer.timer

import com.sherryyuan.emomtimer.MILLIS_PER_SECOND

/**
 * Class containing all the information we need to display on the timer countdown UI.
 */
data class TimerViewData(
    val timerName: String?,
    val totalSecondsInSet: Int,
    val secondsRemainingInSet: Int,
    val currentSet: Int,
    val currentExercise: Int? = null,
    val currentExerciseName: String? = null,
    val currentExerciseReps: Int? = null,
    val nextExerciseName: String? = null,
    val nextExerciseReps: Int? = null
) {
    fun getRemainingMillis(): Long = (secondsRemainingInSet * MILLIS_PER_SECOND).toLong()
}