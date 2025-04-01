package br.com.michel.soundsynthesizer.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.michel.soundsynthesizer.domain.CoroutineDispatcherProvider
import br.com.michel.soundsynthesizer.domain.ResourcesProvider
import br.com.michel.soundsynthesizer.domain.Wavetable
import br.com.michel.soundsynthesizer.domain.WavetableSynthesizer
import br.com.michel.soundsynthesizer.domain.getColor
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

    override fun onCleared() {
        synthesizer.close()
        super.onCleared()
    }

    fun setWavetable(wavetable: Wavetable) =
        viewModelScope.launch(dispatcherProvider.default) {
            synthesizer.setWavetable(wavetable.takeUnless { it == synthesizer.selectedWavetable.value })
        }

    fun setFrequency(frequency: Float) =
        viewModelScope.launch(dispatcherProvider.default) {
            synthesizer.setFrequency(frequency)
        }

    fun setVolume(volume: Float) =
        viewModelScope.launch(dispatcherProvider.default) {
            synthesizer.setVolume(volume)
        }

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

    companion object {
        internal const val DEFAULT_FREQUENCY = 0.324325F
        internal const val DEFAULT_VOLUME = 0.5F
        private const val UNSUBSCRIBE_DELAY: Long = 5_000
    }
}
