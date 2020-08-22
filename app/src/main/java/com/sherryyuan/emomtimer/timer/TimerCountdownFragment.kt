package com.sherryyuan.emomtimer.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.FragmentTimerCountdownBinding
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModel
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelFactory
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewState
import com.sherryyuan.emomtimer.utils.toTimeString

class TimerCountdownFragment : Fragment() {

    private val navArgs: TimerCountdownFragmentArgs by navArgs()
    private val viewModel: TimerViewModel by viewModels {
        TimerViewModelFactory(navArgs.type)
    }

    private val binding: FragmentTimerCountdownBinding by lazy {
        FragmentTimerCountdownBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOnBackPressed()
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
                updateButtons(it)
                checkIfComplete(it)
            }
        )
        binding.timerControllerButton.setOnClickListener {
            when (viewModel.timerViewState.value) {
                TimerViewState.NOT_STARTED -> viewModel.start()
                TimerViewState.RUNNING -> viewModel.pause()
                TimerViewState.PAUSED -> viewModel.resume()
                else -> Unit
            }
        }
        binding.skipNextButton.setOnClickListener {
            viewModel.startNextExercise(viewModel.timerViewState.value == TimerViewState.RUNNING)
        }
        binding.skipPreviousButton.setOnClickListener {
            viewModel.restartExercise(viewModel.timerViewState.value == TimerViewState.RUNNING)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun updateViews(timerViewData: TimerViewData) {
        binding.apply {
            currentSetText.text = currentExerciseText.resources.getString(
                R.string.current_set_text,
                // Adding 1 because currentSet is 0-indexed.
                timerViewData.currentSet + 1,
                timerViewData.totalSets
            )
            remainingSecondsText.text = timerViewData.secondsRemainingInSet.toTimeString()
            timerProgressBar.startValue = 0
            timerProgressBar.endValue = timerViewData.totalSecondsInSet
            timerProgressBar.value = timerViewData.secondsRemainingInSet
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
                viewModel.getTotalRemainingSeconds().toTimeString()
            )
        }
    }

    private fun updateButtons(timerViewState: TimerViewState) {
        @DrawableRes val buttonDrawable: Int = when (timerViewState) {
            TimerViewState.NOT_STARTED -> R.drawable.icon_play_arrow
            TimerViewState.RUNNING -> R.drawable.icon_pause
            TimerViewState.PAUSED -> R.drawable.icon_play_arrow
            else -> R.drawable.icon_play_arrow
        }
        binding.timerControllerButton.isVisible = timerViewState != TimerViewState.STARTING
        binding.skipPreviousButton.isVisible =
            (timerViewState == TimerViewState.RUNNING || timerViewState == TimerViewState.PAUSED)
        binding.skipNextButton.isVisible =
            (timerViewState == TimerViewState.RUNNING || timerViewState == TimerViewState.PAUSED)
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

    // If there's a workout in progress, show a dialog confirming user wants to quit before
    // navigating back.
    private fun setupOnBackPressed() {
        activity?.let { activity ->
            activity.onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (viewModel.timerViewState.value == TimerViewState.NOT_STARTED) {
                            isEnabled = false
                            activity.onBackPressed()
                        } else {
                            AlertDialog.Builder(activity)
                                .setMessage(R.string.alert_quit_workout)
                                .setPositiveButton(R.string.yes) { _, _ ->
                                    isEnabled = false
                                    activity.onBackPressed()
                                }
                                .setNegativeButton(R.string.cancel) { dialog, _ ->
                                    dialog.cancel()
                                }
                                .create()
                                .show()
                        }
                    }
                })
        }
    }
}