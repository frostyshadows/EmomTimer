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
import com.sherryyuan.emomtimer.timer.viewmodel.TimerViewModelType.SimpleTabataTimerViewModelType
import com.sherryyuan.emomtimer.utils.SECONDS_PER_MINUTE

private const val SPINNER_SECONDS_POSITION = 1

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
            binding.workTimeUnitSpinner.setSelection(SPINNER_SECONDS_POSITION)
            binding.restTimeUnitSpinner.adapter = adapter
            binding.restTimeUnitSpinner.setSelection(SPINNER_SECONDS_POSITION)
        }
    }

    private fun setupStartButton() {
        binding.startButton.setOnClickListener {
            val numWorkTime: Int = binding.workTimeText.text.toString().toIntOrNull() ?: 45
            val numWorkSeconds: Int =
                if (binding.workTimeUnitSpinner.selectedItem == "minute(s)") {
                    numWorkTime * SECONDS_PER_MINUTE
                } else {
                    numWorkTime
                }
            val numRestTime: Int = binding.restTimeText.text.toString().toIntOrNull() ?: 15
            val numRestSeconds: Int =
                if (binding.restTimeUnitSpinner.selectedItem == "minute(s)") {
                    numRestTime * SECONDS_PER_MINUTE
                } else {
                    numRestTime
                }
            val numRounds: Int =
                binding.numRoundsText.text.toString().toIntOrNull() ?: 20
            findNavController().navigate(
                SimpleTabataTimerConfigFragmentDirections.actionTabataTimerConfigToTimerCountdown(
                    SimpleTabataTimerViewModelType(
                        numWorkSecondsPerRound = numWorkSeconds,
                        numRestSecondsPerRound = numRestSeconds,
                        numRounds = numRounds
                    )
                )
            )
        }
    }
}