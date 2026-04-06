package com.kolisnichenko2828.profiles.di

import com.kolisnichenko2828.profiles.data.contacts.ContactsDao
import com.kolisnichenko2828.profiles.data.contacts.ContactsRepositoryImpl
import com.kolisnichenko2828.profiles.data.database.AppDatabase
import com.kolisnichenko2828.profiles.domain.interfaces.ContactsRepository
import org.koin.dsl.module

val contactsModule = module {
    single<ContactsRepository> {
        ContactsRepositoryImpl(
            contactsDao = get()
        )
    }
    single<ContactsDao> {
        get<AppDatabase>().contactsDao()
    }
}