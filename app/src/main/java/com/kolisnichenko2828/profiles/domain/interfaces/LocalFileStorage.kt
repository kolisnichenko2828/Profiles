package com.kolisnichenko2828.profiles.domain.interfaces

interface LocalFileStorage {
    suspend fun saveImage(uri: String): Result<String>
}