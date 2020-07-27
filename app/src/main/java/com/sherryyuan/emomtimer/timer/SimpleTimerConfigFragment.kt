package com.sherryyuan.emomtimer.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sherryyuan.emomtimer.databinding.FragmentSimpleTimerConfigBinding
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelType.SimpleTimerViewModelType

class SimpleTimerConfigFragment : Fragment() {

    private val binding: FragmentSimpleTimerConfigBinding by lazy {
        FragmentSimpleTimerConfigBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupStartButton()
        return binding.root
    }

    private fun setupStartButton() {
        binding.startButton.setOnClickListener {
            val numMinutes: Int = binding.numMinutesText.text.toString().toIntOrNull() ?: 1
            val numSets: Int =
                binding.numSetsText.text.toString().toIntOrNull() ?: 20
            findNavController().navigate(
                SimpleTimerConfigFragmentDirections.actionTimerConfigFragmentToTimerCountdownFragment(
                    SimpleTimerViewModelType(numMinutes, numSets)
                )
            )
        }
    }
}