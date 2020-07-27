package com.sherryyuan.emomtimer.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.sherryyuan.emomtimer.databinding.FragmentTimerCountdownBinding
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModel
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelFactory

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
        return binding.root
    }

    private fun updateViews(timerViewData: TimerViewData) {
        binding.apply {
            workoutName.text = timerViewData.timerName
            remainingSecondsText.text = timerViewData.secondsRemainingInSet.toString()
            // TODO format the time correctly and also update progressbar
            timerProgressBar.max = timerViewData.totalSecondsInSet
            timerProgressBar.progress = timerViewData.secondsRemainingInSet
            currentSetText.text = timerViewData.currentExerciseName
            nextSetText.text = timerViewData.nextExerciseName
        }
    }
}