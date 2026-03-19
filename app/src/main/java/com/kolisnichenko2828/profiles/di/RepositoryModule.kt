package com.kolisnichenko2828.profiles.di

import com.kolisnichenko2828.profiles.data.local.UsersRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::UsersRepository)
}