package com.nir.apod

import android.app.Application
import com.nir.apod.module.appModule
import com.nir.apod.module.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class APODApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // Setting up Timber to provide app logs with debug builds
            Timber.plant(Timber.DebugTree())
        }
        // starting Koin with dataModule & appModule modules
        startKoin {
            androidContext(this@APODApp)
            modules(arrayListOf(dataModule, appModule))
        }
    }
}