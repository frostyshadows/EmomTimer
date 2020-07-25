package com.sherryyuan.emomtimer.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sherryyuan.emomtimer.models.TimerData

class SimpleTimerViewModel(
    private val numSecondsPerSet: Int, private val numSets: Int
) : TimerViewModel() {

    //    override val secondsRemainingInSet: LiveData<Int>
//        get() = _secondsRemainingInSet
//    override val timerName: String? = null
//    override var currentExerciseName: LiveData<String>? = null
//    override val currentExerciseReps: LiveData<Int>? = null
//    override var nextExerciseName: LiveData<String?>? = null
//
//    private val _secondsRemainingInSet: MutableLiveData<Int> = MutableLiveData(numSecondsPerSet)
    override val timerData: LiveData<TimerData>
        get() = _timerData

    private val _timerData: MutableLiveData<TimerData> =
        MutableLiveData(
            TimerData(
                timerName = null,
                totalSecondsInSet = numSecondsPerSet,
                secondsRemainingInSet = numSecondsPerSet,
                currentSet = 1,
                currentExerciseName = null,
                currentExerciseReps = null,
                nextExerciseName = null
            )
        )

    //    override fun startNextSet() {
//        _currentSet.value = _currentSet.value?.plus(1)
//        if (_currentSet.value ?: 0 > numSets) {
//            _timerState.value = TimerState.FINISHED
//        }
//        _secondsRemainingInSet.value = numSecondsPerSet
//    }
    override fun startNextSet() {
        val currentSet: Int = _timerData.value?.currentSet ?: 0
        if (currentSet >= numSets) {
            _timerState.value = TimerState.FINISHED
            return
        }
        _timerData.value = TimerData(
            timerName = null,
            totalSecondsInSet = numSecondsPerSet,
            secondsRemainingInSet = numSecondsPerSet,
            currentSet = currentSet,
            currentExerciseName = null,
            currentExerciseReps = null,
            nextExerciseName = null
        )
    }
}