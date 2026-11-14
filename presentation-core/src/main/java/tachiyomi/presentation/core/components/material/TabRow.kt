package tachiyomi.presentation.core.components.material

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

/**
 * Underline tab strip: 2dp accent indicator and a 1dp rule under the whole strip.
 */
@Composable
fun GraphiteTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = Graphite.colors.canvas,
    tabs: @Composable () -> Unit,
) {
    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = Graphite.colors.text,
        divider = {
            HorizontalDivider(color = Graphite.colors.borderSubtle, thickness = GSize.border)
        },
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(selectedTabIndex),
                height = GSpace.s1,
                color = Graphite.colors.accent,
            )
        },
        tabs = tabs,
    )
}
