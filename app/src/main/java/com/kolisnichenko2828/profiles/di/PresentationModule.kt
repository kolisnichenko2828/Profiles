package com.kolisnichenko2828.profiles.di

import com.kolisnichenko2828.profiles.MainViewModel
import com.kolisnichenko2828.profiles.presentation.profile.profile_create.ProfileCreateViewModel
import com.kolisnichenko2828.profiles.presentation.contacts.contacts_details.DetailsViewModel
import com.kolisnichenko2828.profiles.presentation.contacts.contacts_list.ListViewModel
import com.kolisnichenko2828.profiles.presentation.profile.profile_details.ProfileDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::ListViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::ProfileCreateViewModel)
    viewModelOf(::ProfileDetailsViewModel)
}