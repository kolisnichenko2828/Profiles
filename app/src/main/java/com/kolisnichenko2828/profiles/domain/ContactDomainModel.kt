package com.kolisnichenko2828.profiles.domain

import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.data.local.ContactEntity
import com.kolisnichenko2828.profiles.presentation.contacts.contacts_details.model.ContactUiModel

data class ContactDomainModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
    val category: ContactCategory
)

fun ContactDomainModel.toEntity(): ContactEntity {
    return ContactEntity(
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth,
        category = this.category
    )
}

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