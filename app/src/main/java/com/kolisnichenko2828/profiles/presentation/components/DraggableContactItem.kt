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
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
    swipeCoordinator: SwipeCoordinator,
    onDeleteConfirm: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var closeItemCallback by remember { mutableStateOf<(() -> Unit)?>(null) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                showDialog = false
                onDeleteConfirm(id)
            },
            onDismiss = {
                showDialog = false
                closeItemCallback?.invoke()
            }
        )
    }

    DraggableContactItemStateless(
        modifier = modifier,
        onDeleteClick = { showDialog = true },
        onInteraction = { closeAction ->
            closeItemCallback = closeAction
            swipeCoordinator.onItemInteraction(id, closeAction)
        },
        content = content
    )
}

@Composable
fun DraggableContactItemStateless(
    onDeleteClick: () -> Unit,
    onInteraction: (closeItem: () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
    initialDragAnchor: DragAnchors = DragAnchors.Start,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val actionSize = 80.dp
    val actionSizePx = with(density) { actionSize.toPx() }

    val state = remember {
        AnchoredDraggableState(
            initialValue = initialDragAnchor,
            anchors = DraggableAnchors {
                DragAnchors.Start at 0f
                DragAnchors.End at -actionSizePx
            }
        )
    }

    val closeItem: () -> Unit = {
        coroutineScope.launch { state.animateTo(DragAnchors.Start) }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(CardDefaults.shape)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        if (event.changes.any { it.pressed && !it.previousPressed }) {
                            onInteraction(closeItem)
                        }
                    }
                }
            }
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
        DraggableContactItemStateless(
            initialDragAnchor = DragAnchors.Start,
            onDeleteClick = {},
            onInteraction = {}
        ) {
            ContactItemCard("Name Surname")
        }
    }
}

@Preview
@Composable
fun DraggableContactItemSwipedPreview() {
    ProfilesTheme {
        DraggableContactItemStateless(
            initialDragAnchor = DragAnchors.End,
            onDeleteClick = {},
            onInteraction = {}
        ) {
            ContactItemCard("Name Surname")
        }
    }
}

class SwipeCoordinator {
    private var currentOpenedId: String? = null
    private var closeAction: (() -> Unit)? = null

    fun onItemInteraction(id: String, closeThisItem: () -> Unit) {
        if (currentOpenedId != id) {
            closeAction?.invoke()

            currentOpenedId = id
            closeAction = closeThisItem
        }
    }

    fun clear() {
        currentOpenedId = null
        closeAction = null
    }
}