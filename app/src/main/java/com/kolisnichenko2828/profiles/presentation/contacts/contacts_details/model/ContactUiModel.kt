package com.kolisnichenko2828.profiles.presentation.contacts.contacts_details.model

import com.kolisnichenko2828.profiles.core.ContactCategory

data class ContactUiModel(
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
    val category: ContactCategory
)