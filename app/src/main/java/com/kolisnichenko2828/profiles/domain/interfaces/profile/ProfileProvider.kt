package com.kolisnichenko2828.profiles.domain.interfaces.profile

import com.kolisnichenko2828.profiles.domain.models.ProfileModel

interface ProfileProvider {
    suspend fun get(): Result<ProfileModel?>
}