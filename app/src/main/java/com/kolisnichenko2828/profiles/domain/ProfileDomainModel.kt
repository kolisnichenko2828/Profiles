package com.kolisnichenko2828.profiles.domain

import com.kolisnichenko2828.profiles.data.local.ProfileEntityModel
import com.kolisnichenko2828.profiles.presentation.profile.profile_details.model.ProfileUiModel

data class ProfileDomainModel(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
)

fun ProfileDomainModel.toEntity(): ProfileEntityModel {
    return ProfileEntityModel(
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth
    )
}

fun ProfileDomainModel.toUi(): ProfileUiModel {
    return ProfileUiModel(
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth
    )
}