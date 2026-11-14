package eu.kanade.presentation.reader.appbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import eu.kanade.tachiyomi.ui.reader.setting.ReaderOrientation
import eu.kanade.tachiyomi.ui.reader.setting.ReadingMode
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.Crop
import mihon.icons.lucide.icons.Scan
import mihon.icons.lucide.icons.Settings
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GIconButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun ReaderBottomBar(
    readingMode: ReadingMode,
    onClickReadingMode: () -> Unit,
    orientation: ReaderOrientation,
    onClickOrientation: () -> Unit,
    cropEnabled: Boolean,
    onClickCropBorder: () -> Unit,
    onClickSettings: () -> Unit,
    isTextChapter: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .pointerInput(Unit) {},
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // A text book is always read top to bottom in the text viewer, so reading mode and crop don't apply.
        if (!isTextChapter) {
            GIconButton(
                icon = readingMode.icon,
                contentDescription = stringResource(MR.strings.viewer),
                onClick = onClickReadingMode,
            )
        }

        GIconButton(
            icon = orientation.icon,
            contentDescription = stringResource(MR.strings.rotation_type),
            onClick = onClickOrientation,
        )

        if (!isTextChapter) {
            GIconButton(
                icon = if (cropEnabled) Lucide.Icons.Crop else Lucide.Icons.Scan,
                contentDescription = stringResource(MR.strings.pref_crop_borders),
                onClick = onClickCropBorder,
                tint = if (cropEnabled) Graphite.colors.accent else Graphite.colors.textMuted,
            )
        }

        GIconButton(
            icon = Lucide.Icons.Settings,
            contentDescription = stringResource(MR.strings.action_settings),
            onClick = onClickSettings,
        )
    }
}
