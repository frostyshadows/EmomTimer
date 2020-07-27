package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.timer.TimerViewData

class WorkoutTimerViewModel(private val workout: Workout) : TimerViewModel() {

    override val timerViewData: LiveData<TimerViewData>
        get() = _timerViewData

    private val _timerViewData: MutableLiveData<TimerViewData> =
        MutableLiveData(
            TimerViewData(
                timerName = workout.name,
                totalSecondsInSet = workout.exercises[0].numSeconds,
                secondsRemainingInSet = workout.exercises[0].numSeconds,
                currentSet = 1,
                currentExerciseName = workout.exercises[0].name,
                currentExerciseReps = workout.exercises[0].numReps,
                nextExerciseName = workout.exercises.getOrNull(1)?.name
            )
        )

    override fun startNextSet() {
        val currentSet: Int = _timerViewData.value?.currentSet ?: 0
        if (currentSet >= workout.exercises.size - 1) {
            _timerViewState.value = TimerViewState.FINISHED
            return
        }
        val currentExercise: Exercise = workout.exercises[currentSet + 1]
        _timerViewData.value =
            TimerViewData(
                timerName = workout.name,
                totalSecondsInSet = currentExercise.numSeconds,
                secondsRemainingInSet = currentExercise.numSeconds,
                currentSet = currentSet + 1,
                currentExerciseName = currentExercise.name,
                currentExerciseReps = currentExercise.numReps,
                nextExerciseName = workout.exercises.getOrNull(currentSet + 2)?.name
            )
    }
}