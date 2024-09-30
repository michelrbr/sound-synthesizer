package br.com.michel.soundsynthesizer.di

import br.com.michel.soundsynthesizer.domain.ResourceProviderImpl
import br.com.michel.soundsynthesizer.domain.ResourcesProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindsResourceProvider(
        providerImpl: ResourceProviderImpl
    ): ResourcesProvider
}
