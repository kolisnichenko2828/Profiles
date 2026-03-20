package com.kolisnichenko2828.profiles.data.repository

import com.kolisnichenko2828.profiles.data.local.ProfileDao
import com.kolisnichenko2828.profiles.data.local.toDomain
import com.kolisnichenko2828.profiles.domain.ProfileDomainModel
import com.kolisnichenko2828.profiles.domain.toEntity
import java.util.concurrent.CancellationException

class ProfileRepository(
    val dao: ProfileDao
) {
    suspend fun getProfile(): Result<ProfileDomainModel> {
        val ownProfile = runCatching { dao.getProfile() }
        ownProfile.fold(
            onSuccess = { entity ->
                return Result.success(entity.toDomain())
            },
            onFailure = { localException ->
                if (localException is CancellationException) throw localException
                return Result.failure(localException)
            }
        )
    }

    suspend fun saveProfile(profile: ProfileDomainModel): Result<Unit> {
        val result = runCatching { dao.insertProfile(profile.toEntity()) }
        result.fold(
            onSuccess = {
                return Result.success(Unit)
            },
            onFailure = { exception ->
                return Result.failure(exception)
            }
        )
    }
}