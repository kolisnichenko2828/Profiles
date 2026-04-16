package com.kolisnichenko2828.profiles.data.repository

import com.kolisnichenko2828.profiles.data.local.profile.ProfileDao
import com.kolisnichenko2828.profiles.data.local.profile.toDomain
import com.kolisnichenko2828.profiles.data.local.profile.toEntity
import com.kolisnichenko2828.profiles.domain.interfaces.profile.ProfileProvider
import com.kolisnichenko2828.profiles.domain.interfaces.profile.ProfileSaver
import com.kolisnichenko2828.profiles.domain.models.ProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException

class ProfileRepositoryImpl(
    private val dao: ProfileDao
) : ProfileProvider, ProfileSaver {

    override suspend fun get(): Result<ProfileModel?> {
        return withContext(Dispatchers.IO) {
            val result = runCatching { dao.getProfile() }
            result.fold(
                onSuccess = { entity ->
                    Result.success(entity?.toDomain())
                },
                onFailure = { exception ->
                    if (exception is CancellationException) throw exception
                    Result.failure(exception)
                }
            )
        }
    }

    override suspend fun save(profile: ProfileModel): Result<Unit> {
        return withContext(Dispatchers.IO) {
            runCatching { dao.insertProfile(profile.toEntity()) }
        }
    }
}