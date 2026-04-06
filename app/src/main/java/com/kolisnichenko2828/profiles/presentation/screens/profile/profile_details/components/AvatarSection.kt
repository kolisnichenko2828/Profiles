package com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme

@Composable
fun AvatarSection(uri: String?) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            if (uri.isNullOrEmpty()) {
                Icon(
                    painter = painterResource(R.drawable.person_24px),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            } else {
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AvatarSectionPreview() {
    ProfilesTheme {
        AvatarSection(
            uri = null
        )
    }
}