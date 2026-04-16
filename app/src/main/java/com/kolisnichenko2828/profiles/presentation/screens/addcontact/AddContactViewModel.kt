package com.kolisnichenko2828.profiles.presentation.screens.addcontact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactSaver
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactsFetcher
import com.kolisnichenko2828.profiles.presentation.screens.contactslist.toDomain
import com.kolisnichenko2828.profiles.presentation.screens.contactslist.toUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddContactViewModel(
    private val contactsFetcher: ContactsFetcher,
    private val contactSaver: ContactSaver
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddContactContract.State())
    val uiState = _uiState.asStateFlow()
        .onStart {
            setEvent(AddContactContract.Event.InitialLoad)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _uiState.value
        )

    private val _effect = Channel<AddContactContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun setEvent(event: AddContactContract.Event) {
        when (event) {
            is AddContactContract.Event.InitialLoad -> loadContacts()
            is AddContactContract.Event.ContactClicked -> {
                _uiState.update { it.copy(targetContact = event.contact) }
            }
            is AddContactContract.Event.DismissDialog -> {
                _uiState.update { it.copy(targetContact = null) }
            }
            is AddContactContract.Event.CategorySelected -> {
                saveContact(event.category)
            }
        }
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = contactsFetcher.fetch()

            result.fold(
                onSuccess = { contacts ->
                    _uiState.update {
                        it.copy(
                            contacts = contacts.toUi(),
                            isLoading = false
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            error = exception.localizedMessage,
                            isLoading = false
                        )
                    }
                }
            )
        }
    }

    private fun saveContact(category: ContactCategory) {
        val currentContact = _uiState.value.targetContact ?: return

        viewModelScope.launch {
            _uiState.update {
                it.copy(targetContact = null, isLoading = true)
            }

            val domainContact = currentContact.toDomain().copy(category = category)
            val saveResult = contactSaver.save(domainContact)

            saveResult.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false) }
                    _effect.send(AddContactContract.Effect.NavigateToContacts)
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.localizedMessage
                        )
                    }
                }
            )
        }
    }
}