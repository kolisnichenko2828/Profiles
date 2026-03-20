package com.kolisnichenko2828.profiles.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactsDao {
    @Query("SELECT * FROM contacts ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getContacts(offset: Int, limit: Int): List<ContactEntityModel>

    @Query("SELECT * FROM contacts WHERE id = :id LIMIT 1")
    suspend fun getContact(id: Int): ContactEntityModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contact: ContactEntityModel)

    @Query("DELETE FROM contacts")
    suspend fun clearAll()
}

@Dao
interface ProfileDao {
    @Query("SELECT * FROM own LIMIT 1")
    suspend fun getProfile(): ProfileEntityModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(user: ProfileEntityModel)
}