package com.sherryyuan.emomtimer.timer.viewmodel

import android.os.CountDownTimer
import androidx.annotation.CallSuper
import androidx.lifecycle.*
import com.sherryyuan.emomtimer.MILLIS_PER_SECOND
import com.sherryyuan.emomtimer.ResourcesProvider
import com.sherryyuan.emomtimer.AudioPlayer
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.timer.TimerViewData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject

private const val ONE_SECOND = 1000L
private const val TWO_SECONDS = 2000L
private const val THREE_SECONDS = 3000L
private const val EIGHT_SECONDS = 8000L

abstract class TimerViewModel : ViewModel(), KoinComponent {

    val timerViewData: LiveData<TimerViewData>
        get() = _timerViewData
    val timerViewState: LiveData<TimerViewState>
        get() = _timerViewState
    protected abstract val _timerViewData: MutableLiveData<TimerViewData>
    private var _timerViewState: MutableLiveData<TimerViewState> = MutableLiveData(
        TimerViewState.NOT_STARTED
    )

    // Need to use an eager get() because TextToSpeech will only say text if it's fully initialized.
    protected val audioPlayer: AudioPlayer = get()
    protected val resourcesProvider: ResourcesProvider by inject()

    private var timer: CountDownTimer? = null

    override fun onCleared() {
        timer?.cancel()
        timer = null
        audioPlayer.shutdown()
    }

    /**
     * Subclasses should update the view model data then call this at the end of the function once
     * view model reflects the next set.
     */
    @CallSuper
    open fun startNextExercise(startTimer: Boolean = true) {
        audioPlayer.playBeep()
        setupTimer(_timerViewData.value?.getRemainingMillisInSet() ?: 0L)
        if (startTimer) {
            viewModelScope.launch {
                delay(ONE_SECOND)
                timer?.start()
            }
        }
    }

    @CallSuper
    open fun restartExercise(startTimer: Boolean = true) {
        audioPlayer.playBeep()
        setupTimer(_timerViewData.value?.getRemainingMillisInSet() ?: 0L)
        if (startTimer) {
            viewModelScope.launch {
                delay(ONE_SECOND)
                timer?.start()
            }
        }
    }

    abstract fun getTotalRemainingSeconds(): Int

    abstract fun sayNextExercise()

    fun start() {
        _timerViewState.value = TimerViewState.STARTING
        playCountdownToStart()
    }

    /**
     * Make sure timer also gets cancelled when user navigates away from the countdown fragment.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        _timerViewState.value = TimerViewState.PAUSED
        timer?.cancel()
    }

    fun resume() {
        _timerViewState.value = TimerViewState.RUNNING
        setupTimer(_timerViewData.value?.getRemainingMillisInSet() ?: 0L)
        timer?.start()
    }

    fun finish() {
        _timerViewState.value = TimerViewState.FINISHED
        audioPlayer.playBeep()
        // Add one second delay so that "Workout complete" doesn't overlap with the beep.
        viewModelScope.launch {
            delay(ONE_SECOND)
            audioPlayer.speak(resourcesProvider.getString(R.string.workout_complete))
        }
    }

    private fun setupTimer(millisRemaining: Long) {
        // Cancel any existing timer.
        timer?.cancel()
        timer = object : CountDownTimer(millisRemaining, MILLIS_PER_SECOND.toLong()) {

            var hasSaidNextExercise = false

            override fun onTick(millisUntilFinished: Long) {
                // Say the next exercise the first time onTick() is called with < 5 seconds left.
                if (!hasSaidNextExercise && millisUntilFinished < EIGHT_SECONDS) {
                    sayNextExercise()
                    hasSaidNextExercise = true
                }
                _timerViewData.value = _timerViewData.value?.copy(
                    secondsRemainingInSet = millisUntilFinished.toInt() / MILLIS_PER_SECOND
                )
            }

            override fun onFinish() {
                startNextExercise()
            }
        }
    }

    /**
     * Count down for 3 seconds before starting the workout.
     */
    private fun playCountdownToStart() {
        val countdownTimerToStart =
            object : CountDownTimer(THREE_SECONDS, MILLIS_PER_SECOND.toLong()) {

                var lastCountedSecond = 4

                override fun onTick(millisUntilFinished: Long) {
                    when {
                        (lastCountedSecond > 3 && millisUntilFinished < THREE_SECONDS) -> {
                            audioPlayer.speak("3")
                            lastCountedSecond--
                        }
                        (lastCountedSecond > 2 && millisUntilFinished < TWO_SECONDS) -> {
                            audioPlayer.speak("2")
                            lastCountedSecond--
                        }
                        (lastCountedSecond > 1 && millisUntilFinished < ONE_SECOND) -> {
                            audioPlayer.speak("1")
                            lastCountedSecond--
                        }
                    }
                }

                override fun onFinish() {
                    audioPlayer.playBeep()
                    resume()
                    this.cancel()
                }
            }
        countdownTimerToStart.start()
    }
}