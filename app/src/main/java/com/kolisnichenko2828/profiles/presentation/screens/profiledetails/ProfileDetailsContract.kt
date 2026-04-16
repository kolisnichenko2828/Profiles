package com.kolisnichenko2828.profiles.presentation.screens.profiledetails

interface ProfileDetailsContract {
    data class State(
        val profile: ProfileUiModel? = null,
        val errorMessage: String? = null
    )

    sealed interface Event {
        object InitialLoad : Event
    }
}