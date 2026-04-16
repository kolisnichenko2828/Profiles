package com.kolisnichenko2828.profiles.presentation.screens.addcontact

import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.presentation.screens.contactslist.ContactUiModel

sealed interface AddContactContract {
    data class State(
        val contacts: List<ContactUiModel> = emptyList(),
        val error: String? = null,
        val isLoading: Boolean = false,
        val targetContact: ContactUiModel? = null
    )

    sealed interface Event {
        object InitialLoad : Event
        class ContactClicked(val contact: ContactUiModel) : Event
        object DismissDialog : Event
        class CategorySelected(val category: ContactCategory) : Event
    }

    sealed interface Effect {
        object NavigateToContacts : Effect
    }
}