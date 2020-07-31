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
        }
        val currentExercise: Exercise = workout.exercises[currentExerciseIndex]
        _timerViewData.value =
            TimerViewData(
                timerName = workout.name,
                totalSecondsInSet = currentExercise.numSeconds,
                secondsRemainingInSet = currentExercise.numSeconds,
                currentSet = currentSet + 1,
                currentExercise = currentExerciseIndex,
                currentExerciseName = currentExercise.name,
                currentExerciseReps = currentExercise.numReps,
                nextExerciseName = workout.exercises.getOrNull(currentExerciseIndex + 1)?.name,
                nextExerciseReps = workout.exercises.getOrNull(currentExerciseIndex + 1)?.numReps
            )
        super.startNextExercise()
    }
}