package com.sherryyuan.emomtimer.timer

import com.sherryyuan.emomtimer.utils.MILLIS_PER_SECOND

/**
 * Class containing all the information we need to display on the timer countdown UI.
 */
data class TimerViewData(
    val timerName: String?,
    val totalSecondsInRound: Int,
    val secondsRemainingInRound: Int,
    val currentRound: Int,
    val totalRounds: Int,
    val currentExercise: Int? = null,
    val currentExerciseName: String? = null,
    val currentExerciseReps: Int? = null,
    val nextExerciseName: String? = null,
    val nextExerciseReps: Int? = null
) {
    fun getRemainingMillisInSet(): Long = (secondsRemainingInRound * MILLIS_PER_SECOND).toLong()
}