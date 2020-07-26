package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelType.SimpleTimerViewModelType
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelType.WorkoutTimerViewModelType

class TimerViewModelFactory(private val type: TimerViewModelType) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (type) {
            is SimpleTimerViewModelType ->
                SimpleTimerViewModel(type.numSecondsPerSet, type.numSets)
            is WorkoutTimerViewModelType ->
                WorkoutTimerViewModel(type.workout)
        } as T
    }
}