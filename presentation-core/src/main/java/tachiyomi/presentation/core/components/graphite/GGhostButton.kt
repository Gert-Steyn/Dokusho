package tachiyomi.presentation.core.components.graphite

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import tachiyomi.presentation.core.theme.GButton
import tachiyomi.presentation.core.theme.GSize

@Composable
fun GGhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.heightIn(min = GSize.control),
        enabled = enabled,
        shape = GButton.shape,
        colors = GButton.ghost(),
        contentPadding = GButton.padding,
    ) {
        GButtonLabel(text = text, icon = icon)
    }
}
