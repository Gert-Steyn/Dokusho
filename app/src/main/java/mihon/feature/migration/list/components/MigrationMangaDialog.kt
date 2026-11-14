package mihon.feature.migration.list.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GDialog
import tachiyomi.presentation.core.components.graphite.GGhostButton
import tachiyomi.presentation.core.components.graphite.GPrimaryButton
import tachiyomi.presentation.core.i18n.pluralStringResource
import tachiyomi.presentation.core.i18n.stringResource

@Composable
fun MigrationMangaDialog(
    onDismissRequest: () -> Unit,
    copy: Boolean,
    totalCount: Int,
    skippedCount: Int,
    onMigrate: () -> Unit,
) {
    GDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = pluralStringResource(
                    resource = if (copy) {
                        MR.plurals.migrationListScreen_migrateDialog_copyTitle
                    } else {
                        MR.plurals.migrationListScreen_migrateDialog_migrateTitle
                    },
                    count = totalCount,
                    totalCount,
                ),
            )
        },
        text = {
            if (skippedCount > 0) {
                Text(
                    text = pluralStringResource(
                        resource = MR.plurals.migrationListScreen_migrateDialog_skipText,
                        count = skippedCount,
                        skippedCount,
                    ),
                )
            }
        },
        confirmButton = {
            GPrimaryButton(
                text = stringResource(
                    resource = if (copy) {
                        MR.strings.migrationListScreen_migrateDialog_copyLabel
                    } else {
                        MR.strings.migrationListScreen_migrateDialog_migrateLabel
                    },
                ),
                onClick = onMigrate,
            )
        },
        dismissButton = {
            GGhostButton(
                text = stringResource(MR.strings.migrationListScreen_migrateDialog_cancelLabel),
                onClick = onDismissRequest,
            )
        },
    )
}
