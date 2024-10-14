package br.com.michel.soundsynthesizer.domain

import io.mockk.clearMocks
import io.mockk.justRun
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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

    @AfterTest
    fun shutdown() {
        clearMocks(testScope)
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
    fun `Given setFrequency is triggered When value is 0,0 Then update frequencyInHz to 40`() = runTest {
        val expected = 40F
        synthesizerImpl.setFrequency(0F)

        assertEquals(
            expected,
            synthesizerImpl.frequencyInHz.value
        )
    }

    @Test
    fun `Given setFrequency is triggered When value is 0,5 Then update frequencyInHz to 1520`() = runTest {
        val expected = 1520F
        synthesizerImpl.setFrequency(0.5F)

        assertEquals(
            expected,
            synthesizerImpl.frequencyInHz.value
        )
    }

    @Test
    fun `Given setFrequency is triggered When value is 1,0 Then update frequencyInHz to 3000`() = runTest {
        val expected = 3000F
        synthesizerImpl.setFrequency(1F)

        assertEquals(
            expected,
            synthesizerImpl.frequencyInHz.value
        )
    }

    @Test
    fun `Given setFrequency is triggered When the value is more than 1,0 Then throw an IllegalArgumentException`() {
        val expected = 1.1F
        assertFailsWith(IllegalArgumentException::class) {
            runTest { synthesizerImpl.setFrequency(expected) }
        }
    }

    @Test
    fun `Given setFrequency is triggered When the value is less than 0,0 Then throw an IllegalArgumentException`() {
        val expected = -1F
        assertFailsWith(IllegalArgumentException::class) {
            runTest { synthesizerImpl.setFrequency(expected) }
        }
    }

    @Test
    fun `Given setVolume is triggered When value is 0,0 Then update volumeInDb to -60`() = runTest {
        val expected = -60F
        synthesizerImpl.setVolume(0F)

        assertEquals(
            expected,
            synthesizerImpl.volumeInDb.value
        )
    }

    @Test
    fun `Given setVolume is triggered When value is 0,5 Then update volumeInDb to -30`() = runTest {
        val expected = -30F
        synthesizerImpl.setVolume(0.5F)

        assertEquals(
            expected,
            synthesizerImpl.volumeInDb.value
        )
    }

    @Test
    fun `Given setVolume is triggered When value is 1,0 Then update volumeInDb to 0`() = runTest {
        val expected = 0F
        synthesizerImpl.setVolume(1F)

        assertEquals(
            expected,
            synthesizerImpl.volumeInDb.value
        )
    }

    @Test
    fun `Given setVolume is triggered When the value is more than 1,0 Then throw an IllegalArgumentException`() {
        val expected = 1.1F
        assertFailsWith(IllegalArgumentException::class) {
            runTest { synthesizerImpl.setVolume(expected) }
        }
    }

    @Test
    fun `Given setVolume is triggered When the value is less than 0,0 Then throw an IllegalArgumentException`() {
        val expected = -1F
        assertFailsWith(IllegalArgumentException::class) {
            runTest { synthesizerImpl.setVolume(expected) }
        }
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
