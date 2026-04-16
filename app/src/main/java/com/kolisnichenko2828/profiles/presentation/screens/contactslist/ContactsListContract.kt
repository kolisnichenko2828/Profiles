package com.kolisnichenko2828.profiles.presentation.screens.contactslist

interface ContactsListContract {
    data class State(
        val contacts: List<ContactUiModel> = emptyList(),
        val error: String? = null,
        val isLoadingInitial: Boolean = false,
        val isLoadingNext: Boolean = false
    )

    sealed interface Event {
        object InitialLoad : Event
        object LoadNext : Event
        class OnItemVisible(val index: Int) : Event
    }
}