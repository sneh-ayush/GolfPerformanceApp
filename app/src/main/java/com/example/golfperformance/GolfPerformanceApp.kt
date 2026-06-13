package com.example.golfperformance

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.data.di.dataModule
import com.example.golfperf.di.domainModule
import com.example.golfperf.players.di.playersModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class GolfPerformanceApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@GolfPerformanceApp)
            modules(dataModule, domainModule, playersModule)
        }
    }
}
