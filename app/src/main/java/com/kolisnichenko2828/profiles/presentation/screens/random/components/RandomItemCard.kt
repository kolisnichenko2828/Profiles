package com.kolisnichenko2828.profiles.presentation.screens.random.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kolisnichenko2828.profiles.core.ContactCategory
import com.kolisnichenko2828.profiles.presentation.screens.random.RandomUiModel
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme

@Composable
fun RandomItemCard(
    contact: RandomUiModel,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(contact.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = contact.lastName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun RandomItemCardPreview() {
    ProfilesTheme {
        RandomItemCard(
            contact = RandomUiModel(
                id = "1",
                imageUri = null,
                firstName = "Name1",
                lastName = "Surname1",
                phone = "+380931234567",
                email = "example1@gmail.com",
                dateOfBirth = "1991-01-01",
                category = ContactCategory.WORK
            ),
            onClick = {}
        )
    }
}