package com.kolisnichenko2828.profiles.di

import com.kolisnichenko2828.profiles.presentation.screens.addcontact.AddContactViewModel
import com.kolisnichenko2828.profiles.presentation.screens.contactslist.ContactsListViewModel
import com.kolisnichenko2828.profiles.presentation.screens.editprofile.ProfileEditViewModel
import com.kolisnichenko2828.profiles.presentation.screens.profiledetails.ProfileDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::ContactsListViewModel)
    viewModelOf(::ProfileEditViewModel)
    viewModelOf(::ProfileDetailsViewModel)
    viewModelOf(::AddContactViewModel)
}