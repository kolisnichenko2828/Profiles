package com.kolisnichenko2828.profiles.data.contacts

import com.kolisnichenko2828.profiles.domain.models.ContactDomainModel
import com.kolisnichenko2828.profiles.domain.interfaces.ContactsRepository
import java.util.concurrent.CancellationException

class ContactsRepositoryImpl(
    private val contactsDao: ContactsDao
) : ContactsRepository {
    override suspend fun getContacts(offset: Int, limit: Int): Result<List<ContactDomainModel>> {
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

    override suspend fun saveContact(contact: ContactDomainModel): Result<Unit> {
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