package eu.kanade.tachiyomi.ui.reader.viewer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import eu.kanade.presentation.reader.ChapterTransition
import eu.kanade.presentation.reader.reportPress
import eu.kanade.tachiyomi.ui.reader.model.ChapterTransition
import eu.kanade.tachiyomi.ui.reader.model.ReaderChapter
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GPrimaryButton
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun ReaderTransitionPage(
    transition: ChapterTransition,
    downloadState: TransitionDownloadState?,
    onRetry: (ReaderChapter) -> Unit,
    onButtonPressChange: (pressed: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    statusPadding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (downloadState != null) {
            CompositionLocalProvider(
                LocalTextStyle provides Graphite.type.small,
                LocalContentColor provides Graphite.colors.text,
            ) {
                ChapterTransition(
                    transition,
                    downloadState.currChapterDownloaded,
                    downloadState.goingToChapterDownloaded,
                )
            }
        }

        val toChapter = transition.to ?: return@Column
        val state by toChapter.stateFlow.collectAsState()
        when (val state = state) {
            is ReaderChapter.State.Loading -> Column(
                modifier = Modifier.padding(statusPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator()
                Text(
                    text = stringResource(MR.strings.transition_pages_loading),
                    color = Graphite.colors.text,
                    style = Graphite.type.body,
                )
            }
            is ReaderChapter.State.Error -> Column(
                modifier = Modifier.padding(statusPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(MR.strings.transition_pages_error, state.error.message ?: ""),
                    color = Graphite.colors.text,
                    style = Graphite.type.body,
                )
                GPrimaryButton(
                    text = stringResource(MR.strings.action_retry),
                    onClick = { onRetry(toChapter) },
                    modifier = Modifier.reportPress(onButtonPressChange),
                )
            }
            is ReaderChapter.State.Wait, is ReaderChapter.State.Loaded -> {}
        }
    }
}
