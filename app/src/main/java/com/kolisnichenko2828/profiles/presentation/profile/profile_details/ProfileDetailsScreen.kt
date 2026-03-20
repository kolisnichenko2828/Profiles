package com.kolisnichenko2828.profiles.presentation.profile.profile_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.profiles.presentation.profile.profile_details.components.ProfileDetailsContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileDetailsScreen(
    onNavigateBack: () -> Unit,
    onEditProfile: () -> Unit,
    viewModel: ProfileDetailsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    BackHandler {
        onNavigateBack()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        when {
            currentState.errorMessage != null-> {
                Text(
                    text = currentState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            currentState.profile != null -> {
                ProfileDetailsContent(
                    profile = currentState.profile
                )
            }
        }
    }
}