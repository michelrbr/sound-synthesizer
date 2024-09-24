package br.com.michel.soundsynthesizer.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.michel.soundsynthesizer.presentation.theme.AppTheme
import br.com.michel.soundsynthesizer.presentation.theme.spacing
import com.michel.soundsynthesizer.R

@Composable
fun SynthesizerSoundSelection(
    modifier: Modifier = Modifier,
    onSoundSelected: (String) -> Unit
) {
    val buttonList = listOf(
        ButtonProperties(stringResource(R.string.button_label_sine), Color(0xFF148217)),
        ButtonProperties(stringResource(R.string.button_label_triangle), Color(0xFFd6d020)),
        ButtonProperties(stringResource(R.string.button_label_square), Color(0xFFb31e19)),
        ButtonProperties(stringResource(R.string.button_label_saw), Color(0xFFc96908)),
    )

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.normal),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.normal)
    ) {
        items(buttonList) { prop ->
            Button(
                onClick = { onSoundSelected(prop.name) },
                colors = ButtonDefaults.buttonColors(prop.color),
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
}

private data class ButtonProperties(
    val name: String,
    val color: Color,
)
