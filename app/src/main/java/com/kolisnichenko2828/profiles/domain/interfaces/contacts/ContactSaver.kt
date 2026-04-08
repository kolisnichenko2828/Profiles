package com.kolisnichenko2828.profiles.domain.interfaces.contacts

import com.kolisnichenko2828.profiles.domain.models.ContactModel

interface ContactSaver {
    suspend fun save(contact: ContactModel): Result<Unit>
}