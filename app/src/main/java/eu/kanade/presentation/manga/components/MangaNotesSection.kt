package eu.kanade.presentation.manga.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.NotebookPen
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GGhostButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun MangaNotesSection(
    content: String,
    expanded: Boolean,
    onEditNotes: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (content.isBlank()) return
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MangaNotesDisplay(
            content = content,
            modifier = modifier.fillMaxWidth(),
        )
        if (expanded) {
            GGhostButton(
                text = stringResource(MR.strings.action_edit_notes),
                onClick = onEditNotes,
                icon = Lucide.Icons.NotebookPen,
                modifier = Modifier.padding(horizontal = GSpace.s5, vertical = GSpace.s2),
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(
                    top = if (expanded) 0.dp else GSpace.s4,
                    bottom = if (expanded) GSpace.s5 else GSpace.s4,
                ),
            color = Graphite.colors.borderSubtle,
        )
    }
}

@PreviewLightDark
@Composable
private fun MangaNotesSectionPreview() {
    MangaNotesSection(
        onEditNotes = {},
        expanded = true,
        content = "# Hello world\ntest1234 hi there!",
    )
}
