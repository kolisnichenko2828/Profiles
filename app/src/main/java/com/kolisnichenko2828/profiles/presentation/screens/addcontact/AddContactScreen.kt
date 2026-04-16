package com.kolisnichenko2828.profiles.presentation.screens.addcontact

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.kolisnichenko2828.profiles.presentation.screens.addcontact.components.AddContactContent
import com.kolisnichenko2828.profiles.presentation.screens.addcontact.components.CategorySelectionDialog
import com.kolisnichenko2828.profiles.presentation.screens.contactslist.ContactUiModel
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddContactScreen(
    onNavigateToContacts: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddContactViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AddContactContract.Effect.NavigateToContacts -> onNavigateToContacts()
            }
        }
    }

    AddContactScreenStateless(
        uiState = uiState,
        onClick = { viewModel.setEvent(AddContactContract.Event.ContactClicked(it)) },
        onRetry = { viewModel.setEvent(AddContactContract.Event.InitialLoad) },
        onDismissDialog = { viewModel.setEvent(AddContactContract.Event.DismissDialog) },
        onCategorySelected = { viewModel.setEvent(AddContactContract.Event.CategorySelected(it)) },
        modifier = modifier
    )
}

@Composable
fun AddContactScreenStateless(
    uiState: AddContactContract.State,
    onClick: (ContactUiModel) -> Unit,
    onRetry: () -> Unit,
    onDismissDialog: () -> Unit,
    onCategorySelected: (ContactCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.isLoading && uiState.contacts.isEmpty() -> {
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
                    AddContactContent(
                        contacts = uiState.contacts,
                        onClick = onClick
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

            if (uiState.targetContact != null) {
                CategorySelectionDialog(
                    onCategorySelected = onCategorySelected,
                    onDismissRequest = onDismissDialog
                )
            }
        }
    }
}

@Preview
@Composable
private fun AddContactScreenLoadingPreview() {
    ProfilesTheme {
        AddContactScreenStateless(
            uiState = AddContactContract.State(isLoading = true),
            onClick = {},
            onRetry = {},
            onDismissDialog = {},
            onCategorySelected = {}
        )
    }
}

@Preview
@Composable
private fun AddContactScreenErrorPreview() {
    ProfilesTheme {
        AddContactScreenStateless(
            uiState = AddContactContract.State(
                isLoading = false,
                error = "No internet"
            ),
            onClick = {},
            onRetry = {},
            onDismissDialog = {},
            onCategorySelected = {}
        )
    }
}

@Preview
@Composable
private fun AddContactScreenContentPreview() {
    ProfilesTheme {
        AddContactScreenStateless(
            uiState = AddContactContract.State(
                isLoading = false,
                error = null,
                contacts = listOf(
                    ContactUiModel(
                        id = "1",
                        imageUri = null,
                        firstName = "Name1",
                        lastName = "Surname1",
                        phone = "+380931234567",
                        email = "example1@gmail.com",
                        dateOfBirth = "1991-01-01",
                        category = ContactCategory.WORK
                    ),
                    ContactUiModel(
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
            onClick = {},
            onRetry = {},
            onDismissDialog = {},
            onCategorySelected = {}
        )
    }
}