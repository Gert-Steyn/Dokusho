package eu.kanade.presentation.more.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.PreviewLightDark
import eu.kanade.presentation.theme.TachiyomiPreviewTheme
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GSecondaryButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

internal class GuidesStep(
    private val onRestoreBackup: () -> Unit,
) : OnboardingStep {

    override val isComplete: Boolean = true

    @Composable
    override fun Content() {
        val handler = LocalUriHandler.current

        Column(
            modifier = Modifier.padding(GSpace.s5),
            verticalArrangement = Arrangement.spacedBy(GSpace.s3),
        ) {
            Text(stringResource(MR.strings.onboarding_guides_new_user, stringResource(MR.strings.app_name)))
            GSecondaryButton(
                text = stringResource(MR.strings.getting_started_guide),
                onClick = { handler.openUri(GETTING_STARTED_URL) },
                modifier = Modifier.fillMaxWidth(),
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = GSpace.s3),
                color = Graphite.colors.borderSubtle,
            )

            Text(stringResource(MR.strings.onboarding_guides_returning_user, stringResource(MR.strings.app_name)))
            GSecondaryButton(
                text = stringResource(MR.strings.pref_restore_backup),
                onClick = onRestoreBackup,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

const val GETTING_STARTED_URL = "https://mihon.app/docs/guides/getting-started"

@PreviewLightDark
@Composable
private fun GuidesStepPreview() {
    TachiyomiPreviewTheme {
        GuidesStep(
            onRestoreBackup = {},
        ).Content()
    }
}
