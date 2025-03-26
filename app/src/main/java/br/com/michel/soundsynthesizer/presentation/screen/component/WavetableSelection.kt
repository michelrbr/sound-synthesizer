package br.com.michel.soundsynthesizer.presentation.screen.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import br.com.michel.soundsynthesizer.domain.Wavetable
import br.com.michel.soundsynthesizer.presentation.screen.model.WavetableState
import br.com.michel.soundsynthesizer.presentation.theme.AppTheme
import br.com.michel.soundsynthesizer.presentation.theme.spacing

@Composable
fun WavetableSelection(
    modifier: Modifier = Modifier,
    wavetables: List<WavetableState>,
    onSoundSelected: (Wavetable) -> Unit
) {

    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.small)
        ) {
            items(wavetables) { prop ->
                Button(
                    onClick = { onSoundSelected(prop.wavetable) },
                    colors = ButtonDefaults
                        .buttonColors(Color(prop.color), Color.White),
                    shape = RoundedCornerShape(AppTheme.spacing.small),
                    modifier = Modifier
                        .size(100.dp)
                        .padding(
                            if (prop.isSelected) 0.dp else 5.dp
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(prop.name)
                    }
                }
            }
        }
        else -> LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.small)
        ) {
            items(wavetables) { prop ->
                Button(
                    onClick = { onSoundSelected(prop.wavetable) },
                    colors = ButtonDefaults
                        .buttonColors(Color(prop.color), Color.White),
                    shape = RoundedCornerShape(AppTheme.spacing.small),
                    modifier = Modifier
                        .size(100.dp)
                        .padding(
                            if (prop.isSelected) 0.dp else 5.dp
                        )
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
}
