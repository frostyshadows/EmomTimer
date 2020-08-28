package com.sherryyuan.emomtimer

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.speech.tts.TextToSpeech
import java.util.*

/**
 * Class for handling TextToSpeech and generating beep tones.
 */
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
        val bundle = Bundle().apply {
            putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_ALARM)
        }
        tts.speak(text, TextToSpeech.QUEUE_ADD, bundle, null)
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