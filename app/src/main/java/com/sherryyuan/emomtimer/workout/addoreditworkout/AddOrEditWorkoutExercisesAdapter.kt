package com.sherryyuan.emomtimer.workout.addoreditworkout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.ItemAddExerciseBinding
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.utils.SECONDS_PER_MINUTE
import com.sherryyuan.emomtimer.workout.repository.ExerciseNamesStorage
import org.koin.core.KoinComponent
import org.koin.core.inject


class AddOrEditWorkoutExercisesAdapter(
    private val exercises: MutableList<Exercise>
) : RecyclerView.Adapter<AddOrEditWorkoutExercisesAdapter.ViewHolder>(), KoinComponent {

    private val exerciseNamesStorage by inject<ExerciseNamesStorage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemAddExerciseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            create()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(exercises[position])
    }

    override fun getItemCount() = exercises.size

    inner class ViewHolder(private val binding: ItemAddExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var exercise: Exercise

        fun create() {
            setupTimeCountText()
            setupDeleteButton()
            setupSpinner()
            setupExerciseAutocomplete()
        }

        fun bind(exercise: Exercise) {
            this.exercise = exercise
            binding.exercise = exercise
        }

        private fun setupTimeCountText() {
            binding.apply {
                secondCountText.addTextChangedListener(NumberTextWatcher(secondCountText))
                minuteCountText.addTextChangedListener(NumberTextWatcher(minuteCountText))
                // Make sure editing the seconds text also updates the hidden minutes text.
                secondCountText.doOnTextChanged { text, _, _, _ ->
                    val minutesText =
                        ((text.toString().toDoubleOrNull() ?: 0.0) / SECONDS_PER_MINUTE).toString()
                    if (minuteCountText.text.toString() != minutesText && secondCountText.isVisible) {
                        minuteCountText.setText(minutesText)
                    }
                }
                // Make sure editing the minutes text also updates the hidden seconds text.
                minuteCountText.doOnTextChanged { text, _, _, _ ->
                    val secondsText =
                        ((text.toString().toDoubleOrNull() ?: 0.0) * SECONDS_PER_MINUTE).toString()
                    if (secondCountText.text.toString() != secondsText && minuteCountText.isVisible) {
                        secondCountText.setText(secondsText)
                    }
                }
            }
        }

        private fun setupDeleteButton() {
            binding.deleteButton.setOnClickListener {
                exercises.remove(exercise)
                notifyItemRemoved(adapterPosition)
            }
        }

        private fun setupSpinner() {
            binding.apply {
                ArrayAdapter.createFromResource(
                    timeUnitSpinner.context,
                    R.array.time_unit_array,
                    android.R.layout.simple_spinner_item
                ).also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    timeUnitSpinner.adapter = adapter
                }
                timeUnitSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {

                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            minuteCountText.isVisible =
                                timeUnitSpinner.selectedItem.toString() == "minute(s)"
                            secondCountText.isVisible =
                                timeUnitSpinner.selectedItem.toString() == "second(s)"
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            // No-op
                        }
                    }
            }
        }

        private fun setupExerciseAutocomplete() {
            val exerciseNameAdapter: ArrayAdapter<String> = ExerciseAutocompleteArrayAdapter(
                binding.root.context,
                android.R.layout.simple_spinner_dropdown_item,
                exerciseNamesStorage.getAllExerciseNames().toList()
            )
            binding.exerciseNameText.setAdapter(exerciseNameAdapter)
        }
    }
}