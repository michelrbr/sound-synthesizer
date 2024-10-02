package br.com.michel.soundsynthesizer.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import br.com.michel.soundsynthesizer.domain.Wavetable
import br.com.michel.soundsynthesizer.domain.testDispatcherProvider
import br.com.michel.soundsynthesizer.domain.testResourcesProvider
import br.com.michel.soundsynthesizer.presentation.screen.model.PlayButtonState
import br.com.michel.soundsynthesizer.presentation.screen.model.SliderState
import br.com.michel.soundsynthesizer.presentation.screen.model.WavetableState
import com.michel.soundsynthesizer.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    private val defaultWavetables = listOf(
        WavetableState(
            Wavetable.SINE,
            testResourcesProvider.getString(R.string.button_label_sine),
            0xFF148217,
            false
        ),
        WavetableState(
            Wavetable.TRIANGLE,
            testResourcesProvider.getString(R.string.button_label_triangle),
            0xFFD6D020,
            false
        ),
        WavetableState(
            Wavetable.SQUARE,
            testResourcesProvider.getString(R.string.button_label_square),
            0xFFB31E19,
            false
        ),
        WavetableState(
            Wavetable.SAW,
            testResourcesProvider.getString(R.string.button_label_saw),
            0xFFC96908,
            false
        ),
    )

    @BeforeTest
    fun setUp() {
        viewModel = HomeViewModel(
            testDispatcherProvider,
            testResourcesProvider
        )
    }

    @Test
    fun `Given a new HomeViewModel Then check default wavetables`() = runTest {
        val expected = defaultWavetables

        assertEquals(
            expected,
            viewModel.wavetableStates.first(),
        )
    }

    @Test
    fun `Given setWavetable is triggered Then update it`() = runTest {
        val expected = defaultWavetables.mapIndexed { index, wavetableState ->
            wavetableState.copy(isSelected = index == 0)
        }

        viewModel.setWavetable(Wavetable.SINE)

        assertEquals(
            expected,
            viewModel.wavetableStates.first(),
        )
    }

    @Test
    fun `Given a new HomeViewModel Then check default frequencyState`() = runTest {
        val expected = SliderState(
            label = testResourcesProvider.getString(
                R.string.slider_label_frequency,
                HomeViewModel.DEFAULT_FREQUENCY
            ),
            defaultValue = HomeViewModel.DEFAULT_FREQUENCY
        )

        assertEquals(
            expected,
            viewModel.frequencyState.first(),
        )
    }

    @Test
    fun `Given setFrequency is triggered Then update frequencyState`() = runTest {
        val expected = SliderState(
            label = testResourcesProvider.getString(
                R.string.slider_label_frequency,
                2000F
            ),
            defaultValue = HomeViewModel.DEFAULT_FREQUENCY
        )

        viewModel.setFrequency(2000F)

        assertEquals(
            expected,
            viewModel.frequencyState.first()
        )
    }

    @Test
    fun `Given a new HomeViewModel Then check default volumeState`() = runTest {
        val expected = SliderState(
            label = testResourcesProvider.getString(
                R.string.slider_label_volume,
                HomeViewModel.DEFAULT_VOLUME
            ),
            defaultValue = HomeViewModel.DEFAULT_VOLUME
        )

        assertEquals(
            expected,
            viewModel.volumeState.first()
        )
    }

    @Test
    fun `Given setVolume is triggered Then update volumeState`() = runTest {
        val expected = SliderState(
            label = testResourcesProvider.getString(
                R.string.slider_label_volume,
                80F
            ),
            defaultValue = HomeViewModel.DEFAULT_VOLUME
        )

        viewModel.setVolume(80F)

        assertEquals(
            expected,
            viewModel.volumeState.first()
        )
    }

    @Test
    fun `Given a new HomeViewModel Then check default playState`() = runTest {
        val expected = PlayButtonState(
            icon = Icons.Filled.PlayArrow,
            contentDescription = R.string.play_content_description.toString()
        )

        assertEquals(
            expected,
            viewModel.playState.first()
        )
    }

    @Test
    fun `Given togglePayStop is triggered Then update playState`() = runTest {
        val expected = PlayButtonState(
            icon = Icons.Filled.Stop,
            contentDescription = R.string.stop_content_description.toString()
        )

        viewModel.togglePayStop()

        assertEquals(
            expected,
            viewModel.playState.first()
        )
    }
}
