package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.MILLIS_PER_SECOND
import com.sherryyuan.emomtimer.timer.TimerViewData

class SimpleTimerViewModel(
    private val numSecondsPerSet: Int,
    private val numSets: Int
) : TimerViewModel() {

    override val _timerViewData: MutableLiveData<TimerViewData> =
        MutableLiveData(
            TimerViewData(
                timerName = null,
                totalSecondsInSet = numSecondsPerSet,
                secondsRemainingInSet = numSecondsPerSet,
                currentSet = 0,
                totalSets = numSets
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
                currentSet = currentSet + 1,
                totalSets = numSets
            )
        super.startNextExercise()
    }

    override fun getTotalRemainingMillis(): Long {
        _timerViewData.value?.let { timerViewData ->
            val remainingMillisInSet = timerViewData.getRemainingMillisInSet()

            // Subtracting 1 because currentSet is 0-indexed
            val remainingSets = timerViewData.totalSets - timerViewData.currentSet - 1
            val remainingMillisInOtherSets =
                remainingSets * timerViewData.totalSecondsInSet * MILLIS_PER_SECOND
            return remainingMillisInSet + remainingMillisInOtherSets
        }
        return 0
    }
}