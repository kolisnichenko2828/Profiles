package com.kolisnichenko2828.profiles.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.domain.ContactDomainModel

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
    val category: ContactCategory
)

fun ContactEntity.toDomain(): ContactDomainModel {
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

fun List<ContactEntity>.toDomain(): List<ContactDomainModel> {
    return this.map {
        it.toDomain()
    }
}