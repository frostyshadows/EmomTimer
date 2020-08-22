package com.sherryyuan.emomtimer.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sherryyuan.emomtimer.databinding.FragmentAboutEmomBinding

class AboutEmomFragment : Fragment() {

    private val binding: FragmentAboutEmomBinding by lazy {
        FragmentAboutEmomBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.root
}