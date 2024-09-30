package br.com.michel.soundsynthesizer.presentation.screen.model

import br.com.michel.soundsynthesizer.domain.Wavetable

data class WavetableState(
    val wavetable: Wavetable,
    val name: String,
    val color: Long,
    val isSelected: Boolean = false
)
