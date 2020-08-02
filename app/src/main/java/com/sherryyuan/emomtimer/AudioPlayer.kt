package com.sherryyuan.emomtimer

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.speech.tts.TextToSpeech
import java.util.*

class AudioPlayer(context: Context) {

    private val tts: TextToSpeech =
        TextToSpeech(context, TextToSpeech.OnInitListener { p0 ->
            if (p0 != TextToSpeech.ERROR) {
                setLanguage()
            }
        })

    private val toneGenerator: ToneGenerator by lazy {
        ToneGenerator(AudioManager.STREAM_ALARM, 100)
    }

    fun speak(text: String) {
        // Queue mode where all entries in the playback queue are dropped and replaced by the
        // new entry.
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun playBeep() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT)
    }

    fun shutdown() {
        tts.shutdown()
    }

    private fun setLanguage() {
        tts.language = Locale.US
    }
}