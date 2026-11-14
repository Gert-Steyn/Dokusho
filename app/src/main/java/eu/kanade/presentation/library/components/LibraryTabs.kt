package eu.kanade.presentation.library.components

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.kanade.presentation.category.visualName
import tachiyomi.domain.category.model.Category
import tachiyomi.presentation.core.components.material.TabText
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
internal fun LibraryTabs(
    categories: List<Category>,
    pagerState: PagerState,
    getItemCountForCategory: (Category) -> Int?,
    onTabItemClick: (Int) -> Unit,
) {
    val currentPageIndex = pagerState.currentPage.coerceAtMost(categories.lastIndex)
    PrimaryScrollableTabRow(
        selectedTabIndex = currentPageIndex,
        edgePadding = 0.dp,
        containerColor = Graphite.colors.canvas,
        contentColor = Graphite.colors.text,
        divider = {
            HorizontalDivider(color = Graphite.colors.borderSubtle, thickness = GSize.border)
        },
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(currentPageIndex),
                height = GSpace.s1,
                color = Graphite.colors.accent,
            )
        },
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = currentPageIndex == index,
                onClick = { onTabItemClick(index) },
                text = {
                    TabText(
                        text = category.visualName,
                        badgeCount = getItemCountForCategory(category),
                    )
                },
                selectedContentColor = Graphite.colors.text,
                unselectedContentColor = Graphite.colors.textSecondary,
            )
        }
    }
}
