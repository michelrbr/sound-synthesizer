package br.com.michel.soundsynthesizer.domain

interface WavetableControl {
    suspend fun setWavetable(wavetable: Wavetable)
}
