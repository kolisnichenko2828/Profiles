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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.presentation.screens.components.ErrorMessage
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details.components.ProfileDetailsContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileDetailsScreen(
    onEditClick: () -> Unit,
    viewModel: ProfileDetailsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    LaunchedEffect(Unit) {
        viewModel.setEvent(ProfileDetailsContract.Event.InitialLoad)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                currentState.errorMessage != null -> {
                    ErrorMessage(
                        errorMessage = currentState.errorMessage,
                        onRetry = { viewModel.setEvent(ProfileDetailsContract.Event.InitialLoad) }
                    )
                }
                currentState.profile != null -> {
                    ProfileDetailsContent(
                        profile = currentState.profile
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