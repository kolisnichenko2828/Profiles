package com.kolisnichenko2828.profiles.presentation.screens.profile.profile_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.profiles.domain.ProfileDomainModel
import com.kolisnichenko2828.profiles.domain.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileCreateViewModel(
    val repository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileCreateContract.State())
    val uiState = _uiState.asStateFlow()
    private val _effect = Channel<ProfileCreateContract.Effect>()
    val effect = _effect.receiveAsFlow()

    fun setEvent(event: ProfileCreateContract.Event) {
        when (event) {
            is ProfileCreateContract.Event.FirstNameChanged -> {
                _uiState.update { it.copy(firstName = event.value.trim()) }
            }
            is ProfileCreateContract.Event.LastNameChanged -> {
                _uiState.update { it.copy(lastName = event.value.trim()) }
            }
            is ProfileCreateContract.Event.PhoneChanged -> {
                _uiState.update { it.copy(phone = event.value.trim()) }
            }
            is ProfileCreateContract.Event.EmailChanged -> {
                _uiState.update { it.copy(email = event.value.trim()) }
            }
            is ProfileCreateContract.Event.DateOfBirthChanged -> {
                _uiState.update { it.copy(dateOfBirth = event.value.trim()) }
            }

            is ProfileCreateContract.Event.SaveClicked -> saveContact()
            is ProfileCreateContract.Event.ErrorMessage -> _uiState.update { it.copy(errorMessage = null) }
        }
    }

    private fun saveContact() {
        val currentState = _uiState.value

        if (currentState.firstName.isBlank() || currentState.lastName.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

            val result = repository.saveProfile(
                profile = ProfileDomainModel(
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

                    _effect.send(ProfileCreateContract.Effect.NavigateToProfile)
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