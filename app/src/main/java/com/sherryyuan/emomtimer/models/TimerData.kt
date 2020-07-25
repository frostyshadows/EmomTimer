package com.sherryyuan.emomtimer.models

class TimerData(
    val timerName: String?,
    val totalSecondsInSet: Int,
    val secondsRemainingInSet: Int,
    val currentSet: Int,
    val currentExerciseName: String?,
    val currentExerciseReps: Int?,
    val nextExerciseName: String?
)