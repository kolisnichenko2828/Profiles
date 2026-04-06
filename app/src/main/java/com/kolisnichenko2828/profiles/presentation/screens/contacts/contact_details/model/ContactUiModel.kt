package com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details.model

import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.domain.models.ContactDomainModel

data class ContactUiModel(
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
    val category: ContactCategory
)

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