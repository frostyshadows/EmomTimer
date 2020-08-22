package com.sherryyuan.emomtimer.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.sherryyuan.emomtimer.BuildConfig

private const val PREFS_NAME = "emom_shared_prefs"
private const val PREF_VERSION_CODE_KEY = "version_code"
private const val NOT_FOUND = -1

fun isFirstInstall(context: Context): Boolean {
    // Get current version code
    val currentVersionCode: Int = BuildConfig.VERSION_CODE

    // Get saved version code
    val sharedPrefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    val savedVersionCode = sharedPrefs.getInt(PREF_VERSION_CODE_KEY, NOT_FOUND)

    // Check for first run or upgrade
    when {
        currentVersionCode == savedVersionCode -> return false  // This is just a normal run
        savedVersionCode == NOT_FOUND -> return true // This is a new install (or the user cleared the shared preferences)
        currentVersionCode > savedVersionCode -> return false // This is an upgrade
        }
    // Update the shared preferences with the current version code
    sharedPrefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply()
    return false
}