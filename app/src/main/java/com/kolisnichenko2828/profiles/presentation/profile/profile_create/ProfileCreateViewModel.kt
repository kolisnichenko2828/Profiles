package com.kolisnichenko2828.profiles.presentation.profile.profile_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.profiles.data.repository.ProfileRepository
import com.kolisnichenko2828.profiles.presentation.models.ProfileUiModel
import com.kolisnichenko2828.profiles.presentation.models.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileCreateViewModel(
    val repository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileCreateContract.State())
    val uiState = _uiState.asStateFlow()

    fun setEvent(event: ProfileCreateContract.Event) {
        when (event) {
            is ProfileCreateContract.Event.SaveClicked -> saveContact(event.contact)
            is ProfileCreateContract.Event.ErrorMessage -> _uiState.update { it.copy(errorMessage = null) }
        }
    }

    private fun saveContact(contact: ProfileUiModel) {
        if (contact.firstName.isBlank() || contact.lastName.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

            val result = repository.saveProfile(contact.toDomain())
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isSaved = true,
                            isLoading = false,
                            errorMessage = null
                        )
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
}