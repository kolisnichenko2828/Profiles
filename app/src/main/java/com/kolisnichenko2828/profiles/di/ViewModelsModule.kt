package com.kolisnichenko2828.profiles.di

import com.kolisnichenko2828.profiles.MainViewModel
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_edit.ProfileEditViewModel
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details.ContactDetailsViewModel
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contacts_list.ContactsListViewModel
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details.ProfileDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::ContactsListViewModel)
    viewModelOf(::ContactDetailsViewModel)
    viewModelOf(::ProfileEditViewModel)
    viewModelOf(::ProfileDetailsViewModel)
}