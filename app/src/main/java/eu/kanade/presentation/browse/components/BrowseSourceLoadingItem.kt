package eu.kanade.presentation.browse.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
internal fun BrowseSourceLoadingItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = GSpace.s5),
        horizontalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(color = Graphite.colors.textMuted)
    }
}
