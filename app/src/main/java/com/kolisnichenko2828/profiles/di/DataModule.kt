package com.kolisnichenko2828.profiles.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kolisnichenko2828.profiles.data.local.AppDatabase
import com.kolisnichenko2828.profiles.data.local.contacts.ContactsDao
import com.kolisnichenko2828.profiles.data.local.profile.ProfileDao
import com.kolisnichenko2828.profiles.data.remote.ContactsApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.api-ninjas.com/v2/"

val dataModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "database"
        ).build()
    }

    single<ProfileDao> {
        get<AppDatabase>().profileDao()
    }

    single<ContactsDao> {
        get<AppDatabase>().contactsDao()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        val networkJson = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(networkJson.asConverterFactory(contentType))
            .build()
    }

    single<ContactsApi> {
        get<Retrofit>().create(ContactsApi::class.java)
    }
}