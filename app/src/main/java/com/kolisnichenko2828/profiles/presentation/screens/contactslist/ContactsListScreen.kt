package com.kolisnichenko2828.profiles.presentation.screens.contactslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.presentation.components.ErrorMessage
import com.kolisnichenko2828.profiles.presentation.screens.contactslist.components.ContactsListContent
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactsListScreen(
    onAddClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContactsListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ContactsListScreenStateless(
        modifier = modifier,
        uiState = uiState,
        onAddClick = onAddClick,
        onProfileClick = onProfileClick,
        onRetry = { viewModel.setEvent(ContactsListContract.Event.InitialLoad) },
        onItemVisible = { id -> viewModel.setEvent(ContactsListContract.Event.OnItemVisible(id)) },
        onLoadNext = { viewModel.setEvent(ContactsListContract.Event.LoadNext) },
        onDeleteContact = { id -> viewModel.setEvent(ContactsListContract.Event.OnDeleteContact(id)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsListScreenStateless(
    uiState: ContactsListContract.State,
    onAddClick: () -> Unit,
    onProfileClick: () -> Unit,
    onRetry: () -> Unit,
    onItemVisible: (Int) -> Unit,
    onLoadNext: () -> Unit,
    onDeleteContact: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.contacts))
                },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            painter = painterResource(R.drawable.person_24px),
                            contentDescription = stringResource(R.string.my_profile)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_24px),
                    contentDescription = stringResource(R.string.add_contact)
                )
            }
        }
    ) { innerPadding ->
        when {
            uiState.isLoadingInitial -> {
                Box(
                    modifier = Modifier.padding(innerPadding).fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null && uiState.contacts.isEmpty() -> {
                ErrorMessage(
                    errorMessage = uiState.error,
                    onRetry = onRetry,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            uiState.contacts.isNotEmpty() -> {
                ContactsListContent(
                    contacts = uiState.contacts,
                    isError = uiState.error,
                    isLoadingNext = uiState.isLoadingNext,
                    onItemVisible = onItemVisible,
                    onLoadNext = onLoadNext,
                    onDeleteContact = onDeleteContact,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            else -> {
                Box(
                    modifier = Modifier.padding(innerPadding).fillMaxSize(),
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

@Preview
@Composable
private fun ContactsListScreenLoadingPreview() {
    ProfilesTheme {
        ContactsListScreenStateless(
            uiState = ContactsListContract.State(isLoadingInitial = true),
            onRetry = {},
            onAddClick = {},
            onProfileClick = {},
            onItemVisible = {},
            onLoadNext = {},
            onDeleteContact = {}
        )
    }
}

@Preview
@Composable
private fun ContactsListScreenErrorPreview() {
    ProfilesTheme {
        ContactsListScreenStateless(
            uiState = ContactsListContract.State(error = "No internet"),
            onRetry = {},
            onAddClick = {},
            onProfileClick = {},
            onItemVisible = {},
            onLoadNext = {},
            onDeleteContact = {}
        )
    }
}

@Preview
@Composable
private fun ContactsListScreenEmptyContentPreview() {
    ProfilesTheme {
        ContactsListScreenStateless(
            uiState = ContactsListContract.State(contacts = emptyList()),
            onRetry = {},
            onAddClick = {},
            onProfileClick = {},
            onItemVisible = {},
            onLoadNext = {},
            onDeleteContact = {}
        )
    }
}

@Preview
@Composable
private fun ContactsListScreenContentPreview() {
    ProfilesTheme {
        ContactsListScreenStateless(
            uiState = ContactsListContract.State(
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
            onRetry = {},
            onAddClick = {},
            onProfileClick = {},
            onItemVisible = {},
            onLoadNext = {},
            onDeleteContact = {}
        )
    }
}