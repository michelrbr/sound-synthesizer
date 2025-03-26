package br.com.michel.soundsynthesizer.presentation.screen

import br.com.michel.soundsynthesizer.domain.Wavetable
import br.com.michel.soundsynthesizer.domain.WavetableSynthesizer
import br.com.michel.soundsynthesizer.domain.getColor
import br.com.michel.soundsynthesizer.domain.testDispatcherProvider
import br.com.michel.soundsynthesizer.domain.testResourcesProvider
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
            Wavetable.SINE.getColor(),
            false
        ),
        WavetableState(
            Wavetable.TRIANGLE,
            testResourcesProvider.getString(R.string.button_label_triangle),
            Wavetable.TRIANGLE.getColor(),
            false
        ),
        WavetableState(
            Wavetable.SQUARE,
            testResourcesProvider.getString(R.string.button_label_square),
            Wavetable.SQUARE.getColor(),
            false
        ),
        WavetableState(
            Wavetable.SAW,
            testResourcesProvider.getString(R.string.button_label_saw),
            Wavetable.SAW.getColor(),
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
        val expected = defaultWavetables.mapIndexed { _, wavetableState ->
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
    fun `Given setWavetable is triggered When Wavetable is different from current selection Then call synthesizer setWavetable with with the provided Wavetable`() = runTest {
        val mockedSelection = MutableStateFlow<Wavetable?>(null)

        coJustRun { synthesizer.setWavetable(any()) }
        every { synthesizer.selectedWavetable } returns mockedSelection

        viewModel.setWavetable(Wavetable.SINE)

        coVerify {
            synthesizer.setWavetable(Wavetable.SINE)
        }
    }

    @Test
    fun `Given setWavetable is triggered When Wavetable is equals to the current selection Then call synthesizer setWavetable with null`() = runTest {
        val mockedSelection = MutableStateFlow<Wavetable?>(Wavetable.SINE)

        coJustRun { synthesizer.setWavetable(any()) }
        every { synthesizer.selectedWavetable } returns mockedSelection

        viewModel.setWavetable(Wavetable.SINE)

        coVerify {
            synthesizer.setWavetable(null)
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
}
