package com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_edit

import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details.ContactUiModel

sealed interface ContactEditContract {
    data class State(
        val originalContact: ContactUiModel = ContactUiModel(),
        val currentContact: ContactUiModel = ContactUiModel(),
        val isLoading: Boolean = true,
        val errorMessage: String? = null
    ) {
        val isSaveEnabled: Boolean
            get() {
                return !isLoading &&
                        currentContact.firstName.isNotBlank() &&
                        currentContact.lastName.isNotBlank() &&
                        originalContact != currentContact
            }
    }

    sealed interface Event {
        class InitialLoad(val id: String, val isNew: Boolean) : Event
        class ImageUriChanged(val value: String) : Event
        class FirstNameChanged(val value: String) : Event
        class LastNameChanged(val value: String) : Event
        class PhoneChanged(val value: String) : Event
        class EmailChanged(val value: String) : Event
        class DateOfBirthChanged(val value: String) : Event
        class CategoryChanged(val value: ContactCategory) : Event

        object SaveClicked : Event
        object ErrorMessage : Event
    }

    sealed interface Effect {
        object NavigateToDetails : Effect
    }
}