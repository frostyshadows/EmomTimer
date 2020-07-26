package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.timer.TimerViewData

class SimpleTimerViewModel(
    private val numSecondsPerSet: Int, private val numSets: Int
) : TimerViewModel() {

    override val timerViewData: LiveData<TimerViewData>
        get() = _timerViewData

    private val _timerViewData: MutableLiveData<TimerViewData> =
        MutableLiveData(
            TimerViewData(
                timerName = null,
                totalSecondsInSet = numSecondsPerSet,
                secondsRemainingInSet = numSecondsPerSet,
                currentSet = 1,
                currentExerciseName = null,
                currentExerciseReps = null,
                nextExerciseName = null
            )
        )

    override fun startNextSet() {
        val currentSet: Int = _timerViewData.value?.currentSet ?: 0
        if (currentSet >= numSets) {
            _timerViewState.value =
                TimerViewState.FINISHED
            return
        }
        _timerViewData.value =
            TimerViewData(
                timerName = null,
                totalSecondsInSet = numSecondsPerSet,
                secondsRemainingInSet = numSecondsPerSet,
                currentSet = currentSet,
                currentExerciseName = null,
                currentExerciseReps = null,
                nextExerciseName = null
            )
    }
}