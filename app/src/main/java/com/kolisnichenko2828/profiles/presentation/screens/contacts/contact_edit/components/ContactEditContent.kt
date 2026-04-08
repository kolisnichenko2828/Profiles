package com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_edit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details.ContactUiModel
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_edit.ContactEditContract
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_edit.components.EditPhotoField
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_edit.components.EditTextField
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme

@Composable
fun ContactEditContent(
    uiState: ContactEditContract.State,
    onEvent: (ContactEditContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.edit_contact),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )

        EditPhotoField(
            imageUri = uiState.currentContact.imageUri,
            onImageSelected = { newUri ->
                onEvent(ContactEditContract.Event.ImageUriChanged(newUri))
            }
        )

        EditTextField(
            value = uiState.currentContact.firstName,
            onValueChange = { onEvent(ContactEditContract.Event.FirstNameChanged(it)) },
            label = stringResource(R.string.first_name),
            icon = painterResource(R.drawable.person_24px),
            capitalization = KeyboardCapitalization.Words,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        EditTextField(
            value = uiState.currentContact.lastName,
            onValueChange = { onEvent(ContactEditContract.Event.LastNameChanged(it)) },
            label = stringResource(R.string.last_name),
            icon = painterResource(R.drawable.person_24px),
            capitalization = KeyboardCapitalization.Words,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        EditTextField(
            value = uiState.currentContact.phone,
            onValueChange = { onEvent(ContactEditContract.Event.PhoneChanged(it)) },
            label = stringResource(R.string.phone),
            icon = painterResource(R.drawable.phone_enabled_24px),
            keyboardType = KeyboardType.Phone,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        EditTextField(
            value = uiState.currentContact.email,
            onValueChange = { onEvent(ContactEditContract.Event.EmailChanged(it)) },
            label = stringResource(R.string.email),
            icon = painterResource(R.drawable.mail_24px),
            keyboardType = KeyboardType.Email,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )

        EditTextField(
            value = uiState.currentContact.dateOfBirth,
            onValueChange = { onEvent(ContactEditContract.Event.DateOfBirthChanged(it)) },
            label = stringResource(R.string.date_of_birth),
            icon = painterResource(R.drawable.date_range_24px),
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
            onDone = { focusManager.clearFocus() }
        )

        EditCategoryDropdown(
            selectedCategory = uiState.currentContact.category,
            onCategorySelected = { onEvent(ContactEditContract.Event.CategoryChanged(it)) }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                focusManager.clearFocus()
                onEvent(ContactEditContract.Event.SaveClicked)
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            enabled = uiState.isSaveEnabled,
            shape = MaterialTheme.shapes.large
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
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

@Preview(showBackground = true)
@Composable
private fun ContactEditContentPreview() {
    ProfilesTheme {
        ContactEditContent(
            uiState = ContactEditContract.State(
                isLoading = false,
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
            ),
            onEvent = {}
        )
    }
}