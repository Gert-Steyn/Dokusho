package eu.kanade.presentation.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import eu.kanade.presentation.theme.TachiyomiPreviewTheme
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.LabeledCheckbox
import tachiyomi.presentation.core.components.graphite.GDangerButton
import tachiyomi.presentation.core.components.graphite.GDialog
import tachiyomi.presentation.core.components.graphite.GGhostButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GSpace

@Composable
fun HistoryDeleteDialog(
    onDismissRequest: () -> Unit,
    onDelete: (Boolean) -> Unit,
) {
    var removeEverything by remember { mutableStateOf(false) }

    GDialog(
        title = {
            Text(text = stringResource(MR.strings.action_remove))
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(GSpace.s3),
            ) {
                Text(text = stringResource(MR.strings.dialog_with_checkbox_remove_description))

                LabeledCheckbox(
                    label = stringResource(MR.strings.dialog_with_checkbox_reset),
                    checked = removeEverything,
                    onCheckedChange = { removeEverything = it },
                )
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            GDangerButton(
                text = stringResource(MR.strings.action_remove),
                onClick = {
                    onDelete(removeEverything)
                    onDismissRequest()
                },
            )
        },
        dismissButton = {
            GGhostButton(
                text = stringResource(MR.strings.action_cancel),
                onClick = onDismissRequest,
            )
        },
    )
}

@Composable
fun HistoryDeleteAllDialog(
    onDismissRequest: () -> Unit,
    onDelete: () -> Unit,
) {
    GDialog(
        title = {
            Text(text = stringResource(MR.strings.action_remove_everything))
        },
        text = {
            Text(text = stringResource(MR.strings.clear_history_confirmation))
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            GDangerButton(
                text = stringResource(MR.strings.action_ok),
                onClick = {
                    onDelete()
                    onDismissRequest()
                },
            )
        },
        dismissButton = {
            GGhostButton(
                text = stringResource(MR.strings.action_cancel),
                onClick = onDismissRequest,
            )
        },
    )
}

@PreviewLightDark
@Composable
private fun HistoryDeleteDialogPreview() {
    TachiyomiPreviewTheme {
        HistoryDeleteDialog(
            onDismissRequest = {},
            onDelete = {},
        )
    }
}
