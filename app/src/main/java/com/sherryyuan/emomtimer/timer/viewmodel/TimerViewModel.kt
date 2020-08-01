package com.sherryyuan.emomtimer.timer.viewmodel

import android.os.CountDownTimer
import androidx.annotation.CallSuper
import androidx.lifecycle.*
import com.sherryyuan.emomtimer.MILLIS_PER_SECOND
import com.sherryyuan.emomtimer.timer.TimerViewData

abstract class TimerViewModel : ViewModel() {

    val timerViewData: LiveData<TimerViewData>
        get() = _timerViewData
    val timerViewState: LiveData<TimerViewState>
        get() = _timerViewState
    protected abstract val _timerViewData: MutableLiveData<TimerViewData>
    protected var _timerViewState: MutableLiveData<TimerViewState> = MutableLiveData(
        TimerViewState.NOT_STARTED
    )

    private var timer: CountDownTimer? = null

    /**
     * Subclasses should update the view model data then call this at the end of the function once
     * view model reflects the next set.
     */
    @CallSuper
    open fun startNextExercise() {
        setupTimer(_timerViewData.value?.getRemainingMillisInSet() ?: 0L)
        timer?.start()
    }

    fun start() {
        setupTimer(_timerViewData.value?.getRemainingMillisInSet() ?: 0L)
        timer?.start()
        _timerViewState.value = TimerViewState.RUNNING
    }

    abstract fun getTotalRemainingSeconds(): Int

    /**
     * Make sure timer also gets cancelled when user navigates away from the countdown fragment.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        timer?.cancel()
        _timerViewState.value = TimerViewState.PAUSED
    }

    fun resume() {
        setupTimer(_timerViewData.value?.getRemainingMillisInSet() ?: 0L)
        timer?.start()
        _timerViewState.value = TimerViewState.RUNNING
    }

    private fun setupTimer(millisRemaining: Long) {
        timer = object : CountDownTimer(millisRemaining, MILLIS_PER_SECOND.toLong()) {

            override fun onTick(millisUntilFinished: Long) {
                _timerViewData.value = _timerViewData.value?.copy(
                    secondsRemainingInSet = millisUntilFinished.toInt() / MILLIS_PER_SECOND
                )
            }

            override fun onFinish() {
                startNextExercise()
            }
        }
    }
}