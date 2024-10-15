package br.com.michel.soundsynthesizer.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.michel.soundsynthesizer.domain.CoroutineDispatcherProvider
import br.com.michel.soundsynthesizer.domain.ResourcesProvider
import br.com.michel.soundsynthesizer.domain.Wavetable
import br.com.michel.soundsynthesizer.domain.WavetableSynthesizer
import br.com.michel.soundsynthesizer.presentation.screen.model.PlayButtonState
import br.com.michel.soundsynthesizer.presentation.screen.model.SliderState
import br.com.michel.soundsynthesizer.presentation.screen.model.WavetableState
import com.michel.soundsynthesizer.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val resources: ResourcesProvider,
    private val synthesizer: WavetableSynthesizer
): ViewModel() {

    /*
    * Using lazy here helps with testing, this approach no synthesizer's property will be called
    * at creation
     */
    val wavetableStates: StateFlow<List<WavetableState>> by lazy {
        synthesizer.selectedWavetable
            .combine(synthesizer.wavetables, ::getWavetableSelectionState)
            .stateIn(
                scope = viewModelScope.plus(dispatcherProvider.default),
                started = SharingStarted.WhileSubscribed(UNSUBSCRIBE_DELAY),
                initialValue = getWavetableSelectionState(null, synthesizer.wavetables.value)
            )
    }

    val frequencyState: StateFlow<SliderState> by lazy {
        synthesizer.frequencyInHz
            .map {
                SliderState(
                    label = resources.getString(R.string.slider_label_frequency, it)
                )
            }
            .stateIn(
                scope = viewModelScope.plus(dispatcherProvider.default),
                started = SharingStarted.WhileSubscribed(UNSUBSCRIBE_DELAY),
                initialValue = SliderState(
                    label = resources.getString(R.string.slider_label_frequency, synthesizer.frequencyInHz.value)
                )
            )
    }

    val volumeState: StateFlow<SliderState> by lazy {
        synthesizer.volumeInDb
            .map {
                SliderState(
                    label = resources.getString(R.string.slider_label_volume, it)
                )
            }.stateIn(
                scope = viewModelScope.plus(dispatcherProvider.default),
                started = SharingStarted.WhileSubscribed(UNSUBSCRIBE_DELAY),
                initialValue = SliderState(
                    label = resources.getString(R.string.slider_label_volume, DEFAULT_VOLUME),
                )
            )
    }

    val playState: StateFlow<PlayButtonState> by lazy {
        synthesizer.isPlaying
            .map { getPlayButtonState(it) }
            .stateIn(
                scope = viewModelScope.plus(dispatcherProvider.default),
                started = SharingStarted.WhileSubscribed(UNSUBSCRIBE_DELAY),
                initialValue = getPlayButtonState(synthesizer.isPlaying.value)
            )
    }

    override fun onCleared() {
        synthesizer.close()
        super.onCleared()
    }

    fun setWavetable(wavetable: Wavetable) =
        viewModelScope.launch(dispatcherProvider.default) {
            synthesizer.setWavetable(wavetable)
        }

    fun setFrequency(frequency: Float) =
        viewModelScope.launch(dispatcherProvider.default) {
            synthesizer.setFrequency(frequency)
        }

    fun setVolume(volume: Float) =
        viewModelScope.launch(dispatcherProvider.default) {
            synthesizer.setVolume(volume)
        }

    fun togglePayStop() =
        viewModelScope.launch(dispatcherProvider.default) {
            if (synthesizer.isPlaying.value) {
                synthesizer.stop()
            } else {
                synthesizer.play()
            }
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

    private fun getWavetableSelectionState(selected: Wavetable?, wavetables: List<Wavetable>): List<WavetableState> =
        wavetables.map {
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
        internal const val DEFAULT_FREQUENCY = 0.324325F
        internal const val DEFAULT_VOLUME = 0.5F
        private const val UNSUBSCRIBE_DELAY: Long = 5_000
    }
}
