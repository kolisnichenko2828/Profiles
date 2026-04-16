package com.kolisnichenko2828.profiles.presentation.screens.contactslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kolisnichenko2828.profiles.domain.interfaces.contacts.ContactsProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactsListViewModel(
    private val contactsProvider: ContactsProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContactsListContract.State())
    val uiState = _uiState.asStateFlow()
        .onStart {
            setEvent(ContactsListContract.Event.InitialLoad)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _uiState.value
        )
    private val currentLimit = MutableStateFlow(30)

    fun setEvent(event: ContactsListContract.Event) {
        when (event) {
            is ContactsListContract.Event.InitialLoad -> observeContacts()
            is ContactsListContract.Event.OnItemVisible -> checkScrollPosition(event.index)
            is ContactsListContract.Event.LoadNext -> loadNextPage()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeContacts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingInitial = true) }

            currentLimit
                .flatMapLatest { limit ->
                    contactsProvider.getAll(limit)
                }
                .catch { exception ->
                    _uiState.update {
                        it.copy(
                            error = exception.localizedMessage,
                            isLoadingInitial = false,
                            isLoadingNext = false
                        )
                    }
                }
                .collect { contacts ->
                    _uiState.update {
                        it.copy(
                            contacts = contacts.toUi(),
                            error = null,
                            isLoadingInitial = false,
                            isLoadingNext = false
                        )
                    }
                }
        }
    }

    private fun checkScrollPosition(index: Int) {
        val threshold = uiState.value.contacts.size - 10
        if (index == threshold) loadNextPage()
    }

    private fun loadNextPage() {
        if (uiState.value.isLoadingNext) return

        _uiState.update { it.copy(isLoadingNext = true) }

        currentLimit.update { it + 30 }
    }
}