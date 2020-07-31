package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.timer.TimerViewData

class SimpleTimerViewModel(
    private val numSecondsPerSet: Int, private val numSets: Int
) : TimerViewModel() {

    override val _timerViewData: MutableLiveData<TimerViewData> =
        MutableLiveData(
            TimerViewData(
                timerName = null,
                totalSecondsInSet = numSecondsPerSet,
                secondsRemainingInSet = numSecondsPerSet,
                currentSet = 0
            )
        )

    override fun startNextExercise() {
        val currentSet: Int = _timerViewData.value?.currentSet ?: 0
        if (currentSet >= numSets - 1) {
            _timerViewState.value =
                TimerViewState.FINISHED
            return
        }
        _timerViewData.value =
            TimerViewData(
                timerName = null,
                totalSecondsInSet = numSecondsPerSet,
                secondsRemainingInSet = numSecondsPerSet,
                currentSet = currentSet + 1
            )
        super.startNextExercise()
    }
}