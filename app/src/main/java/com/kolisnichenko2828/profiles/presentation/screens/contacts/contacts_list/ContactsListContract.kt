package com.kolisnichenko2828.profiles.presentation.screens.contacts.contacts_list

interface ContactsListContract {
    data class State(
        val contacts: List<ContactsListUiModel> = emptyList(),
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