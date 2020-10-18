package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelType.SimpleEmomTimerViewModelType
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelType.WorkoutTimerViewModelType

class TimerViewModelFactory(private val type: TimerViewModelType) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (type) {
            is SimpleEmomTimerViewModelType ->
                SimpleEmomTimerViewModel(type.numSecondsPerRound, type.numRounds)
            is TimerViewModelType.SimpleTabataTimerViewModelType ->
                SimpleTabataTimerViewModel(
                    type.numWorkSecondsPerRound,
                    type.numRestSecondsPerRound,
                    RoundType.WORK,
                    type.numRounds
                )
            is WorkoutTimerViewModelType ->
                WorkoutTimerViewModel(type.workout)
        } as T
    }
}