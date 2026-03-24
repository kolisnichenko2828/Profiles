package com.kolisnichenko2828.profiles.domain

interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileDomainModel>
    suspend fun saveProfile(profile: ProfileDomainModel): Result<Unit>
}