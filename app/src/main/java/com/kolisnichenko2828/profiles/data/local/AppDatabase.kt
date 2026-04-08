package com.kolisnichenko2828.profiles.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kolisnichenko2828.profiles.data.local.contacts.ContactEntity
import com.kolisnichenko2828.profiles.data.local.contacts.ContactsDao
import com.kolisnichenko2828.profiles.data.local.profile.ProfileDao
import com.kolisnichenko2828.profiles.data.local.profile.ProfileEntity

@Database(entities = [ContactEntity::class, ProfileEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
    abstract fun profileDao(): ProfileDao
}