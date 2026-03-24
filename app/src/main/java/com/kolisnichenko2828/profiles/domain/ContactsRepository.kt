package com.kolisnichenko2828.profiles.domain

interface ContactsRepository {
    suspend fun getContacts(offset: Int, limit: Int): Result<List<ContactDomainModel>>
    suspend fun saveContact(contact: ContactDomainModel): Result<Unit>
}