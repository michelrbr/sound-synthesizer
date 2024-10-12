package br.com.michel.soundsynthesizer.domain

import kotlinx.coroutines.flow.StateFlow

interface WavetableSynthesizer {
    val selectedWavetable: StateFlow<Wavetable?>
    val wavetables: StateFlow<List<Wavetable>>
    val frequencyInHz: StateFlow<Float>
    val volumeInDb: StateFlow<Float>
    val isPlaying: StateFlow<Boolean>
    suspend fun setFrequency(frequencyInHz: Float)
    suspend fun setVolume(volumeInDb: Float)
    suspend fun setWavetable(wavetable: Wavetable)
    suspend fun play()
    suspend fun stop()
}
