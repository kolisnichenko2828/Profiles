package com.kolisnichenko2828.profiles.di

import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details.ContactDetailsViewModel
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_edit.ContactEditViewModel
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contacts_list.ContactsListViewModel
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details.ProfileDetailsViewModel
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_edit.ProfileEditViewModel
import com.kolisnichenko2828.profiles.presentation.screens.random.RandomViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::ContactsListViewModel)
    viewModelOf(::ContactDetailsViewModel)
    viewModelOf(::ContactEditViewModel)
    viewModelOf(::ProfileEditViewModel)
    viewModelOf(::ProfileDetailsViewModel)
    viewModelOf(::RandomViewModel)
}