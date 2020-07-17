package com.sherryyuan.emomtimer

import androidx.room.Room
import com.sherryyuan.emomtimer.workout.WorkoutContract
import com.sherryyuan.emomtimer.workout.repository.WorkoutRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {

    single<WorkoutContract.Repository> {
        WorkoutRepository()
    }

    single<ApplicationDatabase> {
        Room.databaseBuilder(
            androidContext(),
            ApplicationDatabase::class.java,
            "applicationDatabase"
        ).build()
    }
}