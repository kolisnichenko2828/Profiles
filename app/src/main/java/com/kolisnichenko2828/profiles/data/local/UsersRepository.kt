package com.kolisnichenko2828.profiles.data.local

import com.kolisnichenko2828.profiles.domain.UsersDomain
import java.util.concurrent.CancellationException

class UsersRepository(
    val dao: UsersDao
) {
    suspend fun getUsers(offset: Int, limit: Int): Result<List<UsersDomain>> {
        val localEntities = runCatching { dao.getUsers(offset = offset, limit = limit) }
        localEntities.fold(
            onSuccess = { entities ->
                return Result.success(entities.toDomain())
            },
            onFailure = { localException ->
                if (localException is CancellationException) throw localException
                return Result.failure(localException)
            }
        )
    }

    suspend fun getOwnProfile(): Result<UsersDomain> {
        val ownProfile = runCatching { dao.getOwnProfile() }
        ownProfile.fold(
            onSuccess = { entity ->
                if (entity != null) return Result.success(entity.toDomain())
                return Result.failure(Exception("Unknown Exception"))
            },
            onFailure = { localException ->
                if (localException is CancellationException) throw localException
                return Result.failure(localException)
            }
        )
    }
}