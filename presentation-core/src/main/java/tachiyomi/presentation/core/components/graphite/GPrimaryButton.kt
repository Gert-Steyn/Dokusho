package tachiyomi.presentation.core.components.graphite

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import tachiyomi.presentation.core.theme.GButton
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun GPrimaryButton(
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
        colors = GButton.primary(),
        contentPadding = GButton.padding,
        elevation = null,
    ) {
        GButtonLabel(text = text, icon = icon)
    }
}

@Composable
internal fun GButtonLabel(text: String, icon: ImageVector?) {
    if (icon != null) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(GSize.icon),
        )
        Spacer(modifier = Modifier.width(GSpace.s3))
    }
    Text(text = text, style = Graphite.type.label)
}
