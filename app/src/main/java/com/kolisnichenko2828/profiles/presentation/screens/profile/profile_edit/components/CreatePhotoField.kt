package com.kolisnichenko2828.profiles.presentation.screens.profile.profile_edit.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_edit.ProfileEditContract

@Composable
fun CreatePhotoField(
    uiState: ProfileEditContract.State,
    onEvent: (ProfileEditContract.Event) -> Unit
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                onEvent(ProfileEditContract.Event.ImageUriChanged(uri.toString()))
            }
        }
    )

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (uiState.imageUri.isNullOrEmpty()) {
                Icon(
                    painter = painterResource(R.drawable.person_24px),
                    contentDescription = stringResource(R.string.profile_image),
                    modifier = Modifier.size(48.dp)
                )
            } else {
                AsyncImage(
                    model = uiState.imageUri,
                    contentDescription = stringResource(R.string.profile_image),
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}