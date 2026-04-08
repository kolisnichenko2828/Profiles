package com.kolisnichenko2828.profiles.data.remote

import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.domain.models.ContactModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactDto(
    @SerialName("uuid") val uuid: String,
    @SerialName("picture") val picture: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("cell") val cell: String,
    @SerialName("email") val email: String,
    @SerialName("dob") val dob: String,
)

fun ContactDto.toDomain(): ContactModel {
    return ContactModel(
        id = this.uuid,
        imageUri = this.picture,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.cell,
        email = this.email,
        dateOfBirth = this.dob,
        category = ContactCategory.NONE
    )
}

fun List<ContactDto>.toDomain(): List<ContactModel> {
    return this.map { it.toDomain() }
}