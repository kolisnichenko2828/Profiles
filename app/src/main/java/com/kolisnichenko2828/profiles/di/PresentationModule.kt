package com.kolisnichenko2828.profiles.di

import com.kolisnichenko2828.profiles.MainViewModel
import com.kolisnichenko2828.profiles.presentation.details.DetailsViewModel
import com.kolisnichenko2828.profiles.presentation.users.UsersViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::UsersViewModel)
    viewModelOf(::DetailsViewModel)
}