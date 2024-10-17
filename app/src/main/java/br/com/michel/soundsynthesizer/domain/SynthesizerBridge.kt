package br.com.michel.soundsynthesizer.domain

interface SynthesizerBridge:
    PlayControl,
    WavetableControl,
    FrequencyControl,
    VolumeControl,
    AutoCloseable
