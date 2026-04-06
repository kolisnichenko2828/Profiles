package com.kolisnichenko2828.profiles.data.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kolisnichenko2828.profiles.domain.models.ProfileDomainModel

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = 0,
    val imageUri: String?,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
)

fun ProfileEntity.toDomain(): ProfileDomainModel {
    return ProfileDomainModel(
        imageUri = this.imageUri,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth
    )
}

fun ProfileDomainModel.toEntity(): ProfileEntity {
    return ProfileEntity(
        imageUri = this.imageUri,
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth
    )
}