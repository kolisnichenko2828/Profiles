package com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details

sealed interface ContactDetailsContract {
    data class State(
        val contact: ContactUiModel? = null,
        val errorMessage: String? = null
    )

    sealed interface Event {
        class InitialLoad(val id: String) : Event
    }
}