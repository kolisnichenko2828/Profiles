package com.kolisnichenko2828.profiles.domain.models

import com.kolisnichenko2828.profiles.core.ContactCategory
import java.util.UUID

data class ContactModel(
    val id: String = UUID.randomUUID().toString(),
    val imageUri: String?,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
    val category: ContactCategory
)