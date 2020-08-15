package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.R
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

    override fun startNextExercise(startTimer: Boolean) {
        val currentSet: Int = _timerViewData.value?.currentSet ?: 0
        if (currentSet >= numSets - 1) {
            finish()
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
        super.startNextExercise(startTimer)
    }

    override fun restartExercise(startTimer: Boolean) {
        _timerViewData.value =
            TimerViewData(
                timerName = null,
                totalSecondsInSet = numSecondsPerSet,
                secondsRemainingInSet = numSecondsPerSet,
                currentSet = _timerViewData.value?.currentSet ?: 0,
                totalSets = numSets
            )
        super.restartExercise(startTimer)
    }

    override fun getTotalRemainingSeconds(): Int {
        _timerViewData.value?.let { timerViewData ->
            val remainingMillisInSet = timerViewData.secondsRemainingInSet

            // Subtracting 1 because currentSet is 0-indexed.
            val remainingSets = timerViewData.totalSets - timerViewData.currentSet - 1
            val remainingSecondsInOtherSets = remainingSets * timerViewData.totalSecondsInSet
            return remainingMillisInSet + remainingSecondsInOtherSets
        }
        return 0
    }

    override fun sayNextExercise() {
        _timerViewData.value?.let { timerViewData ->
            if (timerViewData.currentSet + 1 < timerViewData.totalSets) {
                audioPlayer.speak(
                    resourcesProvider.getString(
                        R.string.simple_timer_next_exercise,
                        timerViewData.currentSet + 2,
                        timerViewData.totalSets
                    )
                )
            }
        }
    }
}