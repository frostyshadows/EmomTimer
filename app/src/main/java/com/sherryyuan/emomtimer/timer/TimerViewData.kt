package com.sherryyuan.emomtimer.timer

import com.sherryyuan.emomtimer.utils.MILLIS_PER_SECOND

/**
 * Class containing all the information we need to display on the timer countdown UI.
 */
data class TimerViewData(
    val timerName: String?,
    val totalSecondsInSet: Int,
    val secondsRemainingInSet: Int,
    val currentSet: Int,
    val totalSets: Int,
    val currentExercise: Int? = null,
    val currentExerciseName: String? = null,
    val currentExerciseReps: Int? = null,
    val nextExerciseName: String? = null,
    val nextExerciseReps: Int? = null
) {
    fun getRemainingMillisInSet(): Long = (secondsRemainingInSet * MILLIS_PER_SECOND).toLong()
}