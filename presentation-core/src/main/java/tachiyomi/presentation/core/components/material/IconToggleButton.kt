package tachiyomi.presentation.core.components.material

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun IconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    imageVector: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
) {
    FilledIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier
            .height(GSize.control),
        shape = GRadius.control,
        colors = IconButtonDefaults.filledIconToggleButtonColors(
            containerColor = Graphite.colors.hover,
            contentColor = Graphite.colors.textSecondary,
            checkedContainerColor = Graphite.colors.accentSubtle,
            checkedContentColor = Graphite.colors.accent,
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(GSpace.s3),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(GSpace.s3),
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(GSize.icon),
            )

            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = Graphite.type.label,
            )
        }
    }
}
