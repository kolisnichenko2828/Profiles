package com.kolisnichenko2828.profiles.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme

@Composable
fun ContactItemCard(
    lastName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            text = lastName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun ContactItemCardPreview() {
    ProfilesTheme {
        ContactItemCard(
            lastName = "Surname"
        )
    }
}