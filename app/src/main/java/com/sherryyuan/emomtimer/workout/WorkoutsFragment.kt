package com.sherryyuan.emomtimer.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.databinding.FragmentWorkoutsBinding

class WorkoutsFragment : Fragment() {

    private val viewModel: WorkoutsViewModel by viewModels()
    private val binding: FragmentWorkoutsBinding by lazy {
        FragmentWorkoutsBinding.inflate(layoutInflater)
    }
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: WorkoutsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.workouts.observe(
            viewLifecycleOwner,
            Observer {
                viewAdapter.workouts = it
                viewAdapter.notifyDataSetChanged()
            }
        )
        return binding.root.also {
            setupWorkoutsList()
            setupFab()
        }
    }

    private fun setupWorkoutsList() {
        viewManager = LinearLayoutManager(context)
        viewAdapter = WorkoutsAdapter(viewModel.workouts.value ?: emptyList(), findNavController())

        binding.listWorkouts.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun setupFab() {
        binding.floatingActionButton.setOnClickListener {
            parentFragment?.findNavController()?.navigate(
                WorkoutsFragmentDirections.actionWorkoutsFragmentToAddNewWorkoutFragment()
            )
        }
    }

    private fun updateWorkouts() {

    }
}
