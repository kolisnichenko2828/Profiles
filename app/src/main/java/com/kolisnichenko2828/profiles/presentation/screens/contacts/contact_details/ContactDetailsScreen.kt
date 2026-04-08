package com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.presentation.components.ErrorMessage
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details.components.ContactDetailsContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactDetailsScreen(
    id: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContactDetailsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setEvent(ContactDetailsContract.Event.InitialLoad(id))
    }

    ContactDetailsScreenStateless(
        id = id,
        uiState = uiState,
        onEvent = { viewModel.setEvent(it) },
        onEditClick = onEditClick,
        modifier = modifier
    )
}

@Composable
fun ContactDetailsScreenStateless(
    id: String,
    uiState: ContactDetailsContract.State,
    onEvent: (ContactDetailsContract.Event) -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.errorMessage != null -> {
                    ErrorMessage(
                        errorMessage = uiState.errorMessage,
                        onRetry = { onEvent(ContactDetailsContract.Event.InitialLoad(id)) }
                    )
                }
                uiState.contact != null -> {
                    ContactDetailsContent(
                        contact = uiState.contact
                    )
                }
            }

            FloatingActionButton(
                onClick = onEditClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.edit_24px),
                    contentDescription = stringResource(R.string.edit_profile)
                )
            }
        }
    }
}