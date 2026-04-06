package com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details

interface ProfileDetailsContract {
    data class State(
        val profile: ProfileUiModel? = null,
        val errorMessage: String? = null
    )

    sealed interface Event {
        object InitialLoad : Event
    }
}