package com.kolisnichenko2828.profiles.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kolisnichenko2828.profiles.R
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class DragAnchors { Start, End }

@Composable
fun DraggableContactItem(
    id: String,
    onDeleteConfirm: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val density = LocalDensity.current
    val actionSize = 80.dp
    val actionSizePx = with(density) { actionSize.toPx() }

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragAnchors.Start,
            anchors = DraggableAnchors {
                DragAnchors.Start at 0f
                DragAnchors.End at -actionSizePx
            }
        )
    }

    if (showDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                showDialog = false
                onDeleteConfirm(id)
            },
            onDismiss = {
                showDialog = false
                coroutineScope.launch { state.animateTo(DragAnchors.Start) }
            }
        )
    }

    DraggableContactItemStateless(
        modifier = modifier,
        state = state,
        onDeleteClick = { showDialog = true },
        actionSize = actionSize,
        content = content
    )
}

@Composable
fun DraggableContactItemStateless(
    state: AnchoredDraggableState<DragAnchors>,
    onDeleteClick: () -> Unit,
    actionSize: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(CardDefaults.shape)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(MaterialTheme.colorScheme.errorContainer)
                .clickable { onDeleteClick() },
            contentAlignment = Alignment.CenterEnd
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.width(actionSize)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.warning_24px),
                    contentDescription = stringResource(R.string.delete),
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
                Text(
                    text = stringResource(R.string.delete),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(x = state.requireOffset().roundToInt(), y = 0) }
                .anchoredDraggable(
                    state = state,
                    orientation = Orientation.Horizontal
                )
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun DraggableContactItemPreview() {
    ProfilesTheme {
        val density = LocalDensity.current
        val actionSize = 80.dp
        val actionSizePx = with(density) { actionSize.toPx() }

        val state = remember {
            AnchoredDraggableState(
                initialValue = DragAnchors.Start,
                anchors = DraggableAnchors {
                    DragAnchors.Start at 0f
                    DragAnchors.End at -actionSizePx
                }
            )
        }

        DraggableContactItemStateless(
            state = state,
            actionSize = actionSize,
            onDeleteClick = {},
            content = { ContactItemCard("Name Surname") }
        )
    }
}

@Preview
@Composable
fun DraggableContactItemSwipedPreview() {
    ProfilesTheme {
        val density = LocalDensity.current
        val actionSize = 80.dp
        val actionSizePx = with(density) { actionSize.toPx() }

        val state = remember {
            AnchoredDraggableState(
                initialValue = DragAnchors.End,
                anchors = DraggableAnchors {
                    DragAnchors.Start at 0f
                    DragAnchors.End at -actionSizePx
                }
            )
        }

        DraggableContactItemStateless(
            state = state,
            actionSize = actionSize,
            onDeleteClick = {},
            content = { ContactItemCard("Name Surname") }
        )
    }
}