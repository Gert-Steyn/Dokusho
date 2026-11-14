package eu.kanade.presentation.more.settings.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import eu.kanade.presentation.theme.TachiyomiPreviewTheme
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.Eye
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun TextPreferenceWidget(
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    icon: ImageVector? = null,
    iconTint: Color = Graphite.colors.textMuted,
    widget: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    onPreferenceClick: (() -> Unit)? = null,
) {
    BasePreferenceWidget(
        modifier = modifier,
        title = title,
        subcomponent = if (!subtitle.isNullOrBlank()) {
            {
                Text(
                    text = subtitle,
                    modifier = Modifier
                        .padding(horizontal = PrefsHorizontalPadding),
                    style = Graphite.type.small,
                    color = Graphite.colors.textMuted,
                    maxLines = 10,
                )
            }
        } else {
            null
        },
        icon = if (icon != null) {
            {
                Icon(
                    imageVector = icon,
                    tint = iconTint,
                    contentDescription = null,
                    modifier = Modifier.size(GSize.icon),
                )
            }
        } else {
            null
        },
        onClick = onPreferenceClick,
        widget = widget,
        enabled = enabled,
    )
}

@PreviewLightDark
@Composable
private fun TextPreferenceWidgetPreview() {
    TachiyomiPreviewTheme {
        Surface {
            Column {
                TextPreferenceWidget(
                    title = "Text preference with icon",
                    subtitle = "Text preference summary",
                    icon = Lucide.Icons.Eye,
                    onPreferenceClick = {},
                )
                TextPreferenceWidget(
                    title = "Text preference",
                    subtitle = "Text preference summary",
                    onPreferenceClick = {},
                )
            }
        }
    }
}
