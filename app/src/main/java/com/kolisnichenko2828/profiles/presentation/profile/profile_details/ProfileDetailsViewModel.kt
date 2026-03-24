package com.kolisnichenko2828.profiles.presentation.profile.profile_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.profiles.domain.ProfileRepository
import com.kolisnichenko2828.profiles.domain.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileDetailsViewModel(
    val repository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileDetailsContract.State())
    val uiState = _uiState
        .onStart {
            setEvent(ProfileDetailsContract.Event.InitialLoad)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ProfileDetailsContract.State()
        )

    fun setEvent(event: ProfileDetailsContract.Event) {
        when (event) {
            is ProfileDetailsContract.Event.InitialLoad -> { getProfile() }
        }
    }

    private fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    errorMessage = null,
                    profile = null
                )
            }

            val profileModel = repository.getProfile()
            profileModel.fold(
                onSuccess = { profile ->
                    _uiState.update {
                        it.copy(
                            profile = profile.toUi()
                        )
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