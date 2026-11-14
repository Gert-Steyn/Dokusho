package eu.kanade.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import tachiyomi.presentation.core.theme.GraphiteTheme
import tachiyomi.presentation.core.theme.Inter
import tachiyomi.presentation.core.theme.JetBrainsMono

@Composable
fun TachiyomiTheme(
    content: @Composable () -> Unit,
) {
    GraphiteTheme(
        sans = Inter,
        mono = JetBrainsMono,
        darkTheme = isSystemInDarkTheme(),
        content = content,
    )
}

@Composable
fun TachiyomiPreviewTheme(
    content: @Composable () -> Unit,
) = TachiyomiTheme(content)
