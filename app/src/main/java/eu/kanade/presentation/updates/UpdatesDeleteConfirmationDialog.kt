package eu.kanade.presentation.updates

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GDangerButton
import tachiyomi.presentation.core.components.graphite.GDialog
import tachiyomi.presentation.core.components.graphite.GGhostButton
import tachiyomi.presentation.core.i18n.stringResource

@Composable
fun UpdatesDeleteConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    GDialog(
        text = {
            Text(text = stringResource(MR.strings.confirm_delete_chapters))
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            GDangerButton(
                text = stringResource(MR.strings.action_ok),
                onClick = {
                    onConfirm()
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
