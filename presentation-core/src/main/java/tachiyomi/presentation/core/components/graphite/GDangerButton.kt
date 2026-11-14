package tachiyomi.presentation.core.components.graphite

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import tachiyomi.presentation.core.theme.GButton
import tachiyomi.presentation.core.theme.GSize

/** Confirms a destructive action. Only use it inside its own confirmation. */
@Composable
fun GDangerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier.heightIn(min = GSize.control),
        enabled = enabled,
        shape = GButton.shape,
        colors = GButton.danger(),
        contentPadding = GButton.padding,
        elevation = null,
    ) {
        GButtonLabel(text = text, icon = icon)
    }
}
