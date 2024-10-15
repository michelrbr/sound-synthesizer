package br.com.michel.soundsynthesizer.presentation.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.michel.soundsynthesizer.presentation.theme.AppTheme
import br.com.michel.soundsynthesizer.presentation.theme.spacing

@Composable
fun SynthesizerSlider(
    modifier: Modifier = Modifier,
    label: String? = null,
    initialValue: Float = 0f,
    onValueChange: (Float) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        var value by remember { mutableFloatStateOf(initialValue) }
        label?.let {
            Text(
                modifier = Modifier.padding(start = AppTheme.spacing.small),
                text = it
            )
        }

        Slider(
            value = value,
            onValueChange = {
                value = it
                onValueChange(it)
            }
        )
    }
}
