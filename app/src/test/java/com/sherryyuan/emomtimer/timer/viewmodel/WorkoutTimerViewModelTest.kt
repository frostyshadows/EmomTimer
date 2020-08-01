package com.sherryyuan.emomtimer.timer.viewmodel

import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

class WorkoutTimerViewModelTest {

    @Test
    fun getTotalRemainingSeconds() {
        val viewModel =
            WorkoutTimerViewModel(
                Workout(
                    "test",
                    2, listOf(
                        Exercise("push ups", 60, 10),
                        Exercise("high knees", 45, 30)
                    )
                )
            )
        assertEquals(210, viewModel.getTotalRemainingSeconds())
    }
}