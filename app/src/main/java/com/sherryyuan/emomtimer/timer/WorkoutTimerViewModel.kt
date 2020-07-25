package com.sherryyuan.emomtimer.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout

class WorkoutTimerViewModel(private val workout: Workout) : TimerViewModel() {

    override val secondsRemainingInSet: LiveData<Int>
        get() = _secondsRemainingInSet
    override val timerName: String? = workout.name
    override val currentExerciseName: LiveData<String>
        get() = _currentExerciseName
    override val currentExerciseReps: LiveData<Int>?
        get() = _currentExerciseReps
    override val nextExerciseName: LiveData<String?>?
        get() = _nextExerciseName

    override fun startNextSet() {
        // Increment the current set and update state to FINISHED if we've completed the final
        // exercise.
        _currentSet.value = _currentSet.value?.plus(1)
        if (_currentSet.value ?: 0 > workout.exercises.size) {
            _timerState.value = TimerState.FINISHED
        }
        // Update the data associated with the current exercise and reset the seconds remaining.
        val currentExercise: Exercise = workout.exercises[requireNotNull(_currentSet.value)]
        _secondsRemainingInSet.value = currentExercise.numSeconds
        _currentExerciseName.value = currentExercise.name
        _currentExerciseReps.value = currentExercise.numReps
        // Update the next exercise name.
        _nextExerciseName.value =
            workout.exercises.getOrNull(requireNotNull(_currentSet.value) + 1)?.name
    }

    private val _secondsRemainingInSet: MutableLiveData<Int> =
        MutableLiveData(workout.exercises[0].numSeconds)

    private val _currentExerciseName: MutableLiveData<String> =
        MutableLiveData(workout.exercises[0].name)

    private val _currentExerciseReps: MutableLiveData<Int> =
        MutableLiveData(workout.exercises[0].numReps)

    private val _nextExerciseName: MutableLiveData<String?> =
        MutableLiveData(workout.exercises.getOrNull(1)?.name)
}