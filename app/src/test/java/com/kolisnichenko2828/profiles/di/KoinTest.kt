package com.kolisnichenko2828.profiles.di

import android.content.Context
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class KoinTest : KoinTest {
    @Test
    fun checkKoinModules() {
        val allModules = module {
            includes(
                dataModule,
                repositoryModule,
                viewModelsModule
            )
        }

        allModules.verify(
            extraTypes = listOf(
                Context::class
            )
        )
    }
}