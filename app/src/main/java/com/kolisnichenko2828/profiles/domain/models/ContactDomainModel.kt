package com.kolisnichenko2828.profiles.domain.models

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