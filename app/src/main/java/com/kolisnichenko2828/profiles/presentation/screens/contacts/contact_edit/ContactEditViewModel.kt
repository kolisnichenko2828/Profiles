package com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactSaver
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactsProvider
import com.kolisnichenko2828.profiles.domain.interfaces.profile.ImageSaver
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details.ContactUiModel
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details.toDomain
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactEditViewModel(
    val contactsProvider: ContactsProvider,
    val contactSaver: ContactSaver,
    val imageSaver: ImageSaver
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContactEditContract.State())
    val uiState = _uiState.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _uiState.value
        )
    private val _effect = Channel<ContactEditContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun setEvent(event: ContactEditContract.Event) {
        when (event) {
            is ContactEditContract.Event.InitialLoad -> {
                loadContact(event.id, event.isNew)
            }
            is ContactEditContract.Event.ImageUriChanged -> {
                saveImage(event.value)
            }
            is ContactEditContract.Event.FirstNameChanged -> {
                _uiState.update {
                    it.copy(
                        currentContact = it.currentContact.copy(
                            firstName = event.value.trim()
                        )
                    )
                }
            }
            is ContactEditContract.Event.LastNameChanged -> {
                _uiState.update {
                    it.copy(
                        currentContact = it.currentContact.copy(
                            lastName = event.value.trim()
                        )
                    )
                }
            }
            is ContactEditContract.Event.PhoneChanged -> {
                _uiState.update {
                    it.copy(
                        currentContact = it.currentContact.copy(
                            phone = event.value.trim()
                        )
                    )
                }
            }
            is ContactEditContract.Event.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        currentContact = it.currentContact.copy(
                            email = event.value.trim()
                        )
                    )
                }
            }
            is ContactEditContract.Event.DateOfBirthChanged -> {
                _uiState.update {
                    it.copy(
                        currentContact = it.currentContact.copy(
                            dateOfBirth = event.value.trim()
                        )
                    )
                }
            }
            is ContactEditContract.Event.CategoryChanged -> {
                _uiState.update {
                    it.copy(
                        currentContact = it.currentContact.copy(
                            category = event.value
                        )
                    )
                }
            }

            is ContactEditContract.Event.SaveClicked -> saveContact()
            is ContactEditContract.Event.ErrorMessage -> _uiState.update { it.copy(errorMessage = null) }
        }
    }

    private fun saveImage(uri: String) {
        viewModelScope.launch {
            val result = imageSaver.save(uri)

            result.fold(
                onSuccess = { imageUri ->
                    _uiState.update {
                        it.copy(
                            currentContact = it.currentContact.copy(
                                imageUri = imageUri
                            )
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.update { it.copy(errorMessage = exception.localizedMessage) }
                }
            )
        }
    }

    private fun loadContact(id: String, isNew: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = contactsProvider.getById(id)

            result.fold(
                onSuccess = { contact ->
                    if (contact != null) {
                        val loadedContact = ContactUiModel(
                            id = id,
                            imageUri = contact.imageUri,
                            firstName = contact.firstName,
                            lastName = contact.lastName,
                            phone = contact.phone,
                            email = contact.email,
                            dateOfBirth = contact.dateOfBirth,
                            category = contact.category
                        )

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                currentContact = loadedContact,
                                originalContact = if (isNew) ContactUiModel() else loadedContact
                            )
                        }
                    } else {
                        _uiState.update {
                            ContactEditContract.State(isLoading = false)
                        }
                    }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.localizedMessage
                        )
                    }
                }
            )
        }
    }

    private fun saveContact() {
        val currentState = _uiState.value

        if (!currentState.isSaveEnabled) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = contactSaver.save(
                contact = currentState.currentContact.toDomain()
            )

            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null
                        )
                    }

                    _effect.send(ContactEditContract.Effect.NavigateToDetails)
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.localizedMessage
                        )
                    }
                }
            )
        }
    }
}