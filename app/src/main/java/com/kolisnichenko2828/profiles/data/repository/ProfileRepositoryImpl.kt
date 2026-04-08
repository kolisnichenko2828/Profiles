package com.kolisnichenko2828.profiles.data.repository

import com.kolisnichenko2828.profiles.data.local.profile.ProfileDao
import com.kolisnichenko2828.profiles.data.local.profile.toDomain
import com.kolisnichenko2828.profiles.data.local.profile.toEntity
import com.kolisnichenko2828.profiles.domain.interfaces.profile.ProfileProvider
import com.kolisnichenko2828.profiles.domain.interfaces.profile.ProfileSaver
import com.kolisnichenko2828.profiles.domain.models.ProfileModel
import java.util.concurrent.CancellationException

class ProfileRepositoryImpl(
    private val dao: ProfileDao
) : ProfileProvider, ProfileSaver {

    override suspend fun get(): Result<ProfileModel?> {
        val result = runCatching { dao.getProfile() }
        result.fold(
            onSuccess = { entity ->
                return Result.success(entity?.toDomain())
            },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                return Result.failure(exception)
            }
        )
    }

    override suspend fun save(profile: ProfileModel): Result<Unit> {
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