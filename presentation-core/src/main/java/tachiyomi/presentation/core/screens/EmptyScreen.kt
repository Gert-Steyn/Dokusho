package tachiyomi.presentation.core.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import dev.icerock.moko.resources.StringResource
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.Inbox
import tachiyomi.presentation.core.components.graphite.GSecondaryButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

data class EmptyScreenAction(
    val stringRes: StringResource,
    val icon: ImageVector,
    val onClick: () -> Unit,
)

@Composable
fun EmptyScreen(
    stringRes: StringResource,
    modifier: Modifier = Modifier,
    actions: List<EmptyScreenAction>? = null,
) {
    EmptyScreen(
        message = stringResource(stringRes),
        modifier = modifier,
        actions = actions,
    )
}

@Composable
fun EmptyScreen(
    message: String,
    modifier: Modifier = Modifier,
    actions: List<EmptyScreenAction>? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = GSpace.s6),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.widthIn(max = 320.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Lucide.Icons.Inbox,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Graphite.colors.textMuted,
            )
            Text(
                text = message,
                modifier = Modifier.padding(top = GSpace.s4),
                style = Graphite.type.heading,
                color = Graphite.colors.text,
                textAlign = TextAlign.Center,
            )

            if (!actions.isNullOrEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = GSpace.s5),
                    horizontalArrangement = Arrangement.spacedBy(GSpace.s3, Alignment.CenterHorizontally),
                ) {
                    actions.fastForEach {
                        GSecondaryButton(
                            text = stringResource(it.stringRes),
                            onClick = it.onClick,
                            icon = it.icon,
                        )
                    }
                }
            }
        }
    }
}
