package com.sherryyuan.emomtimer.utils


const val MILLIS_PER_SECOND = 1000
const val SECONDS_PER_MINUTE = 60

fun Int.toTimeString(): String {
    val minutes = this / SECONDS_PER_MINUTE
    val remainingSeconds = this - minutes * SECONDS_PER_MINUTE
    val secondsString: String = when {
        remainingSeconds == 0 -> "00"
        remainingSeconds < 10 -> "0$remainingSeconds"
        else -> "$remainingSeconds"
    }
    return "$minutes:$secondsString"
}