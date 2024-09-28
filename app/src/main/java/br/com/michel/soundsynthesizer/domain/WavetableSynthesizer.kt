package br.com.michel.soundsynthesizer.domain

interface WavetableSynthesizer {
    suspend fun play()
    suspend fun stop()
    suspend fun isPlaying(): Boolean
    suspend fun setFrequency(frequencyInHz: Float)
    suspend fun setVolume(volumeInDb: Float)
    suspend fun setWavetable(wavetable: Wavetable)
}
