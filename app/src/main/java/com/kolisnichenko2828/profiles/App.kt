package com.kolisnichenko2828.profiles

import android.app.Application
import com.kolisnichenko2828.profiles.di.localModule
import com.kolisnichenko2828.profiles.di.presentationModule
import com.kolisnichenko2828.profiles.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(localModule, repositoryModule, presentationModule)
        }
    }
}