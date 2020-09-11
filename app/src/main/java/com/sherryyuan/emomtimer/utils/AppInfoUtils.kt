package com.sherryyuan.emomtimer.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.sherryyuan.emomtimer.BuildConfig
import com.sherryyuan.emomtimer.PREFS_NAME

private const val PREF_VERSION_CODE_KEY = "version_code"
private const val NOT_FOUND = -1

/**
 * Code copied from https://stackoverflow.com/questions/7217578/check-if-application-is-on-its-first-run
 */
fun isFirstInstall(context: Context): Boolean {
    // Get current version code
    val currentVersionCode: Int = BuildConfig.VERSION_CODE
    // Get saved version code
    val sharedPrefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    val savedVersionCode = sharedPrefs.getInt(PREF_VERSION_CODE_KEY, NOT_FOUND)
    // Update the shared preferences with the current version code
    if (currentVersionCode != savedVersionCode) {
        sharedPrefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply()
    }
    return savedVersionCode == NOT_FOUND
}