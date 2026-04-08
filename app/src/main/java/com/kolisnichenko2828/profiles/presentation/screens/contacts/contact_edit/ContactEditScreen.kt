package com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_edit

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
import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.presentation.components.ErrorMessage
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details.ContactUiModel
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_edit.components.ContactEditContent
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactEditScreen(
    id: String,
    isNew: Boolean,
    onNavigateToContacts: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContactEditViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(id, isNew) {
        viewModel.setEvent(ContactEditContract.Event.InitialLoad(id, isNew))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ContactEditContract.Effect.NavigateToDetails -> onNavigateToContacts()
            }
        }
    }

    ContactEditScreenStateless(
        id = id,
        isNew = isNew,
        uiState = uiState,
        onEvent = { viewModel.setEvent(it) },
        modifier = modifier
    )
}

@Composable
fun ContactEditScreenStateless(
    id: String,
    isNew: Boolean,
    uiState: ContactEditContract.State,
    onEvent: (ContactEditContract.Event) -> Unit,
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
                    onRetry = { onEvent(ContactEditContract.Event.InitialLoad(id, isNew)) }
                )
            }
            else -> {
                ContactEditContent(
                    uiState = uiState,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Preview
@Composable
private fun ContactEditScreenLoadingPreview() {
    ProfilesTheme {
        ContactEditScreenStateless(
            id = "1",
            isNew = false,
            uiState = ContactEditContract.State(isLoading = true),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun ContactEditScreenErrorPreview() {
    ProfilesTheme {
        ContactEditScreenStateless(
            id = "1",
            isNew = false,
            uiState = ContactEditContract.State(
                isLoading = false,
                errorMessage = "No internet"
            ),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun ContactEditScreenContentPreview() {
    ProfilesTheme {
        ContactEditScreenStateless(
            id = "1",
            isNew = false,
            uiState = ContactEditContract.State(
                currentContact = ContactUiModel(
                    id = "1",
                    imageUri = null,
                    firstName = "Name1",
                    lastName = "Surname1",
                    phone = "+380931234567",
                    email = "example1@gmail.com",
                    dateOfBirth = "1991-01-01",
                    category = ContactCategory.WORK
                ),
                isLoading = false,
                errorMessage = null,
            ),
            onEvent = {}
        )
    }
}