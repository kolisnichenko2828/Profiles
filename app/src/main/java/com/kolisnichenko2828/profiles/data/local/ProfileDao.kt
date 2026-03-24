package com.kolisnichenko2828.profiles.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile LIMIT 1")
    suspend fun getProfile(): ProfileEntityModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(user: ProfileEntityModel)
}