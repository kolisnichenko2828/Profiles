package com.kolisnichenko2828.profiles.data.repository

import com.kolisnichenko2828.profiles.data.local.ContactsDao
import com.kolisnichenko2828.profiles.data.local.toDomain
import com.kolisnichenko2828.profiles.domain.ContactDomainModel
import com.kolisnichenko2828.profiles.domain.toEntity
import java.util.concurrent.CancellationException

class ContactsRepository(
    val contactsDao: ContactsDao
) {
    suspend fun getContacts(offset: Int, limit: Int): Result<List<ContactDomainModel>> {
        val localEntities = runCatching { contactsDao.getContacts(offset = offset, limit = limit) }
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

    suspend fun saveContact(contact: ContactDomainModel): Result<Unit> {
        val result = runCatching { contactsDao.insertContacts(contact.toEntity()) }
        result.fold(
            onSuccess = {
                return Result.success(Unit)
            },
            onFailure = { localException ->
                if (localException is CancellationException) throw localException
                return Result.failure(localException)
            }
        )
    }
}