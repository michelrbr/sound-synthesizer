package br.com.michel.soundsynthesizer.domain

interface VolumeControl {
    suspend fun setVolume(volumeInDb: Float)
}
