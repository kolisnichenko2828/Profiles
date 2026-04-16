package com.kolisnichenko2828.profiles.presentation.screens.contactslist

import androidx.compose.runtime.Immutable
import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.domain.models.ContactModel

@Immutable
data class ContactUiModel(
    val id: String,
    val imageUri: String?,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
    val category: ContactCategory
)

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

fun List<ContactUiModel>.toDomain(): List<ContactModel> {
    return this.map { it.toDomain() }
}

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

fun List<ContactModel>.toUi(): List<ContactUiModel> {
    return this.map { it.toUi() }
}