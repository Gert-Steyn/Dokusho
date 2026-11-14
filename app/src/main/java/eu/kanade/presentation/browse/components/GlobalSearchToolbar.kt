package eu.kanade.presentation.browse.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import eu.kanade.presentation.components.SearchToolbar
import eu.kanade.tachiyomi.ui.browse.source.globalsearch.SourceFilter
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.CheckCheck
import mihon.icons.lucide.icons.ListFilter
import mihon.icons.lucide.icons.Pin
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GChip
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun GlobalSearchToolbar(
    searchQuery: String?,
    progress: Int,
    total: Int,
    navigateUp: () -> Unit,
    onChangeSearchQuery: (String?) -> Unit,
    onSearch: (String) -> Unit,
    hideSourceFilter: Boolean,
    sourceFilter: SourceFilter,
    onChangeSearchFilter: (SourceFilter) -> Unit,
    onlyShowHasResults: Boolean,
    onToggleResults: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    Column(modifier = Modifier.background(Graphite.colors.canvas)) {
        Box {
            SearchToolbar(
                searchQuery = searchQuery,
                onChangeSearchQuery = onChangeSearchQuery,
                onSearch = onSearch,
                onClickCloseSearch = navigateUp,
                navigateUp = navigateUp,
                scrollBehavior = scrollBehavior,
            )
            if (progress in 1..<total) {
                LinearProgressIndicator(
                    progress = { progress / total.toFloat() },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth(),
                )
            }
        }

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = GSpace.s3),
            horizontalArrangement = Arrangement.spacedBy(GSpace.s3),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // TODO: make this UX better; it only applies when triggering a new search
            if (!hideSourceFilter) {
                GChip(
                    text = stringResource(MR.strings.pinned_sources),
                    selected = sourceFilter == SourceFilter.PinnedOnly,
                    onClick = { onChangeSearchFilter(SourceFilter.PinnedOnly) },
                    icon = Lucide.Icons.Pin,
                )
                GChip(
                    text = stringResource(MR.strings.all),
                    selected = sourceFilter == SourceFilter.All,
                    onClick = { onChangeSearchFilter(SourceFilter.All) },
                    icon = Lucide.Icons.CheckCheck,
                )

                VerticalDivider(modifier = Modifier.height(GSize.controlSm))
            }

            GChip(
                text = stringResource(MR.strings.has_results),
                selected = onlyShowHasResults,
                onClick = { onToggleResults() },
                icon = Lucide.Icons.ListFilter,
            )
        }

        HorizontalDivider()
    }
}
