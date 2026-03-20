package com.kolisnichenko2828.profiles.presentation.profile.profile_details

import com.kolisnichenko2828.profiles.presentation.models.ProfileUiModel

interface ProfileDetailsContract {
    data class State(
        val profile: ProfileUiModel? = null,
        val errorMessage: String? = null
    )

    sealed interface Event {
        object InitialLoad : Event
    }
}