package com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details.model

import com.kolisnichenko2828.profiles.domain.ProfileDomainModel

data class ProfileUiModel(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
)

fun ProfileDomainModel.toUi(): ProfileUiModel {
    return ProfileUiModel(
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth
    )
}