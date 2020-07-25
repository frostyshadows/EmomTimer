package com.sherryyuan.emomtimer.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sherryyuan.emomtimer.models.TimerData
import kotlinx.coroutines.channels.ticker

abstract class TimerViewModel : ViewModel() {

//    abstract val secondsRemainingInSet: LiveData<Int>
//    val currentSet: LiveData<Int>
//        get() = _currentSet
//    val timerState: LiveData<TimerState>
//        get() = _timerState
//
//    abstract val timerName: String?
//    abstract val currentExerciseName: LiveData<String>?
//    abstract val currentExerciseReps: LiveData<Int>?
//    abstract val nextExerciseName: LiveData<String?>?
//
//    protected var _currentSet: MutableLiveData<Int> = MutableLiveData(0)
    abstract val timerData: LiveData<TimerData>
    protected var _timerState: MutableLiveData<TimerState> = MutableLiveData(TimerState.NOT_STARTED)

    private val tickerChannel = ticker(delayMillis = 1_000, initialDelayMillis = 0)

    abstract fun startNextSet()

    fun start() {
        _timerState.value = TimerState.RUNNING
    }

    fun pause() {
        _timerState.value = TimerState.PAUSED
    }

    fun resume() {
        _timerState.value = TimerState.RUNNING
    }

    suspend fun countDownRemainingSeconds(numSeconds: Int) {
        repeat(numSeconds) {
            tickerChannel.receive()
            val currentTime: Long = System.currentTimeMillis()
            println(currentTime)
        }
    }
}

enum class TimerState {
    NOT_STARTED,
    RUNNING,
    PAUSED,
    FINISHED
}