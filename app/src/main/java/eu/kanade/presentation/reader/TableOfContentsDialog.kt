package eu.kanade.presentation.reader

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import eu.kanade.presentation.components.AdaptiveSheet
import eu.kanade.tachiyomi.ui.reader.loader.TextTocEntry
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun TableOfContentsDialog(
    entries: List<TextTocEntry>,
    onDismissRequest: () -> Unit,
    onClickEntry: (TextTocEntry) -> Unit,
) {
    AdaptiveSheet(onDismissRequest = onDismissRequest) {
        Column {
            Text(
                text = stringResource(MR.strings.reader_toc),
                style = Graphite.type.heading,
                color = Graphite.colors.text,
                modifier = Modifier.padding(horizontal = GSize.pagePad, vertical = GSpace.s5),
            )
            LazyColumn {
                items(entries) { entry ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = GSize.row)
                            .clickable { onClickEntry(entry) }
                            .padding(start = GSize.pagePad + GSpace.s5 * entry.depth, end = GSize.pagePad),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Text(
                            text = entry.title,
                            style = Graphite.type.body,
                            color = Graphite.colors.text,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}
