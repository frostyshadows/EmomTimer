package com.sherryyuan.emomtimer.onboarding

import com.sherryyuan.emomtimer.models.Exercise
import com.sherryyuan.emomtimer.models.Workout

private val DEFAULT_WORKOUT_TRIPLE_SWEAT = Workout(
    name = "Triple Sweat",
    numRounds = 8,
    exercises = listOf(
        Exercise(
            name= "Mountain climbers",
            numSeconds = 60,
            numReps = 30
        ),
        Exercise(
            name= "Burpees",
            numSeconds = 60,
            numReps = 15
        ),
        Exercise(
            name= "Air squats",
            numSeconds = 60,
            numReps = 30
        )
    )
)

private val DEFAULT_WORKOUT_FULL_BODY = Workout(
    name = "Full Body Express",
    numRounds = 2,
    exercises = listOf(
        Exercise(
            name= "Curtsy Lunges",
            numSeconds = 60,
            numReps = 25
        ),
        Exercise(
            name= "Push-ups",
            numSeconds = 60,
            numReps = 12
        ),
        Exercise(
            name= "Dumbbell rows",
            numSeconds = 60,
            numReps = 15
        ),
        Exercise(
            name= "Burpees",
            numSeconds = 60,
            numReps = 10
        ),
        Exercise(
            name= "Dumbbell Squat Push-press",
            numSeconds = 60,
            numReps = 15
        ),
        Exercise(
            name= "Plank With Shoulder Taps",
            numSeconds = 60,
            numReps = 20
        ),
        Exercise(
            name= "Jump Rope",
            numSeconds = 60,
            numReps = 75
        )
    )
)

private val DEFAULT_WORKOUT_GRIP_THAT_BAR = Workout(
    name = "Grip That Bar",
    numRounds = 4,
    exercises = listOf(
        Exercise(
            name= "Pull-Ups",
            numSeconds = 60,
            numReps = 10
        ),
        Exercise(
            name= "Toes To Bar",
            numSeconds = 60,
            numReps = 10
        )
    )
)

val DEFAULT_WORKOUTS: List<Workout> = listOf(
    DEFAULT_WORKOUT_TRIPLE_SWEAT,
    DEFAULT_WORKOUT_FULL_BODY,
    DEFAULT_WORKOUT_GRIP_THAT_BAR
)