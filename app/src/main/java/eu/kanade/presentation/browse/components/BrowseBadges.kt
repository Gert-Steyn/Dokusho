package eu.kanade.presentation.browse.components

import androidx.compose.runtime.Composable
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.LibraryBig
import tachiyomi.presentation.core.components.Badge

@Composable
internal fun InLibraryBadge(enabled: Boolean) {
    if (enabled) {
        Badge(
            imageVector = Lucide.Icons.LibraryBig,
        )
    }
}
