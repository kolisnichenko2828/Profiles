package com.kolisnichenko2828.profiles.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class UsersEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)