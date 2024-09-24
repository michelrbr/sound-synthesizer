package br.com.michel.soundsynthesizer.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
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
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = AppTheme.spacing.extraLarge),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.normal),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.normal)
        ) {
            items(buttonList) { prop ->
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(prop.color!!),
                    shape = RoundedCornerShape(AppTheme.spacing.small),
                    modifier = Modifier.size(100.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(prop.name)
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SynthesizerSlider(
                modifier = Modifier.weight(1F),
                label = stringResource(R.string.slider_label_frequency),
                range = 40F..3000F,
                initialValue = 1000F
            )

            SynthesizerSlider(
                modifier = Modifier.weight(1F),
                label = stringResource(R.string.slider_label_volume),
                range = 1F..100F,
                initialValue = 50F
            )
        }

        var playProps by remember {
            mutableStateOf(
                ButtonProperties(
                    "Play",
                    null,
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
                imageVector = playProps.icon!!,
                contentDescription = playProps.name
            )
        }
    }
}

@Composable
fun SynthesizerSlider(
    modifier: Modifier = Modifier,
    label: String? = null,
    range: ClosedFloatingPointRange<Float> = 0f..1f,
    initialValue: Float = 0f,
) {
    Column(
        modifier = modifier
    ) {
        var value by remember { mutableStateOf(initialValue) }
        label?.let {
            Text(
                modifier = Modifier.padding(start = AppTheme.spacing.small),
                text = String.format(it, value)
            )
        }

        Slider(
            value = value,
            valueRange = range,
            onValueChange = { value = it }
        )
    }
}

data class ButtonProperties(
    val name: String = "",
    val color: Color?,
    val icon: ImageVector? = null
)

val buttonList = listOf(
    ButtonProperties("Sine", Color(0xFF148217)),
    ButtonProperties("Triangle", Color(0xFFd6d020)),
    ButtonProperties("Square", Color(0xFFb31e19)),
    ButtonProperties("Saw", Color(0xFFc96908)),
)

@PreviewLightDark
@Composable
fun preview() {
    SoundSynthesizerTheme {
        HomeScreen()
    }
}
