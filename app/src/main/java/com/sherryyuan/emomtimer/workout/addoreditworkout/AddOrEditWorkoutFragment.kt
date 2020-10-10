package com.sherryyuan.emomtimer.workout.addoreditworkout

import android.app.AlertDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sherryyuan.emomtimer.R
import com.sherryyuan.emomtimer.databinding.FragmentAddOrEditWorkoutBinding
import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout
import com.sherryyuan.emomtimer.utils.SECONDS_PER_MINUTE
import com.sherryyuan.emomtimer.workout.WorkoutsViewModel
import org.koin.core.KoinComponent

class AddOrEditWorkoutFragment : Fragment(), KoinComponent {

    private val navArgs: AddOrEditWorkoutFragmentArgs by navArgs()
    private val viewModel: WorkoutsViewModel by viewModels()

    private val exercises: MutableList<Exercise> by lazy {
        navArgs.workout?.exercises?.toMutableList() ?: mutableListOf()
    }

    private val binding: FragmentAddOrEditWorkoutBinding by lazy {
        FragmentAddOrEditWorkoutBinding.inflate(layoutInflater)
    }

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: AddOrEditWorkoutExercisesAdapter

    private val itemTouchHelper: ItemTouchHelper by lazy(LazyThreadSafetyMode.NONE) {
        createSimpleItemTouchHelper()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOnBackPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navArgs.workout?.let { setupWorkoutDetails(it) }
        setupExercisesList()
        setupAddExerciseButton()
        setupSaveButton()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideSoftKeyboard()
    }

    private fun setupWorkoutDetails(workout: Workout) {
        binding.apply {
            titleText.setText(workout.name)
            setsCountText.setText(workout.numSets.toString())
        }
    }

    private fun setupExercisesList() {
        viewManager = LinearLayoutManager(context)
        viewAdapter = AddOrEditWorkoutExercisesAdapter(exercises)
        binding.listExercises.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        itemTouchHelper.attachToRecyclerView(binding.listExercises)
    }

    private fun setupAddExerciseButton() {
        binding.addExerciseButton.setOnClickListener {
            exercises.add(Exercise(numSeconds = SECONDS_PER_MINUTE))
            viewAdapter.notifyItemInserted(exercises.indices.last)
            binding.listExercises.smoothScrollToPosition(exercises.indices.last)
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            val currentWorkout = getCurrentWorkoutOrNull()
            if (currentWorkout != null) {
                saveWorkout(currentWorkout)
            } else {
                showMissingFieldsDialog()
            }
        }
    }

    // If the workout has unsaved changes, show a dialog asking user whether or not to save them
    // before navigating back.
    private fun setupOnBackPressed() {
        activity?.let { activity ->
            activity.onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        isEnabled = false
                        val currentWorkout = getCurrentWorkoutOrNull()
                        if (currentWorkout == navArgs.workout || currentWorkout == null) {
                            activity.onBackPressed()
                        } else {
                            AlertDialog.Builder(activity)
                                .setMessage(R.string.alert_save_workout_changes)
                                .setPositiveButton(R.string.yes) { _, _ ->
                                    saveWorkout(currentWorkout)
                                }
                                .setNegativeButton(R.string.no) { dialog, _ ->
                                    dialog.cancel()
                                    activity.onBackPressed()
                                }
                                .create()
                                .show()
                        }
                    }
                })
        }
    }

    private fun getCurrentWorkoutOrNull(): Workout? {
        val title: String? = binding.titleText.text?.toString()
        val numSets: Int = binding.setsCountText.text.toString().toIntOrNull() ?: 0
        val filledExercises =
            exercises.filter { it.name.isNotEmpty() && it.numSeconds > 0 && it.numReps > 0 }
        if (title.isNullOrBlank() || numSets <= 0 || filledExercises.isNullOrEmpty()) return null
        return Workout(title, numSets, filledExercises)
    }

    private fun saveWorkout(workout: Workout) {
        viewModel.saveWorkout(newWorkout = workout, prevWorkout = navArgs.workout)
        hideSoftKeyboard()
        findNavController().navigate(
            AddOrEditWorkoutFragmentDirections.actionAddOrEditWorkoutBackToWorkouts()
        )
    }

    private fun showMissingFieldsDialog() {
        activity?.let {
            AlertDialog.Builder(it)
                .setMessage(R.string.alert_fill_out_fields)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }

    private fun hideSoftKeyboard() {
        activity?.let { activity ->
            val view: View? = activity.currentFocus
            if (view != null) {
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    private fun createSimpleItemTouchHelper(): ItemTouchHelper {
        val simpleItemTouchCallback =
            // Specifying START and END allows more organic dragging than just specifying UP and DOWN.
            object : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, LEFT) {

                val icon = context?.let {
                    ContextCompat.getDrawable(it, R.drawable.icon_delete)?.apply {
                        setTint(Color.WHITE)
                    }
                }

                // TODO make corner rounded
                val background = ColorDrawable(Color.RED)
                val backgroundCornerOffset = 20

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    viewAdapter.moveItem(from, to)
                    viewAdapter.notifyItemMoved(from, to)

                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewAdapter.delete(view, viewHolder.adapterPosition)
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )

                    if (icon == null) return

                    val itemView = viewHolder.itemView
                    val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
                    val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                    val iconBottom = iconTop + icon.intrinsicHeight

                    when {
                        dX < 0 && dY == 0f -> { // Swiping to the left.
                            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                            val iconRight = itemView.right - iconMargin
                            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                            background.setBounds(
                                itemView.right + dX.toInt() - backgroundCornerOffset,
                                itemView.top,
                                itemView.right,
                                itemView.bottom
                            )
                            background.draw(c)
                            icon.draw(c)
                        }
                        else -> { // View is unswiped.
                            background.setBounds(0, 0, 0, 0)
                        }
                    }
                }
            }
        return ItemTouchHelper(simpleItemTouchCallback)
    }
}