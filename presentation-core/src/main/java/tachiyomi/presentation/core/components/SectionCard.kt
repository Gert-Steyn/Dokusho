package tachiyomi.presentation.core.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.StringResource
import tachiyomi.presentation.core.components.graphite.GCard
import tachiyomi.presentation.core.components.material.padding
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun LazyItemScope.SectionCard(
    titleRes: StringResource? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (titleRes != null) {
        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.padding.extraLarge),
            text = stringResource(titleRes),
            style = Graphite.type.caption,
            color = Graphite.colors.textMuted,
        )
    }

    GCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.padding.medium,
                vertical = MaterialTheme.padding.small,
            ),
        content = content,
    )
}
