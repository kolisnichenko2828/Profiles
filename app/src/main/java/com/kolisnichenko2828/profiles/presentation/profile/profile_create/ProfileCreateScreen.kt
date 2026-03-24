package com.kolisnichenko2828.profiles.presentation.profile.profile_create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.presentation.profile.profile_create.components.CreateTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileCreateScreen(
    onNavigateToDetails: () -> Unit,
    viewModel: ProfileCreateViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ProfileCreateContract.Effect -> onNavigateToDetails()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.create_profile),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        CreateTextField(
            value = uiState.firstName,
            onValueChange = { viewModel.setEvent(ProfileCreateContract.Event.FirstNameChanged(it)) },
            label = stringResource(R.string.first_name),
            icon = painterResource(R.drawable.person_24px),
            capitalization = KeyboardCapitalization.Words,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        CreateTextField(
            value = uiState.lastName,
            onValueChange = { viewModel.setEvent(ProfileCreateContract.Event.LastNameChanged(it)) },
            label = stringResource(R.string.last_name),
            icon = painterResource(R.drawable.person_24px),
            capitalization = KeyboardCapitalization.Words,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        CreateTextField(
            value = uiState.phone,
            onValueChange = { viewModel.setEvent(ProfileCreateContract.Event.PhoneChanged(it)) },
            label = stringResource(R.string.phone),
            icon = painterResource(R.drawable.phone_enabled_24px),
            keyboardType = KeyboardType.Phone,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        CreateTextField(
            value = uiState.email,
            onValueChange = { viewModel.setEvent(ProfileCreateContract.Event.EmailChanged(it)) },
            label = stringResource(R.string.email),
            icon = painterResource(R.drawable.mail_24px),
            keyboardType = KeyboardType.Email,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        CreateTextField(
            value = uiState.dateOfBirth,
            onValueChange = { viewModel.setEvent(ProfileCreateContract.Event.DateOfBirthChanged(it)) },
            label = stringResource(R.string.date_of_birth),
            icon = painterResource(R.drawable.date_range_24px),
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
            onDone = { focusManager.clearFocus() }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                focusManager.clearFocus()
                viewModel.setEvent(ProfileCreateContract.Event.SaveClicked)
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            enabled = !uiState.isLoading && !uiState.firstName.isBlank() && !uiState.lastName.isBlank(),
            shape = MaterialTheme.shapes.large
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 3.dp
                )
            } else {
                Text(
                    text = stringResource(R.string.save),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}