package com.sherryyuan.emomtimer.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.FragmentTimerCountdownBinding
import com.sherryyuan.emomtimer.secondsToMinuteString
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModel
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelFactory
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewState

class TimerCountdownFragment : Fragment() {

    private val navArgs: TimerCountdownFragmentArgs by navArgs()
    private val viewModel: TimerViewModel by viewModels {
        TimerViewModelFactory(navArgs.type)
    }

    private val binding: FragmentTimerCountdownBinding by lazy {
        FragmentTimerCountdownBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Make sure screen stays on.
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        viewModel.timerViewData.observe(requireActivity(), Observer { updateViews(it) })
        viewModel.timerViewState.observe(
            requireActivity(),
            Observer {
                updateTimerControllerButton(it)
                checkIfComplete(it)
            }
        )
        binding.timerControllerButton.setOnClickListener {
            when (viewModel.timerViewState.value) {
                TimerViewState.NOT_STARTED -> viewModel.startWorkout()
                TimerViewState.RUNNING -> viewModel.pause()
                TimerViewState.PAUSED -> viewModel.resumeTimer()
                else -> Unit
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun updateViews(timerViewData: TimerViewData) {
        binding.apply {
            workoutNameText.text = timerViewData.timerName
            currentSetText.text = currentExerciseText.resources.getString(
                R.string.current_set_text,
                // Adding 1 because currentSet is 0-indexed.
                timerViewData.currentSet + 1,
                timerViewData.totalSets
            )
            remainingSecondsText.text = timerViewData.secondsRemainingInSet.toString()
            // TODO format the time correctly and also update progressbar
            timerProgressBar.max = timerViewData.totalSecondsInSet
            timerProgressBar.progress = timerViewData.secondsRemainingInSet
            if (!timerViewData.currentExerciseName.isNullOrBlank()) {
                currentExerciseText.text = currentExerciseText.resources.getString(
                    R.string.current_exercise_text,
                    timerViewData.currentExerciseReps,
                    timerViewData.currentExerciseName
                )
            } else {
                currentExerciseText.text = null
            }
            if (!timerViewData.nextExerciseName.isNullOrBlank()) {
                nextExerciseText.text = nextExerciseText.resources.getString(
                    R.string.next_exercise_text,
                    timerViewData.nextExerciseReps,
                    timerViewData.nextExerciseName
                )
            } else {
                nextExerciseText.text = null
            }
            totalMinsRemainingText.text = totalMinsRemainingText.resources.getString(
                R.string.total_mins_remaining_text,
                secondsToMinuteString(viewModel.getTotalRemainingSeconds())
            )
        }
    }

    private fun updateTimerControllerButton(timerViewState: TimerViewState) {
        @DrawableRes val buttonDrawable: Int = when (timerViewState) {
            TimerViewState.NOT_STARTED -> R.drawable.icon_play_arrow
            TimerViewState.RUNNING -> R.drawable.ic_pause_black_24dp
            TimerViewState.PAUSED -> R.drawable.icon_play_arrow
            else -> R.drawable.icon_play_arrow
        }
        binding.timerControllerButton.isVisible = timerViewState != TimerViewState.STARTING
        binding.timerControllerButton.setImageDrawable(
            ContextCompat.getDrawable(binding.timerControllerButton.context, buttonDrawable)
        )
    }

    private fun checkIfComplete(timerViewState: TimerViewState) {
        if (timerViewState == TimerViewState.FINISHED) {
            findNavController().navigate(
                TimerCountdownFragmentDirections.actionTimerCountdownToWorkoutComplete()
            )
        }
    }
}