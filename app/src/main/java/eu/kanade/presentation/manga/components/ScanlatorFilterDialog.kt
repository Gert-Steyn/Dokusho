package eu.kanade.presentation.manga.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.Square
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

@Composable
fun ScanlatorFilterDialog(
    availableScanlators: Set<String>,
    excludedScanlators: Set<String>,
    onDismissRequest: () -> Unit,
    onConfirm: (Set<String>) -> Unit,
) {
    val sortedAvailableScanlators = remember(availableScanlators) {
        availableScanlators.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it })
    }
    val mutableExcludedScanlators = remember(excludedScanlators) { excludedScanlators.toMutableStateList() }
    GDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(MR.strings.exclude_scanlators)) },
        text = textFunc@{
            if (sortedAvailableScanlators.isEmpty()) {
                Text(text = stringResource(MR.strings.no_scanlators_found))
                return@textFunc
            }
            Box {
                val state = rememberLazyListState()
                LazyColumn(state = state) {
                    sortedAvailableScanlators.forEach { scanlator ->
                        item {
                            val isExcluded = mutableExcludedScanlators.contains(scanlator)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable {
                                        if (isExcluded) {
                                            mutableExcludedScanlators.remove(scanlator)
                                        } else {
                                            mutableExcludedScanlators.add(scanlator)
                                        }
                                    }
                                    .minimumInteractiveComponentSize()
                                    .clip(GRadius.control)
                                    .fillMaxWidth()
                                    .padding(horizontal = GSpace.s3),
                            ) {
                                Icon(
                                    imageVector = if (isExcluded) {
                                        Lucide.Icons.SquareX
                                    } else {
                                        Lucide.Icons.Square
                                    },
                                    modifier = Modifier.size(GSize.icon),
                                    tint = if (isExcluded) {
                                        Graphite.colors.accent
                                    } else {
                                        Graphite.colors.textMuted
                                    },
                                    contentDescription = null,
                                )
                                Text(
                                    text = scanlator,
                                    style = Graphite.type.body,
                                    modifier = Modifier.padding(start = GSpace.s6),
                                )
                            }
                        }
                    }
                }
                if (state.canScrollBackward) HorizontalDivider(modifier = Modifier.align(Alignment.TopCenter))
                if (state.canScrollForward) HorizontalDivider(modifier = Modifier.align(Alignment.BottomCenter))
            }
        },
        dismissButton = {
            if (sortedAvailableScanlators.isNotEmpty()) {
                if (mutableExcludedScanlators.isEmpty()) {
                    GGhostButton(
                        text = stringResource(MR.strings.action_select_all),
                        onClick = { mutableExcludedScanlators.addAll(availableScanlators) },
                    )
                } else {
                    GGhostButton(
                        text = stringResource(MR.strings.action_reset),
                        onClick = mutableExcludedScanlators::clear,
                    )
                }
            }
        },
        confirmButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(GSpace.s3)) {
                GGhostButton(
                    text = stringResource(MR.strings.action_cancel),
                    onClick = onDismissRequest,
                )
                if (sortedAvailableScanlators.isNotEmpty()) {
                    GPrimaryButton(
                        text = stringResource(MR.strings.action_ok),
                        onClick = {
                            onConfirm(mutableExcludedScanlators.toSet())
                            onDismissRequest()
                        },
                    )
                }
            }
        },
    )
}
