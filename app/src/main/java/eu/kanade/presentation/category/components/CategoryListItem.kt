package eu.kanade.presentation.category.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.GripHorizontal
import mihon.icons.lucide.icons.Pencil
import mihon.icons.lucide.icons.Trash2
import sh.calvin.reorderable.ReorderableCollectionItemScope
import tachiyomi.domain.category.model.Category
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GIconButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite
import tachiyomi.presentation.core.theme.graphiteCardBorder

@Composable
fun ReorderableCollectionItemScope.CategoryListItem(
    category: Category,
    onRename: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = GRadius.card,
        color = Graphite.colors.surface,
        border = graphiteCardBorder(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onRename)
                .padding(vertical = GSpace.s3)
                .padding(
                    start = GSpace.s3,
                    end = GSpace.s5,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Lucide.Icons.GripHorizontal,
                contentDescription = null,
                tint = Graphite.colors.textMuted,
                modifier = Modifier
                    .padding(GSpace.s5)
                    .draggableHandle(),
            )
            Text(
                text = category.name,
                modifier = Modifier.weight(1f),
            )
            GIconButton(
                icon = Lucide.Icons.Pencil,
                contentDescription = stringResource(MR.strings.action_rename_category),
                onClick = onRename,
            )
            GIconButton(
                icon = Lucide.Icons.Trash2,
                contentDescription = stringResource(MR.strings.action_delete),
                onClick = onDelete,
            )
        }
    }
}
