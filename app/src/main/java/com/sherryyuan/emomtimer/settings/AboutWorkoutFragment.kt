package com.sherryyuan.emomtimer.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sherryyuan.emomtimer.databinding.FragmentAboutWorkoutBinding

class AboutWorkoutFragment : Fragment() {

    private val navArgs: AboutWorkoutFragmentArgs by navArgs()

    private val binding: FragmentAboutWorkoutBinding by lazy {
        FragmentAboutWorkoutBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.aboutWorkoutTitle.text = getString(navArgs.titleRes)
        binding.aboutWorkoutExplanation.text = getString(navArgs.explanationRes)
    }
}