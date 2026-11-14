package eu.kanade.presentation.reader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import eu.kanade.presentation.components.AdaptiveSheet
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.Copy
import mihon.icons.lucide.icons.Image
import mihon.icons.lucide.icons.Save
import mihon.icons.lucide.icons.Share2
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.ActionButton
import tachiyomi.presentation.core.components.graphite.GDialog
import tachiyomi.presentation.core.components.graphite.GGhostButton
import tachiyomi.presentation.core.components.graphite.GPrimaryButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GSpace

@Composable
fun ReaderPageActionsDialog(
    onDismissRequest: () -> Unit,
    onSetAsCover: () -> Unit,
    onShare: (Boolean) -> Unit,
    onSave: () -> Unit,
) {
    var showSetCoverDialog by remember { mutableStateOf(false) }

    AdaptiveSheet(onDismissRequest = onDismissRequest) {
        Row(
            modifier = Modifier.padding(vertical = GSpace.s5),
            horizontalArrangement = Arrangement.spacedBy(GSpace.s3),
        ) {
            ActionButton(
                modifier = Modifier.weight(1f),
                title = stringResource(MR.strings.set_as_cover),
                icon = Lucide.Icons.Image,
                onClick = { showSetCoverDialog = true },
            )
            ActionButton(
                modifier = Modifier.weight(1f),
                title = stringResource(MR.strings.action_copy_to_clipboard),
                icon = Lucide.Icons.Copy,
                onClick = {
                    onShare(true)
                    onDismissRequest()
                },
            )
            ActionButton(
                modifier = Modifier.weight(1f),
                title = stringResource(MR.strings.action_share),
                icon = Lucide.Icons.Share2,
                onClick = {
                    onShare(false)
                    onDismissRequest()
                },
            )
            ActionButton(
                modifier = Modifier.weight(1f),
                title = stringResource(MR.strings.action_save),
                icon = Lucide.Icons.Save,
                onClick = {
                    onSave()
                    onDismissRequest()
                },
            )
        }
    }

    if (showSetCoverDialog) {
        SetCoverDialog(
            onConfirm = {
                onSetAsCover()
                showSetCoverDialog = false
            },
            onDismiss = { showSetCoverDialog = false },
        )
    }
}

@Composable
private fun SetCoverDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    GDialog(
        text = {
            Text(stringResource(MR.strings.confirm_set_image_as_cover))
        },
        confirmButton = {
            GPrimaryButton(
                text = stringResource(MR.strings.action_ok),
                onClick = onConfirm,
            )
        },
        dismissButton = {
            GGhostButton(
                text = stringResource(MR.strings.action_cancel),
                onClick = onDismiss,
            )
        },
        onDismissRequest = onDismiss,
    )
}
