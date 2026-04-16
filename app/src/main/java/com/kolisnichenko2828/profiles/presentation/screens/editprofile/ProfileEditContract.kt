package com.kolisnichenko2828.profiles.presentation.screens.editprofile

interface ProfileEditContract {
    data class State(
        val imageUri: String? = null,
        val firstName: String = "",
        val lastName: String = "",
        val phone: String = "",
        val email: String = "",
        val dateOfBirth: String = "",

        val isLoading: Boolean = true,
        val errorMessage: String? = null
    )

    sealed interface Event {
        object InitialLoad : Event
        class ImageUriChanged(val value: String) : Event
        class FirstNameChanged(val value: String) : Event
        class LastNameChanged(val value: String) : Event
        class PhoneChanged(val value: String) : Event
        class EmailChanged(val value: String) : Event
        class DateOfBirthChanged(val value: String) : Event

        object SaveClicked : Event
        object ErrorMessage : Event
    }

    sealed interface Effect {
        object NavigateToDetails : Effect
    }
}