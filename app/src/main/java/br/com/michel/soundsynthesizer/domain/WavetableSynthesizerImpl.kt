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
        scope.launch(dispatcherProvider.default) { _selectedWavetable.value = wavetable }
        Timber.d("Wavetable set to: $wavetable")
    }

    override suspend fun play() {
        scope.launch(dispatcherProvider.default) { _isPlaying.value = true }
        Timber.d("Play")
    }

    override suspend fun stop() {
        scope.launch(dispatcherProvider.default) { _isPlaying.value = false }
        Timber.d("Stop")
    }

    override suspend fun setFrequency(percentage: Float) {
        require(percentage in 0F..1F) { "Frequency must be between 0.0 and 1.0" }
        scope.launch(dispatcherProvider.default) {
            val frequencyInHz = (FREQUENCY_RANGE.endInclusive - FREQUENCY_RANGE.start) * percentage + FREQUENCY_RANGE.start
            _frequencyInHz.value = frequencyInHz
        }
        Timber.d("Frequency set to: $frequencyInHz")
    }

    override suspend fun setVolume(percentage: Float) {
        require(percentage in 0F..1F) { "Volume must be between 0.0 and 1.0" }
        scope.launch(dispatcherProvider.default) {
            val volumeInDb = (VOLUME_RANGE.endInclusive - VOLUME_RANGE.start) * percentage + VOLUME_RANGE.start
            _volumeInDb.value = volumeInDb
        }
        Timber.d("Volume set to: $volumeInDb")
    }

    override fun close() {
        scope.cancel()
        Timber.d("Synthesizer closed")
    }

    companion object {
        private const val DEFAULT_FREQUENCY = 1000F
        private val FREQUENCY_RANGE = 40F..3000F
        private const val DEFAULT_VOLUME = -30F
        private val VOLUME_RANGE = -60F..0F
        private const val DEFAULT_IS_PLAYING = false
    }
}
