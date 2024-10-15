package br.com.michel.soundsynthesizer.domain

import kotlinx.coroutines.flow.StateFlow
import java.io.Closeable

interface WavetableSynthesizer : Closeable {
    val selectedWavetable: StateFlow<Wavetable?>
    val wavetables: StateFlow<List<Wavetable>>
    val frequencyInHz: StateFlow<Float>
    val volumeInDb: StateFlow<Float>
    val isPlaying: StateFlow<Boolean>
    suspend fun setFrequency(percentage: Float)
    suspend fun setVolume(percentage: Float)
    suspend fun setWavetable(wavetable: Wavetable)
    suspend fun play()
    suspend fun stop()
}
