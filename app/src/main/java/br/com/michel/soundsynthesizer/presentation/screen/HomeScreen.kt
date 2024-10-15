package br.com.michel.soundsynthesizer.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.michel.soundsynthesizer.presentation.screen.component.SynthesizerSlider
import br.com.michel.soundsynthesizer.presentation.screen.component.WavetableSelection
import br.com.michel.soundsynthesizer.presentation.theme.AppTheme
import br.com.michel.soundsynthesizer.presentation.theme.SoundSynthesizerTheme
import br.com.michel.soundsynthesizer.presentation.theme.spacing

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.padding(horizontal = AppTheme.spacing.normal),
        verticalArrangement = Arrangement.Center
    ) {
        val wavetableState by viewModel.wavetableStates.collectAsStateWithLifecycle()
        val frequencyState by viewModel.frequencyState.collectAsStateWithLifecycle()
        val volumeState by viewModel.volumeState.collectAsStateWithLifecycle()
        val playState by viewModel.playState.collectAsStateWithLifecycle()

        WavetableSelection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = AppTheme.spacing.extraLarge),
            wavetables = wavetableState,
            onSoundSelected = viewModel::setWavetable
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SynthesizerSlider(
                modifier = Modifier.weight(1F),
                label = frequencyState.label,
                initialValue = HomeViewModel.DEFAULT_FREQUENCY,
                onValueChange = viewModel::setFrequency
            )

            SynthesizerSlider(
                modifier = Modifier.weight(1F),
                label = volumeState.label,
                initialValue = HomeViewModel.DEFAULT_VOLUME,
                onValueChange = viewModel::setVolume
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = AppTheme.spacing.large
                ),
            onClick = viewModel::togglePayStop,
            colors = ButtonDefaults.buttonColors(AppTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = playState.icon,
                contentDescription = playState.contentDescription
            )
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewHome() {
    SoundSynthesizerTheme {
        HomeScreen()
    }
}
