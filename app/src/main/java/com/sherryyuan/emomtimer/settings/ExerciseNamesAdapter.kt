package com.sherryyuan.emomtimer.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.databinding.ItemExerciseNameBinding
import org.koin.core.KoinComponent


class ExerciseNamesAdapter(private val exerciseNames: List<String>) :
    RecyclerView.Adapter<ExerciseNamesAdapter.ViewHolder>(), KoinComponent {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemExerciseNameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exerciseNames[position])
    }

    override fun getItemCount() = exerciseNames.size

    inner class ViewHolder(private val binding: ItemExerciseNameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exerciseName: String) {
            binding.root.text = exerciseName
        }
    }
}