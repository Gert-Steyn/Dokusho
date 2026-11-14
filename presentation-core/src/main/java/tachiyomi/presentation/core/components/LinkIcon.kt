package tachiyomi.presentation.core.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import tachiyomi.presentation.core.components.graphite.GIconButton
import tachiyomi.presentation.core.theme.GSpace

@Composable
fun LinkIcon(
    label: String,
    icon: ImageVector,
    url: String,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    GIconButton(
        icon = icon,
        contentDescription = label,
        onClick = { uriHandler.openUri(url) },
        modifier = modifier.padding(GSpace.s2),
    )
}
