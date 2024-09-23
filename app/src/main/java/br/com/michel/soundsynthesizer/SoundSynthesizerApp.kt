package br.com.michel.soundsynthesizer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.michel.soundsynthesizer.ui.theme.AppTheme
import br.com.michel.soundsynthesizer.ui.theme.SoundSynthesizerTheme
import br.com.michel.soundsynthesizer.ui.theme.spacing
import com.michel.soundsynthesizer.R

@Composable
fun SoundSynthesizerApp() {
    SoundSynthesizerTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(stringResource(R.string.app_name))
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.normal),
                ) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colorScheme.primary
                        )
                    ) {
                        Text("Testing")
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colorScheme.secondary
                        )
                    ) {
                        Text("Testing")
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colorScheme.tertiary
                        )
                    ) {
                        Text("Testing")
                    }
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
fun previewApp() {
    SoundSynthesizerApp()
}