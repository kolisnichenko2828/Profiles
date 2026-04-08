package com.kolisnichenko2828.profiles.data.local.contacts

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.domain.models.ContactModel

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey val id: String,
    val imageUri: String?,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
    val category: ContactCategory
)

fun ContactEntity.toDomain(): ContactModel {
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

fun List<ContactEntity>.toDomain(): List<ContactModel> {
    return this.map {
        it.toDomain()
    }
}

fun ContactModel.toEntity(): ContactEntity {
    return ContactEntity(
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