package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.timer.TimerViewData

class WorkoutTimerViewModel(private val workout: Workout) : TimerViewModel() {

    override val _timerViewData: MutableLiveData<TimerViewData> =
        MutableLiveData(
            TimerViewData(
                timerName = workout.name,
                totalSecondsInSet = workout.exercises[0].numSeconds,
                secondsRemainingInSet = workout.exercises[0].numSeconds,
                currentSet = 0,
                totalSets = workout.numSets,
                currentExercise = 0,
                currentExerciseName = workout.exercises[0].name,
                currentExerciseReps = workout.exercises[0].numReps,
                nextExerciseName = workout.exercises.getOrNull(1)?.name,
                nextExerciseReps = workout.exercises.getOrNull(1)?.numReps
            )
        )

    override fun startNextExercise() {
        var currentExerciseIndex: Int = _timerViewData.value?.currentExercise ?: 0
        var currentSet: Int = _timerViewData.value?.currentSet ?: 0
        if (currentExerciseIndex >= workout.exercises.size - 1) {
            if (currentSet >= workout.numSets - 1) {
                _timerViewState.value = TimerViewState.FINISHED
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
                totalSecondsInSet = currentExercise.numSeconds,
                secondsRemainingInSet = currentExercise.numSeconds,
                currentSet = currentSet,
                totalSets = workout.numSets,
                currentExercise = currentExerciseIndex,
                currentExerciseName = currentExercise.name,
                currentExerciseReps = currentExercise.numReps,
                nextExerciseName = workout.exercises.getOrNull(currentExerciseIndex + 1)?.name,
                nextExerciseReps = workout.exercises.getOrNull(currentExerciseIndex + 1)?.numReps
            )
        super.startNextExercise()
    }

    override fun getTotalRemainingSeconds(): Int {
        _timerViewData.value?.let { timerViewData ->
            val remainingSecondsInExercise = timerViewData.secondsRemainingInSet

            // Sum up the seconds in the exercises remaining in the set.
            val remainingSecondsInSet = workout.exercises
                .filterIndexed { index, _ -> index > timerViewData.currentExercise ?: 0 }
                .sumBy { it.numSeconds }

            // Subtracting 1 because currentSet is 0-indexed.
            val remainingSets = timerViewData.totalSets - timerViewData.currentSet - 1
            val remainingSecondsInOtherSets =
                remainingSets * workout.exercises.sumBy { it.numSeconds }
            return remainingSecondsInExercise + remainingSecondsInSet + remainingSecondsInOtherSets
        }
        return 0
    }
}