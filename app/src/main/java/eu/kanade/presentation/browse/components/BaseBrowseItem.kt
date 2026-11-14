package eu.kanade.presentation.browse.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import tachiyomi.presentation.core.theme.GSpace

@Composable
fun BaseBrowseItem(
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit = {},
    onLongClickItem: () -> Unit = {},
    icon: @Composable RowScope.() -> Unit = {},
    action: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit = {},
) {
    Row(
        modifier = modifier
            .combinedClickable(
                onClick = onClickItem,
                onLongClick = onLongClickItem,
            )
            .padding(horizontal = GSpace.s5, vertical = GSpace.s3),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        content()
        action()
    }
}
