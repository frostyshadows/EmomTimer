package com.sherryyuan.emomtimer.timer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sherryyuan.emomtimer.timer.TimerViewData
import kotlinx.coroutines.channels.ticker

abstract class TimerViewModel : ViewModel() {

    abstract val timerViewData: LiveData<TimerViewData>
    protected var _timerViewState: MutableLiveData<TimerViewState> = MutableLiveData(
        TimerViewState.NOT_STARTED
    )

    private val tickerChannel = ticker(delayMillis = 1_000, initialDelayMillis = 0)

    abstract fun startNextSet()

    fun start() {
        _timerViewState.value =
            TimerViewState.RUNNING
    }

    fun pause() {
        _timerViewState.value =
            TimerViewState.PAUSED
    }

    fun resume() {
        _timerViewState.value =
            TimerViewState.RUNNING
    }

    suspend fun countDownRemainingSeconds(numSeconds: Int) {
        repeat(numSeconds) {
            tickerChannel.receive()
            val currentTime: Long = System.currentTimeMillis()
            println(currentTime)
        }
    }
}