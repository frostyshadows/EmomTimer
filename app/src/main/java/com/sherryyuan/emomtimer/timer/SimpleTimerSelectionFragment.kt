package com.sherryyuan.emomtimer.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sherryyuan.emomtimer.databinding.FragmentSimpleTimerSelectionBinding
import com.sherryyuan.emomtimer.utils.safeNavigate

class SimpleTimerSelectionFragment : Fragment() {

    private val binding: FragmentSimpleTimerSelectionBinding by lazy {
        FragmentSimpleTimerSelectionBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupEmomButton()
        setupTabataButton()
        return binding.root
    }

    private fun setupEmomButton() {
        binding.emomButton.setOnClickListener {
            findNavController().safeNavigate(
                SimpleTimerSelectionFragmentDirections.actionTimerSelectionToEmomTimerConfig()
            )
        }
    }

    private fun setupTabataButton() {
        binding.tabataButton.setOnClickListener {
            findNavController().safeNavigate(
                SimpleTimerSelectionFragmentDirections.actionTimerSelectionToTabataTimerConfig()
            )
        }
    }
}