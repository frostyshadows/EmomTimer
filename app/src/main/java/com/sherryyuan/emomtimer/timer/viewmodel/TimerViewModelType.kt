package com.sherryyuan.emomtimer.timer.viewmodel

import com.sherryyuan.emomtimer.models.Workout
import java.io.Serializable

/**
 * Class containing the information we need to create an instance of TimerViewModel.
 */
sealed class TimerViewModelType : Serializable {

    // Represents a simple EMOM timer.
    data class SimpleEmomTimerViewModelType(val numSecondsPerRound: Int, val numRounds: Int) :
        TimerViewModelType()

    // Represents a simple Tabata timer.
    data class SimpleTabataTimerViewModelType(
        val numWorkSecondsPerRound: Int,
        val numRestSecondsPerRound: Int,
        val numRounds: Int
    ) :
        TimerViewModelType()

    // Represents a EMOM timer associated with a specific workout.
    data class WorkoutTimerViewModelType(val workout: Workout) : TimerViewModelType()
}