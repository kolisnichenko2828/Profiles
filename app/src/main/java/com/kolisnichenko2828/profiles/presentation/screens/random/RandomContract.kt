package com.kolisnichenko2828.profiles.presentation.screens.random

sealed interface RandomContract {
    data class State(
        val contacts: List<RandomUiModel> = emptyList(),
        val error: String? = null,
        val isLoading: Boolean = false,
    )

    sealed interface Event {
        object InitialLoad : Event
    }
}