package br.com.michel.soundsynthesizer.domain

interface PlayControl {
    suspend fun play()
    suspend fun stop()
    suspend fun isPlaying(): Boolean
}
