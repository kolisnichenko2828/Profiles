package com.kolisnichenko2828.profiles.presentation.screens.contacts.contacts_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contacts_list.ContactsListUiModel
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme

@Composable
fun ContactsListContent(
    contacts: List<ContactsListUiModel>,
    isLoadingNext: Boolean,
    isError: String?,
    onItemVisible: (Int) -> Unit,
    onLoadNext: () -> Unit,
    onContactClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = contacts,
            key = { _, contact -> contact.id }
        ) { index, contact ->
            ContactItemCard(
                contact = contact,
                onClick = onContactClick
            )

            LaunchedEffect(index) {
                onItemVisible(index)
            }
        }

        when {
            isLoadingNext -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            isError != null -> {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = isError,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { onLoadNext() }
                        ) {
                            Text(stringResource(R.string.retry))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactsListContentPreview() {
    ProfilesTheme {
        ContactsListContent(
            contacts = listOf(
                ContactsListUiModel(
                    id = "1",
                    imageUri = null,
                    firstName = "Name1",
                    lastName = "Surname1",
                    phone = "+380931234567",
                    email = "example1@gmail.com",
                    dateOfBirth = "1991-01-01",
                    category = ContactCategory.WORK
                ),
                ContactsListUiModel(
                    id = "2",
                    imageUri = null,
                    firstName = "Name2",
                    lastName = "Surname2",
                    phone = "+380931234567",
                    email = "example2@gmail.com",
                    dateOfBirth = "1992-01-01",
                    category = ContactCategory.FAMILY
                )
            ),
            onContactClick = {},
            isLoadingNext = false,
            isError = null,
            onItemVisible = {},
            onLoadNext = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactsListContentNextPageLoadingPreview() {
    ProfilesTheme {
        ContactsListContent(
            contacts = listOf(
                ContactsListUiModel(
                    id = "1",
                    imageUri = null,
                    firstName = "Name1",
                    lastName = "Surname1",
                    phone = "+380931234567",
                    email = "example1@gmail.com",
                    dateOfBirth = "1991-01-01",
                    category = ContactCategory.WORK
                ),
                ContactsListUiModel(
                    id = "2",
                    imageUri = null,
                    firstName = "Name2",
                    lastName = "Surname2",
                    phone = "+380931234567",
                    email = "example2@gmail.com",
                    dateOfBirth = "1992-01-01",
                    category = ContactCategory.FAMILY
                )
            ),
            onContactClick = {},
            isLoadingNext = true,
            isError = null,
            onItemVisible = {},
            onLoadNext = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactsListContentNextPageErrorPreview() {
    ProfilesTheme {
        ContactsListContent(
            contacts = listOf(
                ContactsListUiModel(
                    id = "1",
                    imageUri = null,
                    firstName = "Name1",
                    lastName = "Surname1",
                    phone = "+380931234567",
                    email = "example1@gmail.com",
                    dateOfBirth = "1991-01-01",
                    category = ContactCategory.WORK
                ),
                ContactsListUiModel(
                    id = "2",
                    imageUri = null,
                    firstName = "Name2",
                    lastName = "Surname2",
                    phone = "+380931234567",
                    email = "example2@gmail.com",
                    dateOfBirth = "1992-01-01",
                    category = ContactCategory.FAMILY
                )
            ),
            onContactClick = {},
            isLoadingNext = false,
            isError = "No internet",
            onItemVisible = {},
            onLoadNext = {}
        )
    }
}