package com.kolisnichenko2828.profiles.domain.interfaces.profile

interface ImageSaver {
    suspend fun save(uri: String): Result<String>
}