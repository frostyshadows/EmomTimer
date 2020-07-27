package com.sherryyuan.emomtimer.timer

/**
 * Class containing all the information we need to display on the timer countdown UI.
 */
data class TimerViewData(
    val timerName: String?,
    val totalSecondsInSet: Int,
    val secondsRemainingInSet: Int,
    val currentSet: Int,
    val currentExerciseName: String?,
    val currentExerciseReps: Int?,
    val nextExerciseName: String?
)