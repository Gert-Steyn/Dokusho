package eu.kanade.presentation.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import eu.kanade.presentation.manga.components.MarkdownRender
import eu.kanade.presentation.theme.TachiyomiPreviewTheme
import eu.kanade.tachiyomi.ui.more.NewUpdateScreenModel
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.automirrored.ExternalLink
import mihon.icons.lucide.icons.BadgeCheck
import mihon.icons.lucide.icons.RefreshCwOff
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GDialog
import tachiyomi.presentation.core.components.graphite.GGhostButton
import tachiyomi.presentation.core.components.graphite.GIconButton
import tachiyomi.presentation.core.components.graphite.GPrimaryButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.screens.InfoScreen
import tachiyomi.presentation.core.theme.GSpace

@Composable
fun NewUpdateScreen(
    versionName: String,
    changelogInfo: String,
    stage: NewUpdateScreenModel.Stage,
    downloadProgress: () -> Int,
    onOpenInBrowser: () -> Unit,
    onAcceptUpdate: () -> Unit,
    onRejectUpdate: () -> Unit,
    onSkipUpdate: () -> Unit,
) {
    var showSkipDialog by remember { mutableStateOf(false) }
    if (showSkipDialog) {
        val dismiss = { showSkipDialog = false }
        GDialog(
            onDismissRequest = dismiss,
            title = { Text(text = stringResource(MR.strings.update_check_skip_title, versionName)) },
            text = { Text(text = stringResource(MR.strings.update_check_skip_message)) },
            dismissButton = {
                GGhostButton(
                    text = stringResource(MR.strings.action_cancel),
                    onClick = dismiss,
                )
            },
            confirmButton = {
                GPrimaryButton(
                    text = stringResource(MR.strings.action_ok),
                    onClick = {
                        dismiss()
                        onSkipUpdate()
                    },
                )
            },
        )
    }

    InfoScreen(
        icon = Lucide.Icons.BadgeCheck,
        headingText = stringResource(MR.strings.update_check_notification_update_available),
        subtitleText = versionName,
        acceptText = when (stage) {
            NewUpdateScreenModel.Stage.Available -> stringResource(MR.strings.update_check_confirm)
            NewUpdateScreenModel.Stage.Downloading -> stringResource(
                MR.strings.downloading_with_progress,
                downloadProgress(),
            )
            NewUpdateScreenModel.Stage.Downloaded -> stringResource(MR.strings.action_install)
            NewUpdateScreenModel.Stage.Failed -> stringResource(MR.strings.action_retry)
        },
        onAcceptClick = onAcceptUpdate,
        canAccept = stage != NewUpdateScreenModel.Stage.Downloading,
        rejectText = stringResource(MR.strings.action_not_now),
        onRejectClick = onRejectUpdate,
        rejectAction = {
            GIconButton(
                icon = Lucide.Icons.RefreshCwOff,
                contentDescription = stringResource(MR.strings.update_check_skip),
                onClick = { showSkipDialog = true },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = GSpace.s6),
        ) {
            MarkdownRender(
                content = changelogInfo,
                flavour = remember { GFMFlavourDescriptor() },
            )

            GGhostButton(
                text = stringResource(MR.strings.update_check_open),
                onClick = onOpenInBrowser,
                icon = Lucide.AutoMirrored.ExternalLink,
                modifier = Modifier.padding(top = GSpace.s3),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun NewUpdateScreenPreview() {
    TachiyomiPreviewTheme {
        NewUpdateScreen(
            versionName = "v0.99.9",
            changelogInfo = """
                ## Yay
                Foobar

                ### More info
                - Hello
                - World
            """.trimIndent(),
            stage = NewUpdateScreenModel.Stage.Available,
            downloadProgress = { 0 },
            onOpenInBrowser = {},
            onAcceptUpdate = {},
            onRejectUpdate = {},
            onSkipUpdate = {},
        )
    }
}
