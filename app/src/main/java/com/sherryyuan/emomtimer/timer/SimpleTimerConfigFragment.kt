package com.sherryyuan.emomtimer.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.utils.SECONDS_PER_MINUTE
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
        setupSpinner()
        setupStartButton()
        return binding.root
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            binding.timeUnitSpinner.context,
            R.array.time_unit_array,
            R.layout.item_spinner
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.timeUnitSpinner.adapter = adapter
        }
    }

    private fun setupStartButton() {
        binding.startButton.setOnClickListener {
            val numTime: Int = binding.numTimeText.text.toString().toIntOrNull() ?: 1
            val numSeconds: Int =
                if (binding.timeUnitSpinner.selectedItem == "minute(s)") {
                    numTime * SECONDS_PER_MINUTE
                } else {
                    numTime
                }
            val numSets: Int =
                binding.numSetsText.text.toString().toIntOrNull() ?: 20
            findNavController().navigate(
                SimpleTimerConfigFragmentDirections.actionTimerConfigToTimerCountdown(
                    SimpleTimerViewModelType(numSeconds, numSets)
                )
            )
        }
    }
}