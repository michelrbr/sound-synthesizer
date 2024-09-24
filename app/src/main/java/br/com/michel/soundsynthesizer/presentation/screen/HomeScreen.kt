package br.com.michel.soundsynthesizer.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.michel.soundsynthesizer.presentation.screen.component.SynthesizerSlider
import br.com.michel.soundsynthesizer.presentation.screen.component.SynthesizerSoundSelection
import br.com.michel.soundsynthesizer.presentation.theme.AppTheme
import br.com.michel.soundsynthesizer.presentation.theme.SoundSynthesizerTheme
import br.com.michel.soundsynthesizer.presentation.theme.spacing
import com.michel.soundsynthesizer.R

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = AppTheme.spacing.normal),
        verticalArrangement = Arrangement.Center
    ) {
        SynthesizerSoundSelection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = AppTheme.spacing.extraLarge)
        ) { println("Sound selected: $it") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SynthesizerSlider(
                modifier = Modifier.weight(1F),
                label = stringResource(R.string.slider_label_frequency),
                range = 40F..3000F,
                initialValue = 1000F
            ) { println("Frequency: $it") }

            SynthesizerSlider(
                modifier = Modifier.weight(1F),
                label = stringResource(R.string.slider_label_volume),
                range = 1F..100F,
                initialValue = 50F
            ) { println("Volume: $it") }
        }

        var playProps by remember {
            mutableStateOf(
                ButtonProperties(
                    name = "Play",
                    icon = Icons.Filled.PlayArrow
                )
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = AppTheme.spacing.large
                ),
            onClick = {
                if (playProps.name == "Play") {
                    playProps = playProps.copy(name = "Stop", icon = Icons.Filled.Stop)
                } else {
                    playProps = playProps.copy(name = "Play", icon = Icons.Filled.PlayArrow)
                }
            },
            colors = ButtonDefaults.buttonColors(AppTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = playProps.icon,
                contentDescription = playProps.name
            )
        }
    }
}

private data class ButtonProperties(
    val name: String,
    val icon: ImageVector
)

@PreviewLightDark
@Composable
fun PreviewHome() {
    SoundSynthesizerTheme {
        HomeScreen()
    }
}
