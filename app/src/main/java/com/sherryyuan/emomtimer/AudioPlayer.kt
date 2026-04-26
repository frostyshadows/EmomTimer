package com.sherryyuan.emomtimer

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.media3.common.audio.AudioFocusRequestCompat
import androidx.media3.common.audio.AudioManagerCompat
import androidx.media3.common.util.UnstableApi
import java.util.Locale
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

/**
 * Class for handling TextToSpeech and generating beep tones.
 */
@UnstableApi // AudioFocusRequestCompat is unstable
class AudioPlayer(context: Context) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    // Handles lowering volume of other audio (eg. music) while EmomTimer is speaking
    private val audioFocusRequest =
        AudioFocusRequestCompat.Builder(AudioManagerCompat.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
            .setOnAudioFocusChangeListener { }
            .build()
    private val activeUtteranceCount = AtomicInteger(0)

    private var isTtsReady = false
    private val pendingSpeech = mutableListOf<String>()

    private val tts: TextToSpeech =
        TextToSpeech(context, { status ->
            if (status != TextToSpeech.ERROR) {
                setLanguage()
                tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String) {}
                    override fun onDone(utteranceId: String) = onUtteranceFinished()
                    override fun onError(utteranceId: String) = onUtteranceFinished()
                })
                isTtsReady = true
                pendingSpeech.forEach { speakNow(it) }
                pendingSpeech.clear()
            }
        }, GOOGLE_TTS_ENGINE)

    private val toneGenerator: ToneGenerator by lazy {
        ToneGenerator(AudioManager.STREAM_MUSIC, 100)
    }

    fun speak(text: String) {
        if (isTtsReady) {
            speakNow(text)
        } else {
            pendingSpeech.add(text)
        }
    }

    fun playBeep() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ANSWER)
    }

    fun shutdown() {
        isTtsReady = false
        AudioManagerCompat.abandonAudioFocusRequest(audioManager, audioFocusRequest)
        pendingSpeech.clear()
        toneGenerator.release()
        tts.shutdown()
    }

    private fun speakNow(text: String) {
        if (activeUtteranceCount.getAndIncrement() == 0) {
            AudioManagerCompat.requestAudioFocus(audioManager, audioFocusRequest)
        }
        val bundle = Bundle().apply {
            putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC)
        }
        tts.speak(text, TextToSpeech.QUEUE_ADD, bundle, UUID.randomUUID().toString())
    }

    private fun onUtteranceFinished() {
        if (activeUtteranceCount.decrementAndGet() == 0) {
            AudioManagerCompat.abandonAudioFocusRequest(audioManager, audioFocusRequest)
        }
    }

    private fun setLanguage() {
        tts.language = Locale.US
    }

    companion object {
        private const val GOOGLE_TTS_ENGINE = "com.google.android.tts"
    }
}
