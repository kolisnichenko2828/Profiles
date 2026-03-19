package com.kolisnichenko2828.profiles.di

import androidx.room.Room
import com.kolisnichenko2828.profiles.data.local.UsersDao
import com.kolisnichenko2828.profiles.data.local.UsersDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single<UsersDatabase> {
        Room.databaseBuilder(
            androidContext(),
            UsersDatabase::class.java,
            "users"
        ).build()
    }

    single<UsersDao> {
        get<UsersDatabase>().usersDao()
    }
}