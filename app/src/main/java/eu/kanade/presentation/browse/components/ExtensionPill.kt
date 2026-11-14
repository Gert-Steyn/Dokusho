package eu.kanade.presentation.browse.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
internal fun ExtensionPill(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Graphite.colors.textSecondary,
) {
    Text(
        text = text,
        modifier = modifier
            .background(color = color.copy(alpha = PILL_BACKGROUND_ALPHA), shape = GRadius.tag)
            .padding(horizontal = GSpace.s3, vertical = GSpace.s1),
        color = color,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = Graphite.type.caption,
    )
}

private const val PILL_BACKGROUND_ALPHA = 0.12f
