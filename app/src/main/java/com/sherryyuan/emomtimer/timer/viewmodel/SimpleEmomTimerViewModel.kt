package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.timer.TimerViewData

class SimpleEmomTimerViewModel(
    private val numSecondsPerRound: Int,
    private val numRounds: Int
) : TimerViewModel() {

    override val _timerViewData: MutableLiveData<TimerViewData> =
        MutableLiveData(
            TimerViewData(
                timerName = null,
                totalSecondsInRound = numSecondsPerRound,
                secondsRemainingInRound = numSecondsPerRound,
                currentRound = 0,
                totalRounds = numRounds
            )
        )

    override fun startNextExercise(startTimer: Boolean) {
        val currentSet: Int = _timerViewData.value?.currentRound ?: 0
        if (currentSet >= numRounds - 1) {
            finish()
            return
        }
        _timerViewData.value =
            TimerViewData(
                timerName = null,
                totalSecondsInRound = numSecondsPerRound,
                secondsRemainingInRound = numSecondsPerRound,
                currentRound = currentSet + 1,
                totalRounds = numRounds
            )
        super.startNextExercise(startTimer)
    }

    override fun restartExercise(startTimer: Boolean) {
        _timerViewData.value =
            TimerViewData(
                timerName = null,
                totalSecondsInRound = numSecondsPerRound,
                secondsRemainingInRound = numSecondsPerRound,
                currentRound = _timerViewData.value?.currentRound ?: 0,
                totalRounds = numRounds
            )
        super.restartExercise(startTimer)
    }

    override fun getTotalRemainingSeconds(): Int {
        _timerViewData.value?.let { timerViewData ->
            val remainingMillisInRound = timerViewData.secondsRemainingInRound

            // Subtracting 1 because currentRound is 0-indexed.
            val remainingRounds = timerViewData.totalRounds - timerViewData.currentRound - 1
            val remainingSecondsInOtherRounds = remainingRounds * timerViewData.totalSecondsInRound
            return remainingMillisInRound + remainingSecondsInOtherRounds
        }
        return 0
    }

    override fun sayFirstExercise() {
        _timerViewData.value?.let { timerViewData ->
            audioPlayer.speak(
                resourcesProvider.getString(
                    R.string.simple_emom_timer_next_exercise,
                    timerViewData.currentRound + 1,
                    timerViewData.totalRounds
                )
            )
        }
    }

    override fun sayNextExercise() {
        _timerViewData.value?.let { timerViewData ->
            if (timerViewData.currentRound + 1 < timerViewData.totalRounds) {
                audioPlayer.speak(
                    resourcesProvider.getString(
                        R.string.simple_emom_timer_next_exercise,
                        timerViewData.currentRound + 2,
                        timerViewData.totalRounds
                    )
                )
            }
        }
    }
}