package com.kolisnichenko2828.profiles.di

import androidx.room.Room
import com.kolisnichenko2828.profiles.data.local.ContactsDao
import com.kolisnichenko2828.profiles.data.local.ContactsDatabase
import com.kolisnichenko2828.profiles.data.local.ProfileDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single<ContactsDatabase> {
        Room.databaseBuilder(
            androidContext(),
            ContactsDatabase::class.java,
            "users"
        ).build()
    }

    single<ContactsDao> {
        get<ContactsDatabase>().contactsDao()
    }

    single<ProfileDao> {
        get<ContactsDatabase>().profileDao()
    }
}