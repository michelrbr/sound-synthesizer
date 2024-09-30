package br.com.michel.soundsynthesizer.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class CoroutineDispatcherProvider @Inject constructor() {
    open val main: CoroutineDispatcher = Dispatchers.Main
    open val io: CoroutineDispatcher = Dispatchers.IO
}
