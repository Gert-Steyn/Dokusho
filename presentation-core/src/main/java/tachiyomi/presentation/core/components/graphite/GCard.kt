package tachiyomi.presentation.core.components.graphite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite
import tachiyomi.presentation.core.theme.graphiteCardBorder

@Composable
fun GCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = GRadius.card,
        color = Graphite.colors.surface,
        contentColor = Graphite.colors.text,
        border = graphiteCardBorder(),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
    ) {
        Column(modifier = Modifier.padding(GSpace.s5), content = content)
    }
}
