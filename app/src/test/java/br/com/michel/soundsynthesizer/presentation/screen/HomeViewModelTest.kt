package br.com.michel.soundsynthesizer.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import br.com.michel.soundsynthesizer.domain.Wavetable
import br.com.michel.soundsynthesizer.domain.WavetableSynthesizer
import br.com.michel.soundsynthesizer.domain.testDispatcherProvider
import br.com.michel.soundsynthesizer.domain.testResourcesProvider
import br.com.michel.soundsynthesizer.presentation.screen.model.PlayButtonState
import br.com.michel.soundsynthesizer.presentation.screen.model.SliderState
import br.com.michel.soundsynthesizer.presentation.screen.model.WavetableState
import com.michel.soundsynthesizer.R
import io.mockk.clearMocks
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class HomeViewModelTest {

    private val synthesizer = mockk<WavetableSynthesizer>()

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
            testResourcesProvider,
            synthesizer
        )
    }

    @AfterTest
    fun shutDown() {
        clearMocks(synthesizer)
    }

    @Test
    fun `Given a list of Wavetables Then emit WavetableState list`() = runTest {
        val mockedWavetables = MutableStateFlow(Wavetable.entries)
        val mockedSelection = MutableStateFlow<Wavetable?>(null)
        val expected = defaultWavetables

        every { synthesizer.wavetables } returns mockedWavetables
        every { synthesizer.selectedWavetable } returns mockedSelection

        assertEquals(
            expected,
            viewModel.wavetableStates.first(),
        )
    }

    @Test
    fun `Given a Wavetable selection Then update Wavetable list with the selection`() = runTest {
        val mockedWavetables = MutableStateFlow(Wavetable.entries)
        val mockedSelection = MutableStateFlow<Wavetable?>(null)
        val expected = defaultWavetables.mapIndexed { index, wavetableState ->
            wavetableState.copy(isSelected = wavetableState.wavetable == Wavetable.SINE)
        }

        every { synthesizer.wavetables } returns mockedWavetables
        every { synthesizer.selectedWavetable } returns mockedSelection
        coJustRun { synthesizer.setWavetable(any()) }

        mockedSelection.emit(Wavetable.SINE)

        assertEquals(
            expected,
            viewModel.wavetableStates.first(),
        )
    }

    @Test
    fun `Given setWavetable is triggered Then call synthesizer setWavetable method`() = runTest {
        coJustRun { synthesizer.setWavetable(any()) }

        viewModel.setWavetable(Wavetable.SINE)

        coVerify {
            synthesizer.setWavetable(Wavetable.SINE)
        }
    }

    @Test
    fun `Given a new HomeViewModel Then check default frequencyState`() = runTest {
        val mockedFrequency = MutableStateFlow(1000F)
        val expected = SliderState(
            label = testResourcesProvider.getString(
                R.string.slider_label_frequency,
                mockedFrequency.value
            )
        )

        every { synthesizer.frequencyInHz } returns mockedFrequency

        assertEquals(
            expected,
            viewModel.frequencyState.first(),
        )
    }

    @Test
    fun `Given a frequency update Then update frequencyState`() = runTest {
        val mockedFrequency = MutableStateFlow(0F)
        val expected = SliderState(
            label = testResourcesProvider.getString(
                R.string.slider_label_frequency,
                2000F
            )
        )

        every { synthesizer.frequencyInHz } returns mockedFrequency

        mockedFrequency.emit(2000F)

        assertEquals(
            expected,
            viewModel.frequencyState.first()
        )
    }

    @Test
    fun `Given setFrequency is triggered Then call synthesizer frequencyState method`() = runTest {

        coJustRun { synthesizer.setFrequency(any()) }

        viewModel.setFrequency(2000F)

        coVerify {
            synthesizer.setFrequency(2000F)
        }
    }

    @Test
    fun `Given a new HomeViewModel Then check default volumeState`() = runTest {
        val mockedVolume = MutableStateFlow(50F)
        val expected = SliderState(
            label = testResourcesProvider.getString(
                R.string.slider_label_volume,
                mockedVolume.value
            )
        )

        every { synthesizer.volumeInDb } returns mockedVolume

        assertEquals(
            expected,
            viewModel.volumeState.first()
        )
    }

    @Test
    fun `Given setVolume is triggered Then update volumeState`() = runTest {
        val mockedVolume = MutableStateFlow(0F)
        val expected = SliderState(
            label = testResourcesProvider.getString(
                R.string.slider_label_volume,
                80F
            )
        )

        every { synthesizer.volumeInDb } returns mockedVolume
        mockedVolume.emit(80F)

        assertEquals(
            expected,
            viewModel.volumeState.first()
        )
    }

    @Test
    fun `Given setVolume is triggered Then update call synthesizer setVolume method`() = runTest {
        every { synthesizer.volumeInDb.value } returns 50F
        coJustRun { synthesizer.setVolume(any()) }

        viewModel.setVolume(80F)

        coVerify {
            synthesizer.setVolume(80F)
        }
    }

    @Test
    fun `Given playState starts to be collected Then emits default playState`() = runTest {
        val mockedIsPlaying = MutableStateFlow(false)
        val expected = PlayButtonState(
            icon = Icons.Filled.PlayArrow,
            contentDescription = R.string.play_content_description.toString()
        )

        every { synthesizer.isPlaying } returns mockedIsPlaying

        assertEquals(
            expected,
            viewModel.playState.first()
        )
    }

    @Test
    fun `Given isPlaying updates Then update playState`() = runTest {
        val mockedIsPlaying = MutableStateFlow(false)
        val expected = PlayButtonState(
            icon = Icons.Filled.Stop,
            contentDescription = R.string.stop_content_description.toString()
        )

        every { synthesizer.isPlaying } returns mockedIsPlaying
        coJustRun { synthesizer.play() }

        mockedIsPlaying.emit(true)

        assertEquals(
            expected,
            viewModel.playState.first()
        )
    }

    @Test
    fun `Given togglePayStop is triggered When sound is stopped Then trigger synthesizer's play`() = runTest{
        every { synthesizer.isPlaying.value } returns false
        coJustRun { synthesizer.play() }

        viewModel.togglePayStop()

        coVerify {
            synthesizer.play()
        }
    }

    @Test
    fun `Given togglePayStop is triggered When sound is playing Then trigger synthesizer's stop`() = runTest{
        every { synthesizer.isPlaying.value } returns true
        coJustRun { synthesizer.stop() }

        viewModel.togglePayStop()

        coVerify {
            synthesizer.stop()
        }
    }
}
