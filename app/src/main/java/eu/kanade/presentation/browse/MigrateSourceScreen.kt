package eu.kanade.presentation.browse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.kanade.domain.source.interactor.SetMigrateSorting
import eu.kanade.presentation.browse.components.BaseSourceItem
import eu.kanade.presentation.browse.components.SourceIcon
import eu.kanade.tachiyomi.ui.browse.migration.sources.MigrateSourceViewModel
import eu.kanade.tachiyomi.util.system.copyToClipboard
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.ArrowDown
import mihon.icons.lucide.icons.ArrowDownAZ
import mihon.icons.lucide.icons.ArrowUp
import mihon.icons.lucide.icons.Hash
import tachiyomi.domain.source.model.Source
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.Badge
import tachiyomi.presentation.core.components.BadgeGroup
import tachiyomi.presentation.core.components.ScrollbarLazyColumn
import tachiyomi.presentation.core.components.Scroller.STICKY_HEADER_KEY_PREFIX
import tachiyomi.presentation.core.components.graphite.GIconButton
import tachiyomi.presentation.core.components.material.topSmallPaddingValues
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.screens.EmptyScreen
import tachiyomi.presentation.core.screens.LoadingScreen
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite
import tachiyomi.presentation.core.util.plus

@Composable
fun MigrateSourceScreen(
    state: MigrateSourceViewModel.State,
    contentPadding: PaddingValues,
    onClickItem: (Source) -> Unit,
    onToggleSortingDirection: () -> Unit,
    onToggleSortingMode: () -> Unit,
) {
    val context = LocalContext.current
    when {
        state.isLoading -> LoadingScreen(Modifier.padding(contentPadding))
        state.isEmpty -> EmptyScreen(
            stringRes = MR.strings.information_empty_library,
            modifier = Modifier.padding(contentPadding),
        )
        else ->
            MigrateSourceList(
                list = state.items,
                contentPadding = contentPadding,
                onClickItem = onClickItem,
                onLongClickItem = { source ->
                    val sourceId = source.id.toString()
                    context.copyToClipboard(sourceId, sourceId)
                },
                sortingMode = state.sortingMode,
                onToggleSortingMode = onToggleSortingMode,
                sortingDirection = state.sortingDirection,
                onToggleSortingDirection = onToggleSortingDirection,
            )
    }
}

@Composable
private fun MigrateSourceList(
    list: List<Pair<Source, Long>>,
    contentPadding: PaddingValues,
    onClickItem: (Source) -> Unit,
    onLongClickItem: (Source) -> Unit,
    sortingMode: SetMigrateSorting.Mode,
    onToggleSortingMode: () -> Unit,
    sortingDirection: SetMigrateSorting.Direction,
    onToggleSortingDirection: () -> Unit,
) {
    ScrollbarLazyColumn(
        contentPadding = contentPadding + topSmallPaddingValues,
    ) {
        stickyHeader(key = STICKY_HEADER_KEY_PREFIX) {
            Row(
                modifier = Modifier
                    .background(Graphite.colors.canvas)
                    .padding(start = GSpace.s5),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(MR.strings.migration_selection_prompt),
                    modifier = Modifier.weight(1f),
                    style = Graphite.type.caption.copy(color = Graphite.colors.textMuted),
                )

                GIconButton(
                    icon = when (sortingMode) {
                        SetMigrateSorting.Mode.ALPHABETICAL -> Lucide.Icons.ArrowDownAZ
                        SetMigrateSorting.Mode.TOTAL -> Lucide.Icons.Hash
                    },
                    contentDescription = when (sortingMode) {
                        SetMigrateSorting.Mode.ALPHABETICAL -> stringResource(MR.strings.action_sort_alpha)
                        SetMigrateSorting.Mode.TOTAL -> stringResource(MR.strings.action_sort_count)
                    },
                    onClick = onToggleSortingMode,
                )
                GIconButton(
                    icon = when (sortingDirection) {
                        SetMigrateSorting.Direction.ASCENDING -> Lucide.Icons.ArrowUp
                        SetMigrateSorting.Direction.DESCENDING -> Lucide.Icons.ArrowDown
                    },
                    contentDescription = when (sortingDirection) {
                        SetMigrateSorting.Direction.ASCENDING -> stringResource(MR.strings.action_asc)
                        SetMigrateSorting.Direction.DESCENDING -> stringResource(MR.strings.action_desc)
                    },
                    onClick = onToggleSortingDirection,
                )
            }
        }

        items(
            items = list,
            key = { (source, _) -> "migrate-${source.id}" },
        ) { (source, count) ->
            MigrateSourceItem(
                modifier = Modifier.animateItem(),
                source = source,
                count = count,
                onClickItem = { onClickItem(source) },
                onLongClickItem = { onLongClickItem(source) },
            )
        }
    }
}

@Composable
private fun MigrateSourceItem(
    source: Source,
    count: Long,
    onClickItem: () -> Unit,
    onLongClickItem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseSourceItem(
        modifier = modifier,
        source = source,
        showLanguageInContent = source.lang != "",
        onClickItem = onClickItem,
        onLongClickItem = onLongClickItem,
        icon = { SourceIcon(source = source, modifier = Modifier.size(48.dp)) },
        action = {
            BadgeGroup {
                Badge(text = "$count")
            }
        },
        content = { _, sourceLangString ->
            Column(
                modifier = Modifier
                    .padding(horizontal = GSpace.s5)
                    .weight(1f),
            ) {
                Text(
                    text = source.name.ifBlank { source.id.toString() },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = Graphite.type.body,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(GSpace.s3),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (sourceLangString != null) {
                        Text(
                            text = sourceLangString,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = Graphite.type.small,
                            color = Graphite.colors.textMuted,
                        )
                    }
                    if (source.isStub) {
                        Text(
                            text = stringResource(MR.strings.not_installed),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = Graphite.type.small,
                            color = Graphite.colors.danger,
                        )
                    }
                }
            }
        },
    )
}
