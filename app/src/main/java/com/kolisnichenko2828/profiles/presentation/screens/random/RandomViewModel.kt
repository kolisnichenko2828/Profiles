package com.kolisnichenko2828.profiles.presentation.screens.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactsFetcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RandomViewModel(
    private val contactsFetcher: ContactsFetcher
) : ViewModel() {
    private val _uiState = MutableStateFlow(RandomContract.State())
    val uiState = _uiState.asStateFlow()
        .onStart {
            setEvent(RandomContract.Event.InitialLoad)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _uiState.value
        )

    fun setEvent(event: RandomContract.Event) {
        when (event) {
            is RandomContract.Event.InitialLoad -> loadContacts()
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
}