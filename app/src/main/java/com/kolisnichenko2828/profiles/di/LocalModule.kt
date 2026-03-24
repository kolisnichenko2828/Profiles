package com.kolisnichenko2828.profiles.di

import androidx.room.Room
import com.kolisnichenko2828.profiles.data.local.ContactsDao
import com.kolisnichenko2828.profiles.data.local.AppDatabase
import com.kolisnichenko2828.profiles.data.local.ProfileDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "users"
        ).build()
    }

    single<ContactsDao> {
        get<AppDatabase>().contactsDao()
    }

    single<ProfileDao> {
        get<AppDatabase>().profileDao()
    }
}