package com.sherryyuan.emomtimer.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.FragmentSimpleTabataTimerConfigBinding
import com.sherryyuan.emomtimer.utils.SECONDS_PER_MINUTE
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelType.SimpleEmomTimerViewModelType


class SimpleTabataTimerConfigFragment : Fragment() {

    private val binding: FragmentSimpleTabataTimerConfigBinding by lazy {
        FragmentSimpleTabataTimerConfigBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupSpinner()
        setupStartButton()
        return binding.root
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            binding.root.context,
            R.array.time_unit_array,
            R.layout.item_spinner
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.workTimeUnitSpinner.adapter = adapter
            binding.restTimeUnitSpinner.adapter = adapter
        }
    }

    private fun setupStartButton() {
        binding.startButton.setOnClickListener {
            val numWorkTime: Int = binding.numRoundsText.text.toString().toIntOrNull() ?: 1
            val numWorkSeconds: Int =
                if (binding.workTimeUnitSpinner.selectedItem == "minute(s)") {
                    numWorkTime * SECONDS_PER_MINUTE
                } else {
                    numWorkTime
                }
            val numRestTime: Int = binding.numRoundsText.text.toString().toIntOrNull() ?: 1
            val numRestSeconds: Int =
                if (binding.restTimeUnitSpinner.selectedItem == "minute(s)") {
                    numRestTime * SECONDS_PER_MINUTE
                } else {
                    numRestTime
                }
            val numRounds: Int =
                binding.numRoundsText.text.toString().toIntOrNull() ?: 20
            findNavController().navigate(
                SimpleEmomTimerConfigFragmentDirections.actionTimerConfigToTimerCountdown(
                    SimpleEmomTimerViewModelType(numWorkSeconds, numRounds)
                )
            )
        }
    }
}