package com.kolisnichenko2828.profiles.domain

import com.kolisnichenko2828.profiles.data.local.ContactEntityModel
import com.kolisnichenko2828.profiles.data.local.ProfileEntityModel

fun ContactDomainModel.toEntity(): ContactEntityModel {
    return ContactEntityModel(
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth,
        category = this.category
    )
}

fun ProfileDomainModel.toEntity(): ProfileEntityModel {
    return ProfileEntityModel(
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth
    )
}