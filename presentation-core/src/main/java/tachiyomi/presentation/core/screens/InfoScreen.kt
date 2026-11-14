package tachiyomi.presentation.core.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.Newspaper
import tachiyomi.presentation.core.components.graphite.GPrimaryButton
import tachiyomi.presentation.core.components.graphite.GSecondaryButton
import tachiyomi.presentation.core.components.material.Scaffold
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun InfoScreen(
    icon: ImageVector,
    headingText: String,
    subtitleText: String,
    acceptText: String,
    onAcceptClick: () -> Unit,
    canAccept: Boolean = true,
    rejectText: String? = null,
    onRejectClick: (() -> Unit)? = null,
    rejectAction: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        bottomBar = {
            val borderColor = Graphite.colors.borderSubtle
            Column(
                modifier = Modifier
                    .background(Graphite.colors.canvas)
                    .drawBehind {
                        drawLine(
                            borderColor,
                            Offset(0f, 0f),
                            Offset(size.width, 0f),
                            GSize.border.toPx(),
                        )
                    }
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .padding(
                        horizontal = GSpace.s5,
                        vertical = GSpace.s3,
                    ),
            ) {
                GPrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = canAccept,
                    onClick = onAcceptClick,
                    text = acceptText,
                )
                if (rejectText != null && onRejectClick != null) {
                    Row(horizontalArrangement = Arrangement.spacedBy(GSpace.s3)) {
                        GSecondaryButton(
                            modifier = Modifier.weight(1f),
                            onClick = onRejectClick,
                            text = rejectText,
                        )
                        rejectAction?.invoke(this)
                    }
                }
            }
        },
    ) { paddingValues ->
        // Status bar scrim
        Box(
            modifier = Modifier
                .zIndex(2f)
                .background(Graphite.colors.canvas)
                .fillMaxWidth()
                .height(paddingValues.calculateTopPadding()),
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(top = GSpace.s8)
                .padding(horizontal = GSpace.s5),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = GSpace.s3)
                    .size(48.dp),
                tint = Graphite.colors.textMuted,
            )
            Text(
                text = headingText,
                style = Graphite.type.display,
                color = Graphite.colors.text,
            )
            Text(
                text = subtitleText,
                modifier = Modifier.padding(vertical = GSpace.s3),
                style = Graphite.type.label,
                color = Graphite.colors.textSecondary,
            )

            content()
        }
    }
}

@PreviewLightDark
@Composable
private fun InfoScaffoldPreview() {
    InfoScreen(
        icon = Lucide.Icons.Newspaper,
        headingText = "Heading",
        subtitleText = "Subtitle",
        acceptText = "Accept",
        onAcceptClick = {},
        rejectText = "Reject",
        onRejectClick = {},
    ) {
        Text("Hello world")
    }
}
