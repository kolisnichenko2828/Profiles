package com.kolisnichenko2828.profiles.domain

import com.kolisnichenko2828.profiles.core.ContactCategory

data class ContactDomainModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
    val category: ContactCategory
)

data class ProfileDomainModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
)