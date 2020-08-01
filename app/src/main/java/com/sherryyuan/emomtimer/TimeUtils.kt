package com.sherryyuan.emomtimer

import java.util.concurrent.TimeUnit


const val MILLIS_PER_SECOND = 1000
const val SECONDS_PER_MINUTE = 60

fun minutesToTimeString(seconds: Int): String {
    val minutes: Int = seconds / SECONDS_PER_MINUTE
    val leftoverSeconds: Int = seconds - minutes * SECONDS_PER_MINUTE
    // Prepend a 0 if there are less than 10 seconds.
    val secondsString: String =
        if (leftoverSeconds < 10) "0$leftoverSeconds" else leftoverSeconds.toString()
    return "$minutes:$secondsString"
}

fun millisToMinuteString(millis: Long): String {
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millis)
    val remainingMillis: Long = millis - minutes * SECONDS_PER_MINUTE * MILLIS_PER_SECOND
    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(remainingMillis)
    val secondsString: String = when {
        seconds == 0L -> "00"
        seconds < 10L -> "0$seconds"
        else -> "$seconds"
    }
    return "$minutes:$secondsString"
}