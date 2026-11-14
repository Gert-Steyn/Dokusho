package tachiyomi.presentation.core.components.graphite

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun GIconButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tint: Color = Graphite.colors.textMuted,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .minimumInteractiveComponentSize()
            .size(GSize.control),
        enabled = enabled,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(GSize.icon),
        )
    }
}
