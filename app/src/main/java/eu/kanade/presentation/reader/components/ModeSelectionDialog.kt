package eu.kanade.presentation.reader.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import eu.kanade.presentation.theme.TachiyomiPreviewTheme
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.Check
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.SettingsItemsPaddings
import tachiyomi.presentation.core.components.graphite.GPrimaryButton
import tachiyomi.presentation.core.components.graphite.GSecondaryButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GSpace

@Composable
fun ModeSelectionDialog(
    onApply: () -> Unit,
    onUseDefault: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Box(modifier = Modifier.padding(vertical = GSpace.s5)) {
        Column {
            content()

            Row(
                modifier = Modifier.padding(
                    horizontal = SettingsItemsPaddings.Horizontal,
                ),
            ) {
                onUseDefault?.let {
                    GSecondaryButton(
                        text = stringResource(MR.strings.action_revert_to_default),
                        onClick = it,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                GPrimaryButton(
                    text = stringResource(MR.strings.action_apply),
                    onClick = onApply,
                    icon = Lucide.Icons.Check,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    TachiyomiPreviewTheme {
        Surface {
            Column {
                ModeSelectionDialog(
                    onApply = {},
                    onUseDefault = {},
                ) {
                    Text("Dummy content")
                }

                ModeSelectionDialog(
                    onApply = {},
                ) {
                    Text("Dummy content without default")
                }
            }
        }
    }
}
