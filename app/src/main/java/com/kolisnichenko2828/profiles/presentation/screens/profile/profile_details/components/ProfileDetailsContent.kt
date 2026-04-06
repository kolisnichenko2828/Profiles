package com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details.ProfileUiModel
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme

@Composable
fun ProfileDetailsContent(
    profile: ProfileUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.my_profile),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        AvatarSection(uri = profile.imageUri)

        Text(
            text = "${profile.firstName} ${profile.lastName}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                InfoSection(
                    icon = painterResource(R.drawable.phone_enabled_24px),
                    label = stringResource(R.string.phone),
                    text = profile.phone
                )

                HorizontalDivider(modifier = Modifier.padding(start = 56.dp, end = 16.dp))

                InfoSection(
                    icon = painterResource(R.drawable.mail_24px),
                    label = stringResource(R.string.email),
                    text = profile.email
                )

                HorizontalDivider(modifier = Modifier.padding(start = 56.dp, end = 16.dp))

                InfoSection(
                    icon = painterResource(R.drawable.date_range_24px),
                    label = stringResource(R.string.date_of_birth),
                    text = profile.dateOfBirth
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileDetailsContentPreview() {
    ProfilesTheme {
        ProfileDetailsContent(
            profile = ProfileUiModel(
                firstName = "Коля",
                lastName = "Николаенко",
                phone = "+380931234567"
            )
        )
    }
}