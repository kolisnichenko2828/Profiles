package com.kolisnichenko2828.profiles.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kolisnichenko2828.profiles.domain.ProfileDomainModel

@Entity(tableName = "profile")
data class ProfileEntityModel(
    @PrimaryKey val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
)

fun ProfileEntityModel.toDomain(): ProfileDomainModel {
    return ProfileDomainModel(
        firstName = this.firstName,
        lastName = this.lastName,
        phone = this.phone,
        email = this.email,
        dateOfBirth = this.dateOfBirth
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