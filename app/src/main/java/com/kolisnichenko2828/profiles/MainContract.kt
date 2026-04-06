package com.kolisnichenko2828.profiles

interface MainContract {
    data class State(
        val isProfileExist: Boolean = false
    )

    sealed interface Event {
        class InitialLoad : Event
    }
}