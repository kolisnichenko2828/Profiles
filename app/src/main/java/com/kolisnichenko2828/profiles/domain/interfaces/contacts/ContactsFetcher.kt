package com.kolisnichenko2828.profiles.domain.interfaces.contacts

import com.kolisnichenko2828.profiles.domain.models.ContactModel

interface ContactsFetcher {
    suspend fun fetch(): Result<List<ContactModel>>
}