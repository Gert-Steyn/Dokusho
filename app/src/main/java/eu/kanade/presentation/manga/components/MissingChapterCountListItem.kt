package eu.kanade.presentation.manga.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import eu.kanade.presentation.theme.TachiyomiPreviewTheme
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.i18n.pluralStringResource
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun MissingChapterCountListItem(
    count: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = GSpace.s5,
                vertical = GSpace.s3,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(GSpace.s5),
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = Graphite.colors.borderSubtle)
        Text(
            text = pluralStringResource(MR.plurals.missing_chapters, count = count, count),
            style = Graphite.type.caption,
            color = Graphite.colors.textMuted,
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = Graphite.colors.borderSubtle)
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    TachiyomiPreviewTheme {
        Surface {
            MissingChapterCountListItem(count = 42)
        }
    }
}
