package com.kolisnichenko2828.profiles.domain.interfaces

import com.kolisnichenko2828.profiles.domain.models.ProfileDomainModel

interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileDomainModel?>
    suspend fun saveProfile(profile: ProfileDomainModel): Result<Unit>
    suspend fun saveImage(uri: String): Result<String>
}