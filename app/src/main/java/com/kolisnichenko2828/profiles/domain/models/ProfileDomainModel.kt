package com.kolisnichenko2828.profiles.domain.models

data class ProfileDomainModel(
    val imageUri: String?,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
)