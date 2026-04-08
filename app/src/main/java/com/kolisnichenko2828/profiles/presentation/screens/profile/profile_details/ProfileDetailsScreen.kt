package com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.presentation.components.ErrorMessage
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details.components.ProfileDetailsContent
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileDetailsScreen(
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileDetailsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setEvent(ProfileDetailsContract.Event.InitialLoad)
    }

    ProfileDetailsScreenStateless(
        uiState = uiState,
        onEvent = { viewModel.setEvent(it) },
        onEditClick = onEditClick,
        modifier = modifier
    )
}

@Composable
fun ProfileDetailsScreenStateless(
    uiState: ProfileDetailsContract.State,
    onEvent: (ProfileDetailsContract.Event) -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.errorMessage != null -> {
                    ErrorMessage(
                        errorMessage = uiState.errorMessage,
                        onRetry = { onEvent(ProfileDetailsContract.Event.InitialLoad) }
                    )
                }
                uiState.profile != null -> {
                    ProfileDetailsContent(
                        profile = uiState.profile
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

@Preview
@Composable
private fun ProfileDetailsScreenErrorPreview() {
    ProfilesTheme {
        ProfileDetailsScreenStateless(
            uiState = ProfileDetailsContract.State(
                profile = null,
                errorMessage = "No internet"
            ),
            onEvent = {},
            onEditClick = {}
        )
    }
}

@Preview
@Composable
private fun ProfileDetailsScreenContentPreview() {
    ProfilesTheme {
        ProfileDetailsScreenStateless(
            uiState = ProfileDetailsContract.State(
                profile = ProfileUiModel(
                    firstName = "Name",
                    lastName = "Surname",
                    phone = "+380931234567"
                ),
                errorMessage = null
            ),
            onEvent = {},
            onEditClick = {}
        )
    }
}