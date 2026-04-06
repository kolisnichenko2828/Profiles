package com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme

@Composable
fun InfoSection(
    icon: Painter,
    label: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = text.ifBlank { stringResource(R.string.not_specified) },
                style = MaterialTheme.typography.bodyLarge,
                color = if (text.isNotBlank()) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoSectionBlankPreview() {
    ProfilesTheme {
        InfoSection(
            icon = painterResource(R.drawable.phone_enabled_24px),
            label = "Phone",
            text = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoSectionPreview() {
    ProfilesTheme {
        InfoSection(
            icon = painterResource(R.drawable.phone_enabled_24px),
            label = "Phone",
            text = "+380931234567"
        )
    }
}