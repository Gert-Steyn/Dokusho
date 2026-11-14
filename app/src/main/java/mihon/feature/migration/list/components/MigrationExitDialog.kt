package mihon.feature.migration.list.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GDangerButton
import tachiyomi.presentation.core.components.graphite.GDialog
import tachiyomi.presentation.core.components.graphite.GGhostButton
import tachiyomi.presentation.core.i18n.stringResource

@Composable
fun MigrationExitDialog(
    onDismissRequest: () -> Unit,
    exitMigration: () -> Unit,
) {
    GDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(MR.strings.migrationListScreen_exitDialogTitle))
        },
        text = null,
        confirmButton = {
            GDangerButton(
                text = stringResource(MR.strings.migrationListScreen_exitDialog_stopLabel),
                onClick = exitMigration,
            )
        },
        dismissButton = {
            GGhostButton(
                text = stringResource(MR.strings.migrationListScreen_exitDialog_cancelLabel),
                onClick = onDismissRequest,
            )
        },
    )
}
