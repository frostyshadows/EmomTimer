package com.sherryyuan.emomtimer

import android.app.Application
import com.sherryyuan.emomtimer.onboarding.DEFAULT_WORKOUTS
import com.sherryyuan.emomtimer.utils.isFirstInstall
import com.sherryyuan.emomtimer.workout.repository.WorkoutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject

class MainApplication : Application(), KoinComponent {

    private val repository: WorkoutRepository by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(applicationModule)
        }

        if (isFirstInstall(this)) {
            loadDefaultWorkouts()
        }
    }

    private fun loadDefaultWorkouts() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addWorkouts(DEFAULT_WORKOUTS)
        }
    }
}