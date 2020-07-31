package com.sherryyuan.emomtimer.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.FragmentTimerCountdownBinding
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModel
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelFactory
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewState
import kotlinx.android.synthetic.main.item_workout.*

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
        viewModel.timerViewData.observe(requireActivity(), Observer { updateViews(it) })
        viewModel.timerViewState.observe(
            requireActivity(),
            Observer { updateTimerControllerButton(it) })
        binding.timerControllerButton.setOnClickListener {
            when (viewModel.timerViewState.value) {
                TimerViewState.NOT_STARTED -> viewModel.start()
                TimerViewState.RUNNING -> viewModel.pause()
                TimerViewState.PAUSED -> viewModel.resume()
                else -> Unit
            }
        }
        return binding.root
    }

    private fun updateViews(timerViewData: TimerViewData) {
        binding.apply {
            workoutName.text = timerViewData.timerName
            remainingSecondsText.text = timerViewData.secondsRemainingInSet.toString()
            // TODO format the time correctly and also update progressbar
            timerProgressBar.max = timerViewData.totalSecondsInSet
            timerProgressBar.progress = timerViewData.secondsRemainingInSet
            if (!timerViewData.currentExerciseName.isNullOrBlank()) {
                currentSetText.text = currentSetText.resources.getString(
                    R.string.current_exercise_text,
                    timerViewData.currentExerciseReps,
                    timerViewData.currentExerciseName
                )
            }
            if (!timerViewData.nextExerciseName.isNullOrBlank()) {
                nextSetText.text = currentSetText.resources.getString(
                    R.string.next_exercise_text,
                    timerViewData.nextExerciseReps,
                    timerViewData.nextExerciseName
                )
            }
        }
    }

    private fun updateTimerControllerButton(timerViewState: TimerViewState) {
        @DrawableRes val buttonDrawable: Int = when (timerViewState) {
            TimerViewState.NOT_STARTED -> R.drawable.icon_play_arrow
            TimerViewState.RUNNING -> R.drawable.ic_pause_black_24dp
            TimerViewState.PAUSED -> R.drawable.icon_play_arrow
            else -> R.drawable.icon_play_arrow
        }
        binding.timerControllerButton.setImageDrawable(
            ContextCompat.getDrawable(binding.timerControllerButton.context, buttonDrawable)
        )
    }
}