package eu.kanade.presentation.browse.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import tachiyomi.domain.manga.model.Manga
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GDangerButton
import tachiyomi.presentation.core.components.graphite.GDialog
import tachiyomi.presentation.core.components.graphite.GGhostButton
import tachiyomi.presentation.core.i18n.stringResource

@Composable
fun RemoveMangaDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    mangaToRemove: Manga,
) {
    GDialog(
        onDismissRequest = onDismissRequest,
        dismissButton = {
            GGhostButton(
                text = stringResource(MR.strings.action_cancel),
                onClick = onDismissRequest,
            )
        },
        confirmButton = {
            GDangerButton(
                text = stringResource(MR.strings.action_remove),
                onClick = {
                    onDismissRequest()
                    onConfirm()
                },
            )
        },
        title = {
            Text(text = stringResource(MR.strings.are_you_sure))
        },
        text = {
            Text(text = stringResource(MR.strings.remove_manga, mangaToRemove.title))
        },
    )
}
