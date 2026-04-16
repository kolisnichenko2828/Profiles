package com.kolisnichenko2828.profiles.presentation.screens.editprofile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.profiles.presentation.components.ErrorMessage
import com.kolisnichenko2828.profiles.presentation.screens.editprofile.components.ProfileEditContent
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileEditScreen(
    onNavigateToDetails: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileEditViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ProfileEditContract.Effect.NavigateToDetails -> onNavigateToDetails()
            }
        }
    }

    ProfileEditScreenStateless(
        uiState = uiState,
        onEvent = { viewModel.setEvent(it) },
        modifier = modifier
    )
}

@Composable
fun ProfileEditScreenStateless(
    uiState: ProfileEditContract.State,
    onEvent: (ProfileEditContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.errorMessage != null -> {
                ErrorMessage(
                    errorMessage = uiState.errorMessage,
                    onRetry = { onEvent(ProfileEditContract.Event.InitialLoad) }
                )
            }
            else -> {
                ProfileEditContent(
                    uiState = uiState,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileEditScreenLoadingPreview() {
    ProfilesTheme {
        ProfileEditScreenStateless(
            uiState = ProfileEditContract.State(isLoading = true),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun ProfileEditScreenErrorPreview() {
    ProfilesTheme {
        ProfileEditScreenStateless(
            uiState = ProfileEditContract.State(
                isLoading = false,
                errorMessage = "No internet"
            ),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun ProfileEditScreenContentPreview() {
    ProfilesTheme {
        ProfileEditScreenStateless(
            uiState = ProfileEditContract.State(
                isLoading = false,
                errorMessage = null,
                firstName = "Name",
                lastName = "Surname",
                phone = "+380931234567"
            ),
            onEvent = {}
        )
    }
}