package eu.kanade.presentation.library.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import eu.kanade.presentation.theme.TachiyomiPreviewTheme
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.Folder
import tachiyomi.presentation.core.components.Badge
import tachiyomi.presentation.core.theme.Graphite

@Composable
internal fun DownloadsBadge(count: Int) {
    if (count > 0) {
        Badge(
            text = "$count",
            color = Graphite.colors.accent,
            textColor = Graphite.colors.onAccent,
        )
    }
}

@Composable
internal fun UnreadBadge(count: Long) {
    if (count > 0) {
        Badge(text = "$count")
    }
}

@Composable
internal fun LanguageBadge(
    isLocal: Boolean,
    sourceLanguage: String,
) {
    if (isLocal) {
        Badge(
            imageVector = Lucide.Icons.Folder,
            color = Graphite.colors.accent,
            iconColor = Graphite.colors.onAccent,
        )
    } else if (sourceLanguage.isNotEmpty()) {
        Badge(
            text = sourceLanguage,
            color = Graphite.colors.accent,
            textColor = Graphite.colors.onAccent,
        )
    }
}

@PreviewLightDark
@Composable
private fun BadgePreview() {
    TachiyomiPreviewTheme {
        Column {
            DownloadsBadge(count = 10)
            UnreadBadge(count = 10)
            LanguageBadge(isLocal = true, sourceLanguage = "EN")
            LanguageBadge(isLocal = false, sourceLanguage = "EN")
        }
    }
}
