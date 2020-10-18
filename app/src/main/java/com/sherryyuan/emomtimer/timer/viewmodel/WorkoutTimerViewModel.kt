package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.timer.TimerViewData

class WorkoutTimerViewModel(private val workout: Workout) : TimerViewModel() {

    override val _timerViewData: MutableLiveData<TimerViewData> =
        MutableLiveData(
            TimerViewData(
                timerName = workout.name,
                totalSecondsInRound = workout.exercises[0].numSeconds,
                secondsRemainingInRound = workout.exercises[0].numSeconds,
                currentRound = 0,
                totalRounds = workout.numRounds,
                currentExercise = 0,
                currentExerciseName = workout.exercises[0].name,
                currentExerciseReps = workout.exercises[0].numReps,
                nextExerciseName = workout.exercises.getOrNull(1)?.name,
                nextExerciseReps = workout.exercises.getOrNull(1)?.numReps
            )
        )

    override fun startNextExercise(startTimer: Boolean) {
        var currentExerciseIndex: Int = _timerViewData.value?.currentExercise ?: 0
        var currentSet: Int = _timerViewData.value?.currentRound ?: 0
        if (currentExerciseIndex >= workout.exercises.size - 1) {
            if (currentSet >= workout.numRounds - 1) {
                finish()
                return
            } else {
                currentExerciseIndex = 0
                currentSet++
            }
        } else {
            currentExerciseIndex++
        }
        val currentExercise: Exercise = workout.exercises[currentExerciseIndex]
        _timerViewData.value =
            TimerViewData(
                timerName = workout.name,
                totalSecondsInRound = currentExercise.numSeconds,
                secondsRemainingInRound = currentExercise.numSeconds,
                currentRound = currentSet,
                totalRounds = workout.numRounds,
                currentExercise = currentExerciseIndex,
                currentExerciseName = currentExercise.name,
                currentExerciseReps = currentExercise.numReps,
                nextExerciseName = workout.exercises.getOrNull(currentExerciseIndex + 1)?.name,
                nextExerciseReps = workout.exercises.getOrNull(currentExerciseIndex + 1)?.numReps
            )
        super.startNextExercise(startTimer)
    }

    override fun restartExercise(startTimer: Boolean) {
        val currentExerciseIndex: Int = _timerViewData.value?.currentExercise ?: 0
        val currentSet: Int = _timerViewData.value?.currentRound ?: 0
        val currentExercise: Exercise = workout.exercises[currentExerciseIndex]
        _timerViewData.value =
            TimerViewData(
                timerName = workout.name,
                totalSecondsInRound = currentExercise.numSeconds,
                secondsRemainingInRound = currentExercise.numSeconds,
                currentRound = currentSet,
                totalRounds = workout.numRounds,
                currentExercise = currentExerciseIndex,
                currentExerciseName = currentExercise.name,
                currentExerciseReps = currentExercise.numReps,
                nextExerciseName = workout.exercises.getOrNull(currentExerciseIndex + 1)?.name,
                nextExerciseReps = workout.exercises.getOrNull(currentExerciseIndex + 1)?.numReps
            )
        super.restartExercise(startTimer)
    }

    override fun getTotalRemainingSeconds(): Int {
        _timerViewData.value?.let { timerViewData ->
            val remainingSecondsInExercise = timerViewData.secondsRemainingInRound

            // Sum up the seconds in the exercises remaining in the set.
            val remainingSecondsInRound = workout.exercises
                .filterIndexed { index, _ -> index > timerViewData.currentExercise ?: 0 }
                .sumBy { it.numSeconds }

            // Subtracting 1 because currentSet is 0-indexed.
            val remainingRounds = timerViewData.totalRounds - timerViewData.currentRound - 1
            val remainingSecondsInOtherRounds =
                remainingRounds * workout.exercises.sumBy { it.numSeconds }
            return remainingSecondsInExercise + remainingSecondsInRound + remainingSecondsInOtherRounds
        }
        return 0
    }

    override fun sayFirstExercise() {
        val currentExercise: Exercise = workout.exercises[0]
        audioPlayer.speak(
            resourcesProvider.getString(
                R.string.workout_timer_next_exercise,
                currentExercise.numSeconds,
                currentExercise.numReps,
                currentExercise.name
            )
        )
    }

    override fun sayNextExercise() {
        _timerViewData.value?.let { timerViewData ->
            if (timerViewData.currentExercise ?: 0 >= workout.exercises.size - 1) {
                if (timerViewData.currentRound >= workout.numRounds - 1) return
                val currentExercise: Exercise = workout.exercises[0]
                audioPlayer.speak(
                    resourcesProvider.getString(
                        R.string.workout_timer_next_exercise,
                        currentExercise.numSeconds,
                        currentExercise.numReps,
                        currentExercise.name
                    )
                )
                audioPlayer.speak(
                    resourcesProvider.getString(
                        R.string.workout_timer_round_info,
                        timerViewData.currentRound + 2,
                        timerViewData.totalRounds
                    )
                )
            } else {
                val nextExerciseIndex: Int? = timerViewData.currentExercise?.plus(1)
                workout.exercises.getOrNull(nextExerciseIndex ?: 0)?.let { exercise ->
                    audioPlayer.speak(
                        resourcesProvider.getString(
                            R.string.workout_timer_next_exercise,
                            exercise.numSeconds,
                            exercise.numReps,
                            exercise.name
                        )
                    )
                    audioPlayer.speak(
                        resourcesProvider.getString(
                            R.string.workout_timer_round_info,
                            timerViewData.currentRound + 1,
                            timerViewData.totalRounds
                        )
                    )
                }
            }
        }
    }
}