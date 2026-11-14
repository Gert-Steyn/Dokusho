package tachiyomi.presentation.core.components.graphite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

/**
 * Filter or choice chip in the Graphite style: a bordered outline when unselected, an accent tint when selected.
 */
@Composable
fun GChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val contentColor = if (selected) Graphite.colors.accent else Graphite.colors.textSecondary
    Surface(
        onClick = onClick,
        modifier = modifier.minimumInteractiveComponentSize(),
        enabled = enabled,
        shape = GRadius.control,
        color = if (selected) Graphite.colors.accentSubtle else Color.Transparent,
        contentColor = contentColor,
        border = if (selected) null else BorderStroke(GSize.border, Graphite.colors.border),
    ) {
        Box(
            modifier = Modifier
                .height(GSize.controlSm)
                .padding(horizontal = GSpace.s4),
            contentAlignment = Alignment.Center,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(GSize.icon),
                    )
                    Spacer(modifier = Modifier.size(GSpace.s3))
                }
                Text(text = text, style = Graphite.type.label)
            }
        }
    }
}
