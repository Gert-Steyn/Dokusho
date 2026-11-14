package tachiyomi.presentation.core.components.graphite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tachiyomi.presentation.core.theme.GButton
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

/**
 * Replaces a floating action button. Expanded it is a full-width primary button above the bottom
 * inset; collapsed it is a square icon-only primary button at the end.
 */
@Composable
fun GFloatingPrimaryButton(
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    expanded: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = GSize.pagePad),
        contentAlignment = if (expanded) Alignment.Center else Alignment.CenterEnd,
    ) {
        Button(
            onClick = onClick,
            modifier = if (expanded) {
                Modifier.fillMaxWidth().heightIn(min = GSize.control)
            } else {
                Modifier.size(GSize.controlLg)
            },
            shape = GButton.shape,
            colors = GButton.primary(),
            contentPadding = if (expanded) GButton.padding else PaddingValues(0.dp),
            elevation = null,
        ) {
            CompositionLocalProvider(LocalContentColor provides Graphite.colors.onAccent) {
                ProvideTextStyle(Graphite.type.label) {
                    if (expanded) {
                        icon()
                        Spacer(modifier = Modifier.width(GSpace.s3))
                        text()
                    } else {
                        icon()
                    }
                }
            }
        }
    }
}
