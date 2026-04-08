package com.kolisnichenko2828.profiles.domain.interfaces.contacts

import com.kolisnichenko2828.profiles.domain.models.ContactModel
import kotlinx.coroutines.flow.Flow

interface ContactsProvider {
    suspend fun getAll(limit: Int): Flow<List<ContactModel>>
    suspend fun getById(id: String): Result<ContactModel?>
}