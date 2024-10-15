package br.com.michel.soundsynthesizer.di

import br.com.michel.soundsynthesizer.domain.ResourceProviderImpl
import br.com.michel.soundsynthesizer.domain.ResourcesProvider
import br.com.michel.soundsynthesizer.domain.WavetableSynthesizer
import br.com.michel.soundsynthesizer.domain.WavetableSynthesizerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindsResourceProvider(
        providerImpl: ResourceProviderImpl
    ): ResourcesProvider

    @Binds
    fun bindsWavetableSynthesizer(
        synthesizerImpl: WavetableSynthesizerImpl
    ): WavetableSynthesizer

    companion object {
        @Provides
        @SynthesizerScope
        fun provideSynthesizerCoroutineScope() = CoroutineScope(SupervisorJob())
    }
}
