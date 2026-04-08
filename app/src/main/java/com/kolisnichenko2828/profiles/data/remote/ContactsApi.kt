package com.kolisnichenko2828.profiles.data.remote

import com.kolisnichenko2828.profiles.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ContactsApi {
    @GET("randomuser")
    suspend fun getContacts(
        @Header("X-Api-Key") apiKey: String = BuildConfig.RANDOM_API,
        @Query("count") count: Int = 30,
    ): List<ContactDto>
}