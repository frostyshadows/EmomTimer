package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.timer.TimerViewData

class SimpleTabataTimerViewModel(
    private val numWorkSecondsPerRound: Int,
    private val numRestSecondsPerRound: Int,
    private val numRounds: Int,
    private var roundType: RoundType
) : TimerViewModel() {

    override val _timerViewData: MutableLiveData<TimerViewData> =
        MutableLiveData(
            TimerViewData(
                timerName = null,
                totalSecondsInRound = if (roundType == RoundType.WORK) numWorkSecondsPerRound else numRestSecondsPerRound,
                secondsRemainingInRound = if (roundType == RoundType.WORK) numWorkSecondsPerRound else numRestSecondsPerRound,
                currentRound = 0,
                totalRounds = numRounds
            )
        )

    override fun startNextExercise(startTimer: Boolean) {
        val currentRound: Int = _timerViewData.value?.currentRound ?: 0
        if (currentRound >= numRounds - 1) {
            finish()
            return
        }
        roundType = when (roundType) {
            RoundType.WORK -> RoundType.REST
            RoundType.REST -> RoundType.WORK
        }
        val nextRound: Int = when (roundType) {
            RoundType.WORK -> currentRound + 1
            RoundType.REST -> currentRound
        }
        _timerViewData.value =
            TimerViewData(
                timerName = null,
                totalSecondsInRound = if (roundType == RoundType.WORK) numWorkSecondsPerRound else numRestSecondsPerRound,
                secondsRemainingInRound = if (roundType == RoundType.WORK) numWorkSecondsPerRound else numRestSecondsPerRound,
                currentRound = nextRound,
                totalRounds = numRounds
            )
        super.startNextExercise(startTimer)
    }

    override fun restartExercise(startTimer: Boolean) {
        _timerViewData.value =
            TimerViewData(
                timerName = null,
                totalSecondsInRound = numRestSecondsPerRound,
                secondsRemainingInRound = numRestSecondsPerRound,
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
            var remainingSecondsInOtherRounds =
                remainingRounds * (numWorkSecondsPerRound + numRestSecondsPerRound)
            if (roundType == RoundType.WORK) {
                remainingSecondsInOtherRounds += numRestSecondsPerRound
            }
            return remainingMillisInRound + remainingSecondsInOtherRounds
        }
        return 0
    }

    override fun sayFirstExercise() {
        _timerViewData.value?.let { timerViewData ->
            audioPlayer.speak(
                resourcesProvider.getString(
                    R.string.simple_tabata_timer_next_exercise_work,
                    timerViewData.currentRound + 1,
                    timerViewData.totalRounds
                )
            )
        }
    }

    override fun sayNextExercise() {
        _timerViewData.value?.let { timerViewData ->
            if (timerViewData.currentRound + 1 < timerViewData.totalRounds) {
                when (roundType) {
                    RoundType.WORK -> {
                        sayNextExerciseForWorkRound(
                            timerViewData.currentRound,
                            timerViewData.totalRounds
                        )
                    }
                    RoundType.REST -> {
                        sayNextExerciseForRestRound(
                            timerViewData.currentRound,
                            timerViewData.totalRounds
                        )
                    }
                }
            }
        }
    }

    private fun sayNextExerciseForWorkRound(currentRound: Int, totalRounds: Int) {
        audioPlayer.speak(
            resourcesProvider.getString(
                R.string.simple_tabata_timer_next_exercise_rest,
                currentRound + 1,
                totalRounds
            )
        )

    }

    private fun sayNextExerciseForRestRound(currentRound: Int, totalRounds: Int) {
        if (currentRound + 1 < totalRounds) {
            audioPlayer.speak(
                resourcesProvider.getString(
                    R.string.simple_tabata_timer_next_exercise_work,
                    currentRound + 2,
                    totalRounds
                )
            )

        }
    }
}

enum class RoundType {
    WORK,
    REST
}