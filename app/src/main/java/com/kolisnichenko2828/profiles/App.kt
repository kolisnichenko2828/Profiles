package com.kolisnichenko2828.profiles

import android.app.Application
import com.kolisnichenko2828.profiles.di.contactsModule
import com.kolisnichenko2828.profiles.di.databaseModule
import com.kolisnichenko2828.profiles.di.viewModelsModule
import com.kolisnichenko2828.profiles.di.profilesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(databaseModule, contactsModule, profilesModule, viewModelsModule)
        }
    }
}