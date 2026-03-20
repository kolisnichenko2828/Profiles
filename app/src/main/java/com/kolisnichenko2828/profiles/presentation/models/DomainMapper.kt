package com.kolisnichenko2828.profiles.presentation.models

import com.kolisnichenko2828.profiles.domain.ContactDomainModel
import com.kolisnichenko2828.profiles.domain.ProfileDomainModel

fun ContactUiModel.toDomain(): ContactDomainModel {
    return ContactDomainModel(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth,
        category = this.category
    )
}

fun ProfileUiModel.toDomain(): ProfileDomainModel {
    return ProfileDomainModel(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth
    )
}