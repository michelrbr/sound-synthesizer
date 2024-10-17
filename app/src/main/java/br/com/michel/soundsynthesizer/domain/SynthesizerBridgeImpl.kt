package br.com.michel.soundsynthesizer.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SynthesizerBridgeImpl @Inject constructor(): SynthesizerBridge {

    private var synthesizerHandle: Long = 0
    private val synthesizerMutex = Object()
    private external fun create(): Long
    private external fun delete(synthesizerHandle: Long)
    private external fun play(synthesizerHandle: Long)
    private external fun stop(synthesizerHandle: Long)
    private external fun isPlaying(synthesizerHandle: Long): Boolean
    private external fun setFrequency(synthesizerHandle: Long, frequencyInHz: Float)
    private external fun setVolume(synthesizerHandle: Long, amplitudeInDb: Float)
    private external fun setWavetable(synthesizerHandle: Long, wavetable: Int)

    init {
        System.loadLibrary("soundsynthesizer")
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
        }
    }

    override suspend fun play() {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            play(synthesizerHandle)
        }
    }

    override suspend fun stop() {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            stop(synthesizerHandle)
        }
    }

    override suspend fun isPlaying(): Boolean {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            return isPlaying(synthesizerHandle)
        }
    }

    override suspend fun setWavetable(wavetable: Wavetable) {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            setWavetable(synthesizerHandle, wavetable.ordinal)
        }
    }

    override suspend fun setFrequency(frequencyInHz: Float) {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            setFrequency(synthesizerHandle, frequencyInHz)
        }
    }

    override suspend fun setVolume(volumeInDb: Float) {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            setVolume(synthesizerHandle, volumeInDb)
        }
    }

    override fun close() {
        synchronized(synthesizerMutex) {
            if (synthesizerHandle == 0L) {
                return
            }

            delete(synthesizerHandle)
            synthesizerHandle = 0L
        }
    }

    private fun createNativeHandleIfNotExists() {
        if (synthesizerHandle != 0L) {
            return
        }
        synthesizerHandle = create()
    }
}