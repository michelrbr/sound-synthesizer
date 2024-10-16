package br.com.michel.soundsynthesizer.domain

interface FrequencyControl {
    suspend fun setFrequency(frequencyInHz: Float)
}
