package tachiyomi.presentation.core.components.graphite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

/**
 * Dialog in the Graphite style. Built on [BasicAlertDialog] because the Material 3 AlertDialog
 * can't draw the 1dp border.
 *
 * Callers pass [GGhostButton] as the dismiss button and [GPrimaryButton] as the confirm button.
 */
@Composable
fun GDialog(
    onDismissRequest: () -> Unit,
    text: (@Composable () -> Unit)?,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    dismissButton: (@Composable () -> Unit)? = null,
    properties: DialogProperties = DialogProperties(),
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        properties = properties,
    ) {
        Surface(
            shape = GRadius.dialog,
            color = Graphite.colors.raised,
            contentColor = Graphite.colors.text,
            border = BorderStroke(GSize.border, Graphite.colors.border),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(min = DialogMinWidth, max = DialogMaxWidth)
                    .padding(GSpace.s5),
            ) {
                title?.let {
                    CompositionLocalProvider(LocalContentColor provides Graphite.colors.text) {
                        ProvideTextStyle(Graphite.type.title) {
                            it()
                        }
                    }
                    Spacer(modifier = Modifier.height(GSpace.s3))
                }
                text?.let {
                    CompositionLocalProvider(LocalContentColor provides Graphite.colors.textSecondary) {
                        ProvideTextStyle(Graphite.type.body) {
                            it()
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = GSpace.s6),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    dismissButton?.let {
                        it()
                        Spacer(modifier = Modifier.width(GSpace.s3))
                    }
                    confirmButton()
                }
            }
        }
    }
}

private val DialogMinWidth = 280.dp
private val DialogMaxWidth = 420.dp
