package com.kolisnichenko2828.profiles.di

import com.kolisnichenko2828.profiles.data.repository.ContactsRepositoryImpl
import com.kolisnichenko2828.profiles.data.repository.ImageSaverImpl
import com.kolisnichenko2828.profiles.data.repository.ProfileRepositoryImpl
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactSaver
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactsFetcher
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactsProvider
import com.kolisnichenko2828.profiles.domain.interfaces.profile.ImageSaver
import com.kolisnichenko2828.profiles.domain.interfaces.profile.ProfileProvider
import com.kolisnichenko2828.profiles.domain.interfaces.profile.ProfileSaver
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ContactsRepositoryImpl) {
        bind<ContactsProvider>()
        bind<ContactSaver>()
        bind<ContactsFetcher>()
    }

    singleOf(::ProfileRepositoryImpl) {
        bind<ProfileProvider>()
        bind<ProfileSaver>()
    }

    factory<ImageSaver> { ImageSaverImpl(androidContext()) }
}