package com.sherryyuan.emomtimer.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SimpleTimerViewModel(
    private val numSecondsPerSet: Int, private val numSets: Int
) : TimerViewModel() {

    override val secondsRemainingInSet: LiveData<Int>
        get() = _secondsRemainingInSet
    override val timerName: String? = null
    override var currentExerciseName: LiveData<String>? = null
    override val currentExerciseReps: LiveData<Int>? = null
    override var nextExerciseName: LiveData<String?>? = null

    private val _secondsRemainingInSet: MutableLiveData<Int> = MutableLiveData(numSecondsPerSet)

    override fun startNextSet() {
        _currentSet.value = _currentSet.value?.plus(1)
        if (_currentSet.value ?: 0 > numSets) {
            _timerState.value = TimerState.FINISHED
        }
        _secondsRemainingInSet.value = numSecondsPerSet
    }
}