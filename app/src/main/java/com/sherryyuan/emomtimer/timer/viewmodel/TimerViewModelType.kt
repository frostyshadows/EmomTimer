package com.sherryyuan.emomtimer.timer.viewmodel

import com.sherryyuan.emomtimer.models.Workout

sealed class TimerViewModelType {

    data class SimpleTimerViewModelType(val numSecondsPerSet: Int, val numSets: Int) :
        TimerViewModelType()

    data class WorkoutTimerViewModelType(val workout: Workout) : TimerViewModelType()
}