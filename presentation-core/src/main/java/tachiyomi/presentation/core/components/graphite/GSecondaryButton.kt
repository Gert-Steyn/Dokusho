package tachiyomi.presentation.core.components.graphite

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import tachiyomi.presentation.core.theme.GButton
import tachiyomi.presentation.core.theme.GSize

@Composable
fun GSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.heightIn(min = GSize.control),
        enabled = enabled,
        shape = GButton.shape,
        colors = GButton.secondary(),
        border = GButton.secondaryBorder(),
        contentPadding = GButton.padding,
    ) {
        GButtonLabel(text = text, icon = icon)
    }
}
