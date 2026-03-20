package com.kolisnichenko2828.profiles.presentation.models

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

data class ProfileUiModel(
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
)