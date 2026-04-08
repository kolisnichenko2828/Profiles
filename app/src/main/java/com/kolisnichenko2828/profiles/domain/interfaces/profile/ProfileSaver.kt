package com.kolisnichenko2828.profiles.domain.interfaces.profile

import com.kolisnichenko2828.profiles.domain.models.ProfileModel

interface ProfileSaver {
    suspend fun save(profile: ProfileModel): Result<Unit>
}