package com.kolisnichenko2828.profiles

import android.app.Application
import com.kolisnichenko2828.profiles.di.dataModule
import com.kolisnichenko2828.profiles.di.repositoryModule
import com.kolisnichenko2828.profiles.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                repositoryModule,
                viewModelsModule
            )
        }
    }
}