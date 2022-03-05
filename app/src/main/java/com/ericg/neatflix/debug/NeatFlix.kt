package com.ericg.neatflix.debug

import android.app.Application
import timber.log.Timber

class NeatFlixApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}