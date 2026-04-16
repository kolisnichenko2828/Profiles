package com.kolisnichenko2828.profiles.presentation.screens.addcontact.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.presentation.components.ContactItemCard
import com.kolisnichenko2828.profiles.presentation.screens.contactslist.ContactUiModel
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme

@Composable
fun AddContactContent(
    contacts: List<ContactUiModel>,
    onClick: (ContactUiModel) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = contacts,
            key = { _, contact -> contact.id }
        ) { _, contact ->
            ContactItemCard(
                lastName = contact.lastName,
                onClick = { onClick(contact) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddContactContentPreview() {
    ProfilesTheme {
        AddContactContent(
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
            ),
            onClick = {}
        )
    }
}