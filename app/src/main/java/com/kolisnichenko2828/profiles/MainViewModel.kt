package com.kolisnichenko2828.profiles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.profiles.domain.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    val repository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainContract.State())
    val uiState = _uiState
        .onStart {
            setEvent(MainContract.Event.InitialLoad())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = MainContract.State()
        )

    fun setEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.InitialLoad -> { getOwnDetails() }
        }
    }

    private fun getOwnDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val profile = repository.getProfile()
            profile.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isProfileExist = true
                        )
                    }
                },
                onFailure = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isProfileExist = false
                        )
                    }
                }
            )
        }
    }
}