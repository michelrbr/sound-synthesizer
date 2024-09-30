package br.com.michel.soundsynthesizer.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.michel.soundsynthesizer.domain.CoroutineDispatcherProvider
import br.com.michel.soundsynthesizer.domain.ResourcesProvider
import br.com.michel.soundsynthesizer.domain.Wavetable
import br.com.michel.soundsynthesizer.presentation.screen.model.PlayButtonState
import br.com.michel.soundsynthesizer.presentation.screen.model.SliderState
import br.com.michel.soundsynthesizer.presentation.screen.model.WavetableState
import com.michel.soundsynthesizer.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class HomeViewModel(
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val resources: ResourcesProvider
): ViewModel() {

    private val _wavetable = MutableStateFlow<Wavetable?>(null)
    val wavetableStates: StateFlow<List<WavetableState>> = _wavetable
        .map { getWavetableSelectionState(it) }
        .stateIn(
            scope = viewModelScope.plus(dispatcherProvider.io),
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = getWavetableSelectionState(null)
        )

    private val _frequency = MutableStateFlow(DEFAULT_FREQUENCY)
    val frequencyState: StateFlow<SliderState> = _frequency
        .map {
            SliderState(
                label = resources.getString(R.string.slider_label_frequency, it),
                DEFAULT_FREQUENCY
            )
        }
        .stateIn(
            scope = viewModelScope.plus(dispatcherProvider.io),
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SliderState(
                label = resources.getString(R.string.slider_label_frequency, DEFAULT_FREQUENCY),
                DEFAULT_FREQUENCY
            )
        )

    private val _volume = MutableStateFlow(DEFAULT_VOLUME)
    val volumeState: StateFlow<SliderState> = _volume.asStateFlow()
        .map {
            SliderState(
                label = resources.getString(R.string.slider_label_volume, it),
                DEFAULT_VOLUME
            )
        }.stateIn(
            scope = viewModelScope.plus(dispatcherProvider.io),
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SliderState(
                label = resources.getString(R.string.slider_label_volume, DEFAULT_VOLUME),
                DEFAULT_VOLUME
            )
        )

    private val _playState = MutableStateFlow(DEFAULT_IS_PLAYING)
    val playState: StateFlow<PlayButtonState> = _playState.asStateFlow()
        .map {
            getPlayButtonState(it)
        }.stateIn(
            scope = viewModelScope.plus(dispatcherProvider.io),
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = getPlayButtonState(_playState.value)
        )

    fun setWavetable(wavetable: Wavetable) =
        viewModelScope.launch(dispatcherProvider.io) {
            _wavetable.emit(wavetable)
        }

    fun setFrequency(frequency: Float) =
        viewModelScope.launch(dispatcherProvider.io) {
            _frequency.emit(frequency)
        }

    fun setVolume(volume: Float) =
        viewModelScope.launch(dispatcherProvider.io) {
            _volume.emit(volume)
        }

    fun togglePayStop() =
        viewModelScope.launch(dispatcherProvider.io) {
            _playState.emit(_playState.value.not())
        }

    private fun getPlayButtonState(isPlaying: Boolean) =
        PlayButtonState(
            icon = if (isPlaying) Icons.Filled.Stop else Icons.Filled.PlayArrow,
            contentDescription = if (isPlaying) {
                resources.getString(R.string.stop_content_description)
            } else {
                resources.getString(R.string.play_content_description)
            }
        )

    private fun getWavetableSelectionState(selected: Wavetable?): List<WavetableState> =
        Wavetable.entries.map {
            WavetableState(
                wavetable = it,
                name = it.getLabel(),
                color = it.getColor(),
                isSelected = it == selected
            )
        }

    private fun Wavetable.getLabel(): String =
        when (this) {
            Wavetable.SINE -> resources.getString(R.string.button_label_sine)
            Wavetable.TRIANGLE -> resources.getString(R.string.button_label_triangle)
            Wavetable.SQUARE -> resources.getString(R.string.button_label_square)
            Wavetable.SAW -> resources.getString(R.string.button_label_saw)
        }

    private fun Wavetable.getColor(): Long =
        when (this) {
            Wavetable.SINE -> 0xFF148217
            Wavetable.TRIANGLE -> 0xFFD6D020
            Wavetable.SQUARE -> 0xFFB31E19
            Wavetable.SAW -> 0xFFC96908
        }

    companion object {
        internal const val DEFAULT_FREQUENCY = 1000F
        internal const val DEFAULT_VOLUME = 50F
        internal const val DEFAULT_IS_PLAYING = false
    }
}
