package com.example.golfperformance

import android.app.Application
import com.example.data.di.dataModule
import com.example.golfperf.di.domainModule
import com.example.golfperf.players.di.playersModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GolfPerformanceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GolfPerformanceApp)
            modules(dataModule, domainModule, playersModule)
        }
    }
}
