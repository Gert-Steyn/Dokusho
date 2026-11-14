package eu.kanade.presentation.more.settings.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import eu.kanade.domain.ui.model.ThemeMode
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

private val options = mapOf(
    ThemeMode.SYSTEM to MR.strings.theme_system,
    ThemeMode.LIGHT to MR.strings.theme_light,
    ThemeMode.DARK to MR.strings.theme_dark,
)

@Composable
internal fun AppThemeModePreferenceWidget(
    value: ThemeMode,
    onItemClick: (ThemeMode) -> Unit,
) {
    BasePreferenceWidget(
        subcomponent = {
            MultiChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PrefsHorizontalPadding)
                    .border(GSize.border, Graphite.colors.borderSubtle, GRadius.control)
                    .padding(GSpace.s1),
            ) {
                options.onEachIndexed { index, (mode, labelRes) ->
                    SegmentedButton(
                        checked = mode == value,
                        onCheckedChange = { onItemClick(mode) },
                        shape = GRadius.tag,
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = Graphite.colors.hover,
                            activeContentColor = Graphite.colors.text,
                            activeBorderColor = Color.Transparent,
                            inactiveContainerColor = Graphite.colors.surface,
                            inactiveContentColor = Graphite.colors.textSecondary,
                            inactiveBorderColor = Color.Transparent,
                        ),
                    ) {
                        Text(stringResource(labelRes), style = Graphite.type.label)
                    }
                }
            }
        },
    )
}
