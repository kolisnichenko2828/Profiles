package com.kolisnichenko2828.profiles.data.profile

import com.kolisnichenko2828.profiles.domain.interfaces.LocalFileStorage
import com.kolisnichenko2828.profiles.domain.interfaces.ProfileRepository
import com.kolisnichenko2828.profiles.domain.models.ProfileDomainModel
import java.util.concurrent.CancellationException

class ProfileRepositoryImpl(
    private val dao: ProfileDao,
    private val fileStorage: LocalFileStorage
) : ProfileRepository {
    override suspend fun getProfile(): Result<ProfileDomainModel?> {
        val result = runCatching { dao.getProfile() }
        result.fold(
            onSuccess = { entity ->
                return Result.success(entity?.toDomain())
            },
            onFailure = { localException ->
                if (localException is CancellationException) throw localException
                return Result.failure(localException)
            }
        )
    }

    override suspend fun saveProfile(profile: ProfileDomainModel): Result<Unit> {
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

    override suspend fun saveImage(uri: String): Result<String> {
        return fileStorage.saveImage(uri)
    }
}