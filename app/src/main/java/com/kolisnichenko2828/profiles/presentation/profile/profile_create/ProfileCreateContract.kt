package com.kolisnichenko2828.profiles.presentation.profile.profile_create

interface ProfileCreateContract {
    data class State(
        val firstName: String = "",
        val lastName: String = "",
        val phone: String = "",
        val email: String = "",
        val dateOfBirth: String = "",

        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    sealed interface Event {
        class FirstNameChanged(val value: String) : Event
        class LastNameChanged(val value: String) : Event
        class PhoneChanged(val value: String) : Event
        class EmailChanged(val value: String) : Event
        class DateOfBirthChanged(val value: String) : Event

        object SaveClicked : Event
        object ErrorMessage : Event
    }

    sealed interface Effect {
        object NavigateToProfile : Effect
    }
}