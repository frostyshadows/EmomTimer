package com.sherryyuan.emomtimer.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sherryyuan.emomtimer.databinding.FragmentExerciseNamesBinding
import com.sherryyuan.emomtimer.workout.repository.ExerciseNamesStorage
import org.koin.core.KoinComponent
import org.koin.core.get

class ExerciseNamesFragment : Fragment(), KoinComponent {

    private val binding: FragmentExerciseNamesBinding by lazy {
        FragmentExerciseNamesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.listExerciseNames.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter =
                ExerciseNamesAdapter(get<ExerciseNamesStorage>().getAllExerciseNames().toList())
        }
        return binding.root
    }
}