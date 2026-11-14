package eu.kanade.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

/** Marks an unread chapter. Drawn rather than an icon, as Lucide's circle is an outline. */
@Composable
fun UnreadDot(modifier: Modifier = Modifier) {
    val description = stringResource(MR.strings.unread)
    Box(
        modifier = modifier
            .padding(end = GSpace.s2)
            .size(GSpace.s3)
            .background(Graphite.colors.accent, CircleShape)
            .semantics { contentDescription = description },
    )
}
