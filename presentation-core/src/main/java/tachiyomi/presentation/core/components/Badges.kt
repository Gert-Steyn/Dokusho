package tachiyomi.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun BadgeGroup(
    modifier: Modifier = Modifier,
    shape: Shape = GRadius.tag,
    content: @Composable RowScope.() -> Unit,
) {
    Row(modifier = modifier.clip(shape)) {
        content()
    }
}

@Composable
fun Badge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Graphite.colors.hover,
    textColor: Color = Graphite.colors.textSecondary,
    shape: Shape = GRadius.tag,
) {
    Text(
        text = text,
        modifier = modifier
            .clip(shape)
            .background(color)
            .padding(horizontal = GSpace.s3, vertical = GSpace.s1),
        color = textColor,
        maxLines = 1,
        style = Graphite.type.caption,
    )
}

@Composable
fun Badge(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    color: Color = Graphite.colors.hover,
    iconColor: Color = Graphite.colors.textSecondary,
    shape: Shape = GRadius.tag,
) {
    val iconContentPlaceholder = "[icon]"
    val text = buildAnnotatedString {
        appendInlineContent(iconContentPlaceholder)
    }
    val inlineContent = mapOf(
        Pair(
            iconContentPlaceholder,
            InlineTextContent(
                Placeholder(
                    width = Graphite.type.caption.fontSize,
                    height = Graphite.type.caption.fontSize,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
                ),
            ) {
                Icon(
                    imageVector = imageVector,
                    tint = iconColor,
                    contentDescription = null,
                )
            },
        ),
    )

    Text(
        text = text,
        inlineContent = inlineContent,
        modifier = modifier
            .clip(shape)
            .background(color)
            .padding(horizontal = GSpace.s3, vertical = GSpace.s1),
        color = iconColor,
        maxLines = 1,
        style = Graphite.type.caption,
    )
}
