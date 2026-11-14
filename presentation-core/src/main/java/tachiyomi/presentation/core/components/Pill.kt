package tachiyomi.presentation.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun Pill(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Graphite.colors.hover,
    contentColor: Color = Graphite.colors.textSecondary,
    style: TextStyle = LocalTextStyle.current,
) {
    Surface(
        modifier = modifier
            .padding(start = GSpace.s2),
        shape = GRadius.control,
        color = color,
        contentColor = contentColor,
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = GSpace.s3, vertical = GSpace.s1),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                maxLines = 1,
                style = style,
            )
        }
    }
}

@Composable
fun Pill(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Graphite.colors.hover,
    contentColor: Color = Graphite.colors.textSecondary,
    fontSize: TextUnit = LocalTextStyle.current.fontSize,
) {
    Pill(
        text = text,
        modifier = modifier,
        color = color,
        contentColor = contentColor,
        style = LocalTextStyle.current.merge(fontSize = fontSize),
    )
}
