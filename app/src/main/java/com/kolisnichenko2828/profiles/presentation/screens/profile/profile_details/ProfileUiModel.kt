package com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details

import com.kolisnichenko2828.profiles.domain.models.ProfileDomainModel

data class ProfileUiModel(
    val imageUri: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val dateOfBirth: String = "",
)

fun ProfileDomainModel.toUi(): ProfileUiModel {
    return ProfileUiModel(
        imageUri = this.imageUri,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth
    )
}