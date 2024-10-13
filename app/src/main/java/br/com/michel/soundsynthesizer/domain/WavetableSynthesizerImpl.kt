package br.com.michel.soundsynthesizer.domain

import br.com.michel.soundsynthesizer.di.SynthesizerScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WavetableSynthesizerImpl @Inject constructor(
    @SynthesizerScope private val scope: CoroutineScope,
    private val dispatcherProvider: CoroutineDispatcherProvider
) : WavetableSynthesizer {

    private val _selectedWavetable = MutableStateFlow<Wavetable?>(null)
    override val selectedWavetable: StateFlow<Wavetable?> = _selectedWavetable

    private val _wavetables = MutableStateFlow(Wavetable.entries)
    override val wavetables: StateFlow<List<Wavetable>> = _wavetables

    private val _frequencyInHz = MutableStateFlow(DEFAULT_FREQUENCY)
    override val frequencyInHz: StateFlow<Float> = _frequencyInHz

    private val _volumeInDb = MutableStateFlow(DEFAULT_VOLUME)
    override val volumeInDb: StateFlow<Float> = _volumeInDb

    private val _isPlaying = MutableStateFlow(DEFAULT_IS_PLAYING)
    override val isPlaying: StateFlow<Boolean> = _isPlaying

    override suspend fun setWavetable(wavetable: Wavetable) {
        Timber.d("Wavetable set to: $wavetable")
        scope.launch(dispatcherProvider.default) { _selectedWavetable.value = wavetable }
    }

    override suspend fun play() {
        Timber.d("Play")
        scope.launch(dispatcherProvider.default) { _isPlaying.value = true }
    }

    override suspend fun stop() {
        Timber.d("Stop")
        scope.launch(dispatcherProvider.default) { _isPlaying.value = false }
    }

    override suspend fun setFrequency(frequencyInHz: Float) {
        Timber.d("Frequency set to: $frequencyInHz")
        scope.launch(dispatcherProvider.default) { _frequencyInHz.value = frequencyInHz }
    }

    override suspend fun setVolume(volumeInDb: Float) {
        Timber.d("Volume set to: $volumeInDb")
        scope.launch(dispatcherProvider.default) { _volumeInDb.value = volumeInDb }
    }

    override fun close() {
        Timber.d("Synthesizer closed")
        scope.cancel()
    }

    companion object {
        internal const val DEFAULT_FREQUENCY = 1000F
        internal const val DEFAULT_VOLUME = 50F
        internal const val DEFAULT_IS_PLAYING = false
    }
}
