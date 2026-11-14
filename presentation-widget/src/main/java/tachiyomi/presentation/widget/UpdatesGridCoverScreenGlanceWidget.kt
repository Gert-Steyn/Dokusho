package tachiyomi.presentation.widget

import androidx.compose.ui.unit.dp
import androidx.glance.ImageProvider
import androidx.glance.unit.ColorProvider
import tachiyomi.presentation.core.theme.GraphiteDark

class UpdatesGridCoverScreenGlanceWidget : BaseUpdatesGridGlanceWidget() {
    override val foreground = ColorProvider(GraphiteDark.onAccent)
    override val background = ImageProvider(R.drawable.appwidget_coverscreen_background)
    override val topPadding = 0.dp
    override val bottomPadding = 24.dp
}
