package tachiyomi.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tachiyomi.presentation.core.components.graphite.GPrimaryButton
import tachiyomi.presentation.core.theme.GSpace

@Composable
fun LazyColumnWithAction(
    contentPadding: PaddingValues,
    actionLabel: String,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier,
    actionEnabled: Boolean = true,
    content: LazyListScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            content = content,
        )

        HorizontalDivider()

        GPrimaryButton(
            text = actionLabel,
            onClick = onClickAction,
            enabled = actionEnabled,
            modifier = Modifier
                .padding(horizontal = GSpace.s5, vertical = GSpace.s3)
                .fillMaxWidth(),
        )
    }
}
