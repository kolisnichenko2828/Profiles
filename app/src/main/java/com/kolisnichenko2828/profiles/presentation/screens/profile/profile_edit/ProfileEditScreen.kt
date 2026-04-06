package com.kolisnichenko2828.profiles.presentation.screens.profile.profile_edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.profiles.presentation.screens.components.ErrorMessage
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_edit.components.ProfileEditContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileEditScreen(
    onNavigateToDetails: () -> Unit,
    viewModel: ProfileEditViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ProfileEditContract.Effect.NavigateToProfile -> onNavigateToDetails()
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        when {
            currentState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            currentState.errorMessage != null -> {
                ErrorMessage(
                    errorMessage = currentState.errorMessage,
                    onRetry = { viewModel.setEvent(ProfileEditContract.Event.InitialLoad) }
                )
            }
            else -> {
                ProfileEditContent(
                    uiState = uiState,
                    onEvent = { viewModel.setEvent(it) }
                )
            }
        }
    }
}