package br.com.michel.soundsynthesizer.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

open class CoroutineDispatcherProvider {
    open val main: CoroutineDispatcher = Dispatchers.Main
    open val io: CoroutineDispatcher = Dispatchers.IO
}
