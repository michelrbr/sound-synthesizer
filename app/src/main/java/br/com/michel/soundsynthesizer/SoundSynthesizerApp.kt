package br.com.michel.soundsynthesizer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.michel.soundsynthesizer.presentation.screen.HomeScreen
import br.com.michel.soundsynthesizer.presentation.theme.SoundSynthesizerTheme
import com.michel.soundsynthesizer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundSynthesizerApp() {
    SoundSynthesizerTheme {
        Scaffold(
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    title = {Text(stringResource(R.string.app_name))}
                )
            }
        ){ innerPadding ->
            HomeScreen(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            )
        }
    }
}


@PreviewLightDark
@Composable
fun PreviewApp() {
    SoundSynthesizerApp()
}