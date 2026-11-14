package eu.kanade.presentation.track

import android.content.ClipData
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import eu.kanade.presentation.components.DropdownMenu
import eu.kanade.presentation.manga.components.MangaCover
import eu.kanade.presentation.theme.TachiyomiPreviewTheme
import eu.kanade.tachiyomi.data.track.model.TrackSearch
import eu.kanade.tachiyomi.util.system.openInBrowser
import kotlinx.coroutines.launch
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.automirrored.ArrowLeft
import mihon.icons.lucide.icons.CircleCheck
import mihon.icons.lucide.icons.EyeOff
import mihon.icons.lucide.icons.X
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.ScrollbarLazyColumn
import tachiyomi.presentation.core.components.graphite.GIconButton
import tachiyomi.presentation.core.components.graphite.GPrimaryButton
import tachiyomi.presentation.core.components.graphite.GSecondaryButton
import tachiyomi.presentation.core.components.material.Scaffold
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.screens.EmptyScreen
import tachiyomi.presentation.core.screens.LoadingScreen
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite
import tachiyomi.presentation.core.util.plus
import tachiyomi.presentation.core.util.runOnEnterKeyPressed

@Composable
fun TrackerSearch(
    state: TextFieldState,
    onDispatchQuery: () -> Unit,
    queryResult: Result<List<TrackSearch>>?,
    selected: TrackSearch?,
    onSelectedChange: (TrackSearch) -> Unit,
    onConfirmSelection: (private: Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    supportsPrivateTracking: Boolean,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val dispatchQueryAndClearFocus: () -> Unit = {
        onDispatchQuery()
        focusManager.clearFocus()
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    navigationIcon = {
                        GIconButton(
                            icon = Lucide.AutoMirrored.ArrowLeft,
                            contentDescription = null,
                            onClick = onDismissRequest,
                        )
                    },
                    title = {
                        BasicTextField(
                            state = state,
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester)
                                .runOnEnterKeyPressed(action = dispatchQueryAndClearFocus),
                            textStyle = Graphite.type.body
                                .copy(color = Graphite.colors.text),
                            lineLimits = TextFieldLineLimits.SingleLine,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            onKeyboardAction = { dispatchQueryAndClearFocus() },
                            cursorBrush = SolidColor(Graphite.colors.accent),
                            decorator = {
                                if (state.text.isEmpty()) {
                                    Text(
                                        text = stringResource(MR.strings.action_search_hint),
                                        color = Graphite.colors.textSecondary,
                                        style = Graphite.type.body,
                                    )
                                }
                                it()
                            },
                        )
                    },
                    actions = {
                        if (state.text.isNotEmpty()) {
                            GIconButton(
                                icon = Lucide.Icons.X,
                                contentDescription = null,
                                onClick = {
                                    state.clearText()
                                    focusRequester.requestFocus()
                                },
                            )
                        }
                    },
                )
                HorizontalDivider()
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = selected != null,
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = slideOutVertically { it / 2 } + fadeOut(),
            ) {
                Row(
                    modifier = Modifier
                        .padding(GSpace.s3)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(GSpace.s3),
                ) {
                    GPrimaryButton(
                        text = stringResource(MR.strings.action_track),
                        onClick = { onConfirmSelection(false) },
                        modifier = Modifier.weight(1f),
                    )
                    if (supportsPrivateTracking) {
                        GSecondaryButton(
                            text = stringResource(MR.strings.action_toggle_private_on),
                            onClick = { onConfirmSelection(true) },
                            icon = Lucide.Icons.EyeOff,
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        if (queryResult == null) {
            LoadingScreen(modifier = Modifier.padding(innerPadding))
        } else {
            val availableTracks = queryResult.getOrNull()
            if (availableTracks != null) {
                if (availableTracks.isEmpty()) {
                    EmptyScreen(
                        modifier = Modifier.padding(innerPadding),
                        stringRes = MR.strings.no_results_found,
                    )
                } else {
                    ScrollbarLazyColumn(
                        contentPadding = innerPadding + PaddingValues(vertical = GSpace.s4),
                        verticalArrangement = Arrangement.spacedBy(GSpace.s4),
                    ) {
                        items(
                            items = availableTracks,
                            key = { it.hashCode() },
                        ) {
                            SearchResultItem(
                                trackSearch = it,
                                selected = it == selected,
                                onClick = { onSelectedChange(it) },
                            )
                        }
                    }
                }
            } else {
                EmptyScreen(
                    modifier = Modifier.padding(innerPadding),
                    message = queryResult.exceptionOrNull()?.message
                        ?: stringResource(MR.strings.unknown_error),
                )
            }
        }
    }
}

@Composable
private fun SearchResultItem(
    trackSearch: TrackSearch,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    val clipboard: Clipboard = LocalClipboard.current
    val focusManager = LocalFocusManager.current
    val locale = LocalLocale.current
    val type = trackSearch.publishing_type.toLowerCase(locale).capitalize(locale)
    val status = trackSearch.publishing_status.toLowerCase(locale).capitalize(locale)
    val description = trackSearch.summary.trim()
    val shape = GRadius.card
    val borderColor = if (selected) Graphite.colors.accent else Graphite.colors.borderSubtle
    var dropDownMenuExpanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = GSpace.s4)
            .clip(shape)
            .background(Graphite.colors.canvas)
            .border(
                width = GSize.border,
                color = borderColor,
                shape = shape,
            )
            .combinedClickable(
                onLongClick = { dropDownMenuExpanded = true },
                onClick = {
                    focusManager.clearFocus()
                    onClick()
                },
            )
            .padding(GSpace.s4),
    ) {
        if (selected) {
            Icon(
                imageVector = Lucide.Icons.CircleCheck,
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopEnd),
                tint = Graphite.colors.accent,
            )
        }
        Column {
            Row {
                MangaCover.Book(
                    data = trackSearch.cover_url,
                    modifier = Modifier.height(96.dp),
                )
                Spacer(modifier = Modifier.width(GSpace.s4))
                Column {
                    Text(
                        text = trackSearch.title,
                        modifier = Modifier.padding(end = GSpace.s7),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = Graphite.type.heading,
                    )
                    SearchResultItemDropDownMenu(
                        expanded = dropDownMenuExpanded,
                        onCollapseMenu = { dropDownMenuExpanded = false },
                        onCopyName = {
                            scope.launch {
                                val clipEntry = ClipData.newPlainText(
                                    trackSearch.title,
                                    trackSearch.title,
                                ).toClipEntry()
                                clipboard.setClipEntry(clipEntry)
                            }
                        },
                        onOpenInBrowser = {
                            val url = trackSearch.tracking_url
                            if (url.isNotBlank()) {
                                context.openInBrowser(url)
                            }
                        },
                    )
                    if (trackSearch.authors.isNotEmpty() || trackSearch.artists.isNotEmpty()) {
                        Text(
                            text = (trackSearch.authors + trackSearch.artists).distinct().joinToString(),
                            color = Graphite.colors.textSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = Graphite.type.small,
                        )
                    }
                    if (type.isNotBlank()) {
                        SearchResultItemDetails(
                            title = stringResource(MR.strings.track_type),
                            text = type,
                        )
                    }
                    if (trackSearch.start_date.isNotBlank()) {
                        SearchResultItemDetails(
                            title = stringResource(MR.strings.label_started),
                            text = trackSearch.start_date,
                        )
                    }
                    if (status.isNotBlank()) {
                        SearchResultItemDetails(
                            title = stringResource(MR.strings.track_status),
                            text = status,
                        )
                    }
                    if (trackSearch.score != -1.0) {
                        SearchResultItemDetails(
                            title = stringResource(MR.strings.score),
                            text = trackSearch.score.toString(),
                        )
                    }
                }
            }
            if (description.isNotBlank()) {
                Text(
                    text = description,
                    modifier = Modifier.paddingFromBaseline(top = GSpace.s6),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    style = Graphite.type.small,
                    color = Graphite.colors.textSecondary,
                )
            }
        }
    }
}

@Composable
private fun SearchResultItemDropDownMenu(
    expanded: Boolean,
    onCollapseMenu: () -> Unit,
    onCopyName: () -> Unit,
    onOpenInBrowser: () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onCollapseMenu,
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(MR.strings.action_copy_to_clipboard)) },
            onClick = {
                onCopyName()
                onCollapseMenu()
            },
        )
        DropdownMenuItem(
            text = { Text(stringResource(MR.strings.action_open_in_browser)) },
            onClick = {
                onOpenInBrowser()
            },
        )
    }
}

@Composable
private fun SearchResultItemDetails(
    title: String,
    text: String,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(GSpace.s2)) {
        Text(
            text = title,
            maxLines = 1,
            style = Graphite.type.label,
        )
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = Graphite.type.body,
            color = Graphite.colors.textSecondary,
        )
    }
}

@PreviewLightDark
@Composable
private fun TrackerSearchPreviews(
    @PreviewParameter(TrackerSearchPreviewProvider::class)
    content: @Composable () -> Unit,
) {
    TachiyomiPreviewTheme { content() }
}
