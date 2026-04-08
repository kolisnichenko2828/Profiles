package com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactsProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactDetailsViewModel(
    private val contactsProvider: ContactsProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContactDetailsContract.State())
    val uiState = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ContactDetailsContract.State()
        )

    fun setEvent(event: ContactDetailsContract.Event) {
        when (event) {
            is ContactDetailsContract.Event.InitialLoad -> { loadContact(event.id) }
        }
    }

    private fun loadContact(id: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    errorMessage = null,
                    contact = null
                )
            }

            val result = contactsProvider.getById(id)

            result.fold(
                onSuccess = { contact ->
                    if (contact != null) {
                        _uiState.update {
                            it.copy(
                                contact = contact.toUi()
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                contact = ContactUiModel()
                            )
                        }
                    }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            errorMessage = exception.localizedMessage
                        )
                    }
                }
            )
        }
    }
}