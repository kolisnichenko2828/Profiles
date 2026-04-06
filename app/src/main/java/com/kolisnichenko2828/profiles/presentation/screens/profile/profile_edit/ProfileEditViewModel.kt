package com.kolisnichenko2828.profiles.presentation.screens.profile.profile_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.profiles.domain.models.ProfileDomainModel
import com.kolisnichenko2828.profiles.domain.interfaces.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileEditViewModel(
    val repository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileEditContract.State())
    val uiState = _uiState.asStateFlow()
        .onStart {
            setEvent(ProfileEditContract.Event.InitialLoad)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _uiState.value
        )
    private val _effect = Channel<ProfileEditContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun setEvent(event: ProfileEditContract.Event) {
        when (event) {
            is ProfileEditContract.Event.InitialLoad -> {
                loadProfile()
            }
            is ProfileEditContract.Event.ImageUriChanged -> {
                saveImage(event.value)
            }
            is ProfileEditContract.Event.FirstNameChanged -> {
                _uiState.update { it.copy(firstName = event.value.trim()) }
            }
            is ProfileEditContract.Event.LastNameChanged -> {
                _uiState.update { it.copy(lastName = event.value.trim()) }
            }
            is ProfileEditContract.Event.PhoneChanged -> {
                _uiState.update { it.copy(phone = event.value.trim()) }
            }
            is ProfileEditContract.Event.EmailChanged -> {
                _uiState.update { it.copy(email = event.value.trim()) }
            }
            is ProfileEditContract.Event.DateOfBirthChanged -> {
                _uiState.update { it.copy(dateOfBirth = event.value.trim()) }
            }

            is ProfileEditContract.Event.SaveClicked -> saveContact()
            is ProfileEditContract.Event.ErrorMessage -> _uiState.update { it.copy(errorMessage = null) }
        }
    }

    private fun saveImage(uri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.saveImage(uri)

            result.fold(
                onSuccess = { imageUri ->
                    _uiState.update { it.copy(imageUri = imageUri) }
                },
                onFailure = { exception ->
                    _uiState.update { it.copy(errorMessage = exception.localizedMessage) }
                }
            )
        }
    }

    private fun loadProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

            val result = repository.getProfile()

            result.fold(
                onSuccess = { profile ->
                    if (profile != null) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                imageUri = profile.imageUri,
                                firstName = profile.firstName,
                                lastName = profile.lastName,
                                phone = profile.phone,
                                email = profile.email,
                                dateOfBirth = profile.dateOfBirth
                            )
                        }
                    } else {
                        _uiState.update {
                            ProfileEditContract.State(isLoading = false)
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

        if (currentState.firstName.isBlank() || currentState.lastName.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

            val result = repository.saveProfile(
                profile = ProfileDomainModel(
                    imageUri = currentState.imageUri,
                    firstName = currentState.firstName,
                    lastName = currentState.lastName,
                    phone = currentState.phone,
                    email = currentState.email,
                    dateOfBirth = currentState.dateOfBirth
                )
            )
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null
                        )
                    }

                    _effect.send(ProfileEditContract.Effect.NavigateToProfile)
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