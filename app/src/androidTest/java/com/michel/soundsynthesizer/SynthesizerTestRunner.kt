package com.michel.soundsynthesizer

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class SynthesizerTestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(cl, HiltTestApplication::class.java.name, context)
}
