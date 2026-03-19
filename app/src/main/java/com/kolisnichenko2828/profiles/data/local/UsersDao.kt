package com.kolisnichenko2828.profiles.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao {
    @Query("SELECT * FROM users ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getUsers(offset: Int, limit: Int): List<UsersEntity>

    @Query("SELECT * FROM users WHERE id = 0 LIMIT 1")
    suspend fun getOwnProfile(): UsersEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: String): UsersEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UsersEntity>)

    @Query("DELETE FROM users")
    suspend fun clearAll()
}