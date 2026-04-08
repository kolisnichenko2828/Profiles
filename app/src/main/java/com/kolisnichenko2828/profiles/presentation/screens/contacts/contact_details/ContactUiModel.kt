package com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details

import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.domain.models.ContactModel

data class ContactUiModel(
    val id: String = "",
    val imageUri: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val dateOfBirth: String = "",
    val category: ContactCategory = ContactCategory.NONE
)

fun ContactModel.toUi(): ContactUiModel {
    return ContactUiModel(
        id = this.id,
        imageUri = this.imageUri,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth,
        category = this.category
    )
}

fun ContactUiModel.toDomain(): ContactModel {
    return ContactModel(
        id = this.id,
        imageUri = this.imageUri,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth,
        category = this.category
    )
}