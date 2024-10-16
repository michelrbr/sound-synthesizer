package br.com.michel.soundsynthesizer.domain

import br.com.michel.soundsynthesizer.di.SynthesizerScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WavetableSynthesizerImpl @Inject constructor(
    @SynthesizerScope private val scope: CoroutineScope,
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val bridge: SynthesizerBridge
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
        scope.launch(dispatcherProvider.default) {
            bridge.setWavetable(wavetable)
            _selectedWavetable.value = wavetable
        }
    }

    override suspend fun play() {
        scope.launch(dispatcherProvider.default) {
            bridge.play()
            _isPlaying.value = bridge.isPlaying()
        }
    }

    override suspend fun stop() {
        scope.launch(dispatcherProvider.default) {
            bridge.stop()
            _isPlaying.value = bridge.isPlaying()
        }
    }

    override suspend fun setFrequency(percentage: Float) {
        require(percentage in 0F..1F) { "Frequency must be between 0.0 and 1.0" }
        scope.launch(dispatcherProvider.default) {
            val frequencyInHz = (FREQUENCY_RANGE.endInclusive - FREQUENCY_RANGE.start) * percentage + FREQUENCY_RANGE.start
            bridge.setFrequency(frequencyInHz)
            _frequencyInHz.value = frequencyInHz
        }
    }

    override suspend fun setVolume(percentage: Float) {
        require(percentage in 0F..1F) { "Volume must be between 0.0 and 1.0" }
        scope.launch(dispatcherProvider.default) {
            val volumeInDb = (VOLUME_RANGE.endInclusive - VOLUME_RANGE.start) * percentage + VOLUME_RANGE.start
            bridge.setVolume(volumeInDb)
            _volumeInDb.value = volumeInDb
        }
    }

    override fun close() {
        scope.cancel()
        bridge.close()
    }

    companion object {
        private const val DEFAULT_FREQUENCY = 1000F
        private val FREQUENCY_RANGE = 40F..3000F
        private const val DEFAULT_VOLUME = -30F
        private val VOLUME_RANGE = -60F..0F
        private const val DEFAULT_IS_PLAYING = false
    }
}
