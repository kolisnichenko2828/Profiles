package com.kolisnichenko2828.profiles.data.repository

import com.kolisnichenko2828.profiles.data.local.contacts.ContactsDao
import com.kolisnichenko2828.profiles.data.local.contacts.toDomain
import com.kolisnichenko2828.profiles.data.local.contacts.toEntity
import com.kolisnichenko2828.profiles.data.remote.ContactsApi
import com.kolisnichenko2828.profiles.data.remote.toDomain
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactSaver
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactsFetcher
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactsProvider
import com.kolisnichenko2828.profiles.domain.models.ContactModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.CancellationException

class ContactsRepositoryImpl(
    private val api: ContactsApi,
    private val dao: ContactsDao
) : ContactSaver, ContactsProvider, ContactsFetcher {
    private var networkCache: List<ContactModel> = emptyList()

    override suspend fun save(contact: ContactModel): Result<Unit> {
        val result = runCatching { dao.insert(contact.toEntity()) }

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

    override suspend fun getAll(limit: Int): Flow<List<ContactModel>> {
        return dao.getAll(limit).map { it.toDomain() }
    }

    override suspend fun getById(id: String): Result<ContactModel?> {
        val result = runCatching { dao.getById(id) }

        result.fold(
            onSuccess = { entity ->
                if (entity != null) {
                    return Result.success(entity.toDomain())
                } else {
                    return Result.success(networkCache.find { it.id == id })
                }
            },
            onFailure = { exception ->
                if (exception is CancellationException) throw exception
                return Result.failure(exception)
            }
        )
    }

    override suspend fun fetch(): Result<List<ContactModel>> {
        val result = runCatching { api.getContacts().toDomain() }

        result.fold(
            onSuccess = { contacts ->
                networkCache = contacts
                return Result.success(contacts)
            },
            onFailure = { exception ->
                return Result.failure(exception)
            }
        )
    }
}