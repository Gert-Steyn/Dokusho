package tachiyomi.presentation.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.ChevronDown
import mihon.icons.lucide.icons.ChevronUp
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun CollapsibleBox(
    heading: String,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(horizontal = GSize.pagePad, vertical = GSpace.s4),
        ) {
            Text(
                text = heading,
                style = Graphite.type.heading,
                color = Graphite.colors.text,
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = if (expanded) {
                    Lucide.Icons.ChevronUp
                } else {
                    Lucide.Icons.ChevronDown
                },
                contentDescription = null,
                tint = Graphite.colors.textMuted,
                modifier = Modifier.size(GSize.icon),
            )
        }

        AnimatedVisibility(visible = expanded) {
            content()
        }
    }
}
