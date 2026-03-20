package com.kolisnichenko2828.profiles.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactEntityModel::class, ProfileEntityModel::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
    abstract fun profileDao(): ProfileDao
}