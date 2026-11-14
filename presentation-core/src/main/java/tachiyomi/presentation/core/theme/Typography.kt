package tachiyomi.presentation.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

/** Group header style: caption type in muted text, sentence case. */
val Typography.header: TextStyle
    @Composable
    get() = Graphite.type.caption.copy(color = Graphite.colors.textMuted)
