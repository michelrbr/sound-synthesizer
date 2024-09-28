package br.com.michel.soundsynthesizer

import android.app.Application
import com.michel.soundsynthesizer.BuildConfig
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
