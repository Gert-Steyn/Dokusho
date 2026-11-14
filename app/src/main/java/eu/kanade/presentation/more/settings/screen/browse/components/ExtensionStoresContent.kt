package eu.kanade.presentation.more.settings.screen.browse.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mihon.domain.extension.model.ExtensionStore
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.automirrored.Tag
import mihon.icons.lucide.icons.Copy
import mihon.icons.lucide.icons.Globe
import mihon.icons.lucide.icons.Trash2
import mihon.icons.simpleicons.Discord
import mihon.icons.simpleicons.SimpleIcons
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GCard
import tachiyomi.presentation.core.components.graphite.GIconButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun ExtensionStoresContent(
    repos: List<ExtensionStore>,
    lazyListState: LazyListState,
    paddingValues: PaddingValues,
    onCopy: (ExtensionStore) -> Unit,
    onOpenWebsite: (ExtensionStore) -> Unit,
    onOpenDiscord: (ExtensionStore) -> Unit,
    onClickDelete: (ExtensionStore) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        state = lazyListState,
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(GSpace.s3),
        modifier = modifier,
    ) {
        repos.forEach {
            item {
                ExtensionStoresListItem(
                    modifier = Modifier.animateItem(),
                    store = it,
                    onOpenWebsite = { onOpenWebsite(it) },
                    onOpenDiscord = { onOpenDiscord(it) },
                    onCopy = { onCopy(it) },
                    onDelete = { onClickDelete(it) },
                )
            }
        }
    }
}

@Composable
private fun ExtensionStoresListItem(
    store: ExtensionStore,
    onOpenWebsite: () -> Unit,
    onOpenDiscord: () -> Unit,
    onCopy: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GCard(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Lucide.AutoMirrored.Tag,
                contentDescription = null,
                tint = Graphite.colors.textMuted,
            )
            Text(
                text = store.name,
                modifier = Modifier.padding(start = GSpace.s5),
                style = Graphite.type.heading,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            GIconButton(
                icon = Lucide.Icons.Globe,
                contentDescription = stringResource(MR.strings.action_open_in_browser),
                onClick = onOpenWebsite,
            )

            if (store.contact.discord != null) {
                GIconButton(
                    icon = SimpleIcons.Discord,
                    contentDescription = null,
                    onClick = onOpenDiscord,
                )
            }

            GIconButton(
                icon = Lucide.Icons.Copy,
                contentDescription = stringResource(MR.strings.action_copy_to_clipboard),
                onClick = onCopy,
            )

            GIconButton(
                icon = Lucide.Icons.Trash2,
                contentDescription = stringResource(MR.strings.action_delete),
                onClick = onDelete,
            )
        }
    }
}
