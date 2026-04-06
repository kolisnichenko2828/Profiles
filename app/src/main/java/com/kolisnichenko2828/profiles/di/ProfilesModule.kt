package com.kolisnichenko2828.profiles.di

import com.kolisnichenko2828.profiles.data.database.AppDatabase
import com.kolisnichenko2828.profiles.data.profile.ProfileDao
import com.kolisnichenko2828.profiles.data.profile.ProfileFileStorageImpl
import com.kolisnichenko2828.profiles.data.profile.ProfileRepositoryImpl
import com.kolisnichenko2828.profiles.domain.interfaces.LocalFileStorage
import com.kolisnichenko2828.profiles.domain.interfaces.ProfileRepository
import org.koin.dsl.module

val profilesModule = module {
    single<ProfileDao> {
        get<AppDatabase>().profileDao()
    }
    factory<LocalFileStorage> {
        ProfileFileStorageImpl(
            context = get()
        )
    }
    single<ProfileRepository> {
        ProfileRepositoryImpl(
            dao = get(),
            fileStorage = get()
        )
    }
}