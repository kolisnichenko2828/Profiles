package com.kolisnichenko2828.profiles.data.contacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactsDao {
    @Query("SELECT * FROM contacts ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getContacts(offset: Int, limit: Int): List<ContactEntity>

    @Query("SELECT * FROM contacts WHERE id = :id LIMIT 1")
    suspend fun getContact(id: Int): ContactEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contact: ContactEntity)

    @Query("DELETE FROM contacts")
    suspend fun clearAll()
}