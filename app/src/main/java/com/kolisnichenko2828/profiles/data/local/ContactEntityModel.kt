package com.kolisnichenko2828.profiles.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kolisnichenko2828.profiles.core.ContactCategory

@Entity(tableName = "contacts")
data class ContactEntityModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
    val category: ContactCategory
)

@Entity(tableName = "own")
data class ProfileEntityModel(
    @PrimaryKey val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val dateOfBirth: String,
)