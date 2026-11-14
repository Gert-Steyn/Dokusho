package eu.kanade.presentation.more.settings.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.Square
import mihon.icons.lucide.icons.SquareCheck
import mihon.icons.lucide.icons.SquareX
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GDialog
import tachiyomi.presentation.core.components.graphite.GGhostButton
import tachiyomi.presentation.core.components.graphite.GPrimaryButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

private enum class State {
    CHECKED,
    INVERSED,
    UNCHECKED,
}

@Composable
fun <T> TriStateListDialog(
    title: String,
    message: String? = null,
    items: List<T>,
    initialChecked: List<T>,
    initialInversed: List<T>,
    itemLabel: @Composable (T) -> String,
    onDismissRequest: () -> Unit,
    onValueChanged: (newIncluded: List<T>, newExcluded: List<T>) -> Unit,
) {
    val selected = remember {
        items
            .map {
                when (it) {
                    in initialChecked -> State.CHECKED
                    in initialInversed -> State.INVERSED
                    else -> State.UNCHECKED
                }
            }
            .toMutableStateList()
    }
    GDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = title) },
        text = {
            Column {
                if (message != null) {
                    Text(
                        text = message,
                        modifier = Modifier.padding(bottom = GSpace.s3),
                    )
                }

                Box {
                    val listState = rememberLazyListState()
                    LazyColumn(state = listState) {
                        itemsIndexed(items = items) { index, item ->
                            val state = selected[index]
                            Row(
                                modifier = Modifier
                                    .clip(GRadius.control)
                                    .clickable {
                                        selected[index] = when (state) {
                                            State.UNCHECKED -> State.CHECKED
                                            State.CHECKED -> State.INVERSED
                                            State.INVERSED -> State.UNCHECKED
                                        }
                                    }
                                    .defaultMinSize(minHeight = GSize.minTarget)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    modifier = Modifier.padding(end = GSpace.s5),
                                    imageVector = when (state) {
                                        State.UNCHECKED -> Lucide.Icons.Square
                                        State.CHECKED -> Lucide.Icons.SquareCheck
                                        State.INVERSED -> Lucide.Icons.SquareX
                                    },
                                    tint = if (state == State.UNCHECKED) {
                                        Graphite.colors.textMuted
                                    } else {
                                        Graphite.colors.accent
                                    },
                                    contentDescription = stringResource(
                                        when (state) {
                                            State.UNCHECKED -> MR.strings.not_selected
                                            State.CHECKED -> MR.strings.selected
                                            State.INVERSED -> MR.strings.disabled
                                        },
                                    ),
                                )
                                Text(text = itemLabel(item), style = Graphite.type.body)
                            }
                        }
                    }

                    if (listState.canScrollBackward) HorizontalDivider(modifier = Modifier.align(Alignment.TopCenter))
                    if (listState.canScrollForward) HorizontalDivider(modifier = Modifier.align(Alignment.BottomCenter))
                }
            }
        },
        dismissButton = {
            GGhostButton(
                text = stringResource(MR.strings.action_cancel),
                onClick = onDismissRequest,
            )
        },
        confirmButton = {
            GPrimaryButton(
                text = stringResource(MR.strings.action_ok),
                onClick = {
                    val included = items.mapIndexedNotNull { index, category ->
                        if (selected[index] == State.CHECKED) category else null
                    }
                    val excluded = items.mapIndexedNotNull { index, category ->
                        if (selected[index] == State.INVERSED) category else null
                    }
                    onValueChanged(included, excluded)
                },
            )
        },
    )
}
