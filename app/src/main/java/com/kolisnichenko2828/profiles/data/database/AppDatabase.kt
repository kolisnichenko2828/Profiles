package com.kolisnichenko2828.profiles.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kolisnichenko2828.profiles.data.contacts.ContactEntity
import com.kolisnichenko2828.profiles.data.contacts.ContactsDao
import com.kolisnichenko2828.profiles.data.profile.ProfileDao
import com.kolisnichenko2828.profiles.data.profile.ProfileEntity

@Database(entities = [ContactEntity::class, ProfileEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
    abstract fun profileDao(): ProfileDao
}