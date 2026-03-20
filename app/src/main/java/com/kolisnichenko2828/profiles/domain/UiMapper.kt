package com.kolisnichenko2828.profiles.domain

import com.kolisnichenko2828.profiles.presentation.models.ContactUiModel
import com.kolisnichenko2828.profiles.presentation.models.ProfileUiModel

fun ContactDomainModel.toUi(): ContactUiModel {
    return ContactUiModel(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth,
        category = this.category
    )
}

fun ProfileDomainModel.toUi(): ProfileUiModel {
    return ProfileUiModel(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth
    )
}