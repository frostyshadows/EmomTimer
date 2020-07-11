package com.sherryyuan.emomtimer

const val SECONDS_PER_MINUTE = 60

fun secondsToTimeString(seconds: Int): String {
    val minutes: Int = seconds / SECONDS_PER_MINUTE
    val leftoverSeconds: Int = seconds - minutes * SECONDS_PER_MINUTE
    // Prepend a 0 if there are less than 10 seconds.
    val secondsString: String =
        if (leftoverSeconds < 10) "0$leftoverSeconds" else leftoverSeconds.toString()
    return "$minutes:$secondsString"
}