package com.ericg.neatflix.baseclass

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

class NeatFlixAppBaseClass : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)

        Timber.i("Initialized Timber and Stetho")
    }
}