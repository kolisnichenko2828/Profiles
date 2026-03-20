package com.kolisnichenko2828.profiles.presentation.profile.profile_create

import com.kolisnichenko2828.profiles.presentation.models.ProfileUiModel

interface ProfileCreateContract {
    data class State(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val isSaved: Boolean = false
    )

    sealed interface Event {
        class SaveClicked(val contact: ProfileUiModel) : Event
        object ErrorMessage : Event
    }
}