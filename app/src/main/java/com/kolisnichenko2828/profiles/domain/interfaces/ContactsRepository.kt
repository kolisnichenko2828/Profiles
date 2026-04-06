package com.kolisnichenko2828.profiles.domain.interfaces

import com.kolisnichenko2828.profiles.domain.models.ContactDomainModel

interface ContactsRepository {
    suspend fun getContacts(offset: Int, limit: Int): Result<List<ContactDomainModel>>
    suspend fun saveContact(contact: ContactDomainModel): Result<Unit>
}