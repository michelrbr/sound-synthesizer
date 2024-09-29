package br.com.michel.soundsynthesizer.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

val testDispatcherProvider = object : CoroutineDispatcherProvider() {
    override val io: CoroutineDispatcher = Dispatchers.Unconfined
    override val main: CoroutineDispatcher = Dispatchers.Unconfined
}
