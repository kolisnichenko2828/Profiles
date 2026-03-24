package com.kolisnichenko2828.profiles.di

import com.kolisnichenko2828.profiles.data.repository.ContactsRepositoryImpl
import com.kolisnichenko2828.profiles.data.repository.ProfileRepositoryImpl
import com.kolisnichenko2828.profiles.domain.ContactsRepository
import com.kolisnichenko2828.profiles.domain.ProfileRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ContactsRepository> {
        ContactsRepositoryImpl(
            contactsDao = get()
        )
    }
    single<ProfileRepository> {
        ProfileRepositoryImpl(
            dao = get()
        )
    }
}