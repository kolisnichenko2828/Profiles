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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException

class ContactsRepositoryImpl(
    private val api: ContactsApi,
    private val dao: ContactsDao
) : ContactSaver, ContactsProvider, ContactsFetcher {
    private var networkCache: List<ContactModel> = emptyList()

    override suspend fun save(contact: ContactModel): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val result = runCatching { dao.insert(contact.toEntity()) }
            result.fold(
                onSuccess = {
                    Result.success(Unit)
                },
                onFailure = { localException ->
                    if (localException is CancellationException) throw localException
                    Result.failure(localException)
                }
            )
        }
    }

    override suspend fun getAll(limit: Int): Flow<List<ContactModel>> {
        return withContext(Dispatchers.IO) {
            dao.getAll(limit).map { it.toDomain() }
        }
    }

    override suspend fun getById(id: String): Result<ContactModel?> {
        return withContext(Dispatchers.IO) {
            val result = runCatching { dao.getById(id) }
            result.fold(
                onSuccess = { entity ->
                    if (entity != null) {
                        Result.success(entity.toDomain())
                    } else {
                        Result.success(networkCache.find { it.id == id })
                    }
                },
                onFailure = { exception ->
                    if (exception is CancellationException) throw exception
                    Result.failure(exception)
                }
            )
        }
    }

    override suspend fun fetch(): Result<List<ContactModel>> {
        return withContext(Dispatchers.IO) {
            val result = runCatching { api.getContacts().toDomain() }
            result.fold(
                onSuccess = { contacts ->
                    networkCache = contacts
                    Result.success(contacts)
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
        }
    }
}