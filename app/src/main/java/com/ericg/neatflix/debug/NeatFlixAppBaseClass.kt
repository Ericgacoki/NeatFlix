package com.ericg.neatflix.debug

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

class NeatFlixAppBaseClass : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)

        Timber.e("Initialized Timber and Stetho")
    }
}