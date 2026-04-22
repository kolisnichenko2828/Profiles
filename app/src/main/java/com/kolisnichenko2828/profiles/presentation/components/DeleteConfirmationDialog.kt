package com.kolisnichenko2828.profiles.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme

@Composable
fun DeleteConfirmationDialog(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.delete_contact),
    description: String = stringResource(R.string.are_you_sure),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(description) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Preview
@Composable
fun DeleteConfirmationDialogPreview() {
    ProfilesTheme {
        DeleteConfirmationDialog(
            onConfirm = {},
            onDismiss = {}
        )
    }
}