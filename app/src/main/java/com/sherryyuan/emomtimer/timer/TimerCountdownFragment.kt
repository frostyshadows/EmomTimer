package com.sherryyuan.emomtimer.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.sherryyuan.emomtimer.databinding.FragmentTimerCountdownBinding
import com.sherryyuan.emomtimer.models.TimerData

class TimerCountdownFragment : Fragment() {

    private val viewModel: TimerViewModel by viewModels()

    private val binding: FragmentTimerCountdownBinding by lazy {
        FragmentTimerCountdownBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        with(viewModel) {
            timerData.observe(requireActivity(), Observer { updateViews(it) })
        }
        return binding.root.also {}
    }

    private fun updateViews(timerData: TimerData) {
        binding.remainingSecondsText.text = timerData.secondsRemainingInSet.toString()
        // TODO format the time correctly and also update progressbar
    }
}