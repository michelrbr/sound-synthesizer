package br.com.michel.soundsynthesizer.domain

enum class Wavetable {
    SINE,
    TRIANGLE,
    SQUARE,
    SAW
}

fun Wavetable.getColor(): Long =
    when (this) {
        Wavetable.SINE -> 0xFF317D25
        Wavetable.TRIANGLE -> 0xFF3E1fDD
        Wavetable.SQUARE -> 0xFFA2499E
        Wavetable.SAW -> 0xFFB31E19
    }
