package com.kolisnichenko2828.profiles.presentation.screens.random

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.presentation.components.ErrorMessage
import com.kolisnichenko2828.profiles.presentation.screens.random.components.RandomContent
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RandomScreen(
    onContactClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RandomViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RandomScreenStateless(
        uiState = uiState,
        onContactClick = onContactClick,
        onRetry = { viewModel.setEvent(RandomContract.Event.InitialLoad) },
        modifier = modifier
    )
}

@Composable
fun RandomScreenStateless(
    uiState: RandomContract.State,
    onContactClick: (id: String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.error != null && uiState.contacts.isEmpty() -> {
                    ErrorMessage(
                        errorMessage = uiState.error,
                        onRetry = onRetry
                    )
                }
                uiState.contacts.isNotEmpty() -> {
                    RandomContent(
                        contacts = uiState.contacts,
                        onContactClick = onContactClick
                    )
                }
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.nothing_found),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun RandomScreenLoadingPreview() {
    ProfilesTheme {
        RandomScreenStateless(
            uiState = RandomContract.State(isLoading = true),
            onContactClick = {},
            onRetry = {}
        )
    }
}

@Preview
@Composable
private fun RandomScreenErrorPreview() {
    ProfilesTheme {
        RandomScreenStateless(
            uiState = RandomContract.State(
                isLoading = false,
                error = "No internet"
            ),
            onContactClick = {},
            onRetry = {}
        )
    }
}

@Preview
@Composable
private fun RandomScreenContentPreview() {
    ProfilesTheme {
        RandomScreenStateless(
            uiState = RandomContract.State(
                isLoading = false,
                error = null,
                contacts = listOf(
                    RandomUiModel(
                        id = "1",
                        imageUri = null,
                        firstName = "Name1",
                        lastName = "Surname1",
                        phone = "+380931234567",
                        email = "example1@gmail.com",
                        dateOfBirth = "1991-01-01",
                        category = ContactCategory.WORK
                    ),
                    RandomUiModel(
                        id = "2",
                        imageUri = null,
                        firstName = "Name2",
                        lastName = "Surname2",
                        phone = "+380931234567",
                        email = "example2@gmail.com",
                        dateOfBirth = "1992-01-01",
                        category = ContactCategory.FAMILY
                    )
                )
            ),
            onContactClick = {},
            onRetry = {}
        )
    }
}