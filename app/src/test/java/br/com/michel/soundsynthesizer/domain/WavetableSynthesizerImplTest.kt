package br.com.michel.soundsynthesizer.domain

import io.mockk.justRun
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class WavetableSynthesizerImplTest {

    private val testScope = spyk(TestScope())
    private lateinit var synthesizerImpl: WavetableSynthesizerImpl

    @BeforeTest
    fun setup() {
        synthesizerImpl = WavetableSynthesizerImpl(
            testScope,
            testDispatcherProvider
        )
    }

    @Test
    fun `Given a new WavetableSynthesizerImpl Then emit default wavetable list`() = runTest {
        assertEquals(
            Wavetable.entries,
            synthesizerImpl.wavetables.value
        )
    }

    @Test
    fun `Given setWavetable is triggered Then update selectedWavetable`() = runTest {
        val expected = Wavetable.SQUARE
        synthesizerImpl.setWavetable(expected)

        assertEquals(
            expected,
            synthesizerImpl.selectedWavetable.value
        )
    }

    @Test
    fun `Given setFrequency is triggered Then update frequencyInHz`() = runTest {
        val expected = 0.1F
        synthesizerImpl.setFrequency(expected)

        assertEquals(
            expected,
            synthesizerImpl.frequencyInHz.value
        )
    }

    @Test
    fun `Given setVolume is triggered Then update volumeInDb`() = runTest {
        val expected = 0.1F
        synthesizerImpl.setVolume(expected)

        assertEquals(
            expected,
            synthesizerImpl.volumeInDb.value
        )
    }

    @Test
    fun `Given play is triggered Then set isPlaying to true`() = runTest {
        val expected = true
        synthesizerImpl.play()

        assertEquals(
            expected,
            synthesizerImpl.isPlaying.value
        )
    }

    @Test
    fun `Given stop is triggered Then set isPlaying to false`() = runTest {
        val expected = false
        synthesizerImpl.stop()

        assertEquals(
            expected,
            synthesizerImpl.isPlaying.value
        )
    }

    @Test
    fun `Given close is triggered Then call scope cancel method`() = runTest {
        justRun { testScope.cancel() }
        synthesizerImpl.close()

        verify {
            testScope.cancel()
        }
    }
}
