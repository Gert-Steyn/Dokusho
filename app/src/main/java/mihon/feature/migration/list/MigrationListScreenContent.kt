package mihon.feature.migration.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import eu.kanade.presentation.components.AppBar
import eu.kanade.presentation.components.AppBarActions
import eu.kanade.presentation.manga.components.MangaCover
import eu.kanade.presentation.util.formatChapterNumber
import eu.kanade.presentation.util.rememberResourceBitmapPainter
import eu.kanade.tachiyomi.R
import mihon.feature.migration.list.models.MigratingManga
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.automirrored.ArrowRight
import mihon.icons.lucide.icons.Check
import mihon.icons.lucide.icons.CheckCheck
import mihon.icons.lucide.icons.Copy
import mihon.icons.lucide.icons.EllipsisVertical
import mihon.icons.lucide.icons.Files
import mihon.icons.lucide.icons.X
import tachiyomi.domain.manga.model.Manga
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.Badge
import tachiyomi.presentation.core.components.BadgeGroup
import tachiyomi.presentation.core.components.FastScrollLazyColumn
import tachiyomi.presentation.core.components.graphite.GIconButton
import tachiyomi.presentation.core.components.material.Scaffold
import tachiyomi.presentation.core.components.material.topSmallPaddingValues
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite
import tachiyomi.presentation.core.util.plus

@Composable
fun MigrationListScreenContent(
    items: List<MigratingManga>,
    migrationComplete: Boolean,
    finishedCount: Int,
    onItemClick: (Manga) -> Unit,
    onSearchManually: (MigratingManga) -> Unit,
    onSkip: (Long) -> Unit,
    onMigrate: (Long) -> Unit,
    onCopy: (Long) -> Unit,
    openMigrationDialog: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = { scrollBehavior ->
            AppBar(
                title = if (items.isNotEmpty()) {
                    stringResource(MR.strings.migrationListScreenTitleWithProgress, finishedCount, items.size)
                } else {
                    stringResource(MR.strings.migrationListScreenTitle)
                },
                actions = {
                    AppBarActions(
                        listOf(
                            AppBar.Action(
                                title = stringResource(MR.strings.migrationListScreen_copyActionLabel),
                                icon = if (items.size ==
                                    1
                                ) {
                                    Lucide.Icons.Copy
                                } else {
                                    Lucide.Icons.Files
                                },
                                onClick = { openMigrationDialog(true) },
                                enabled = migrationComplete,
                            ),
                            AppBar.Action(
                                title = stringResource(MR.strings.migrationListScreen_migrateActionLabel),
                                icon = if (items.size ==
                                    1
                                ) {
                                    Lucide.Icons.Check
                                } else {
                                    Lucide.Icons.CheckCheck
                                },
                                onClick = { openMigrationDialog(false) },
                                enabled = migrationComplete,
                            ),
                        ),
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { contentPadding ->
        FastScrollLazyColumn(contentPadding = contentPadding + topSmallPaddingValues) {
            items(items, key = { it.manga.id }) { item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .animateItem()
                        .padding(
                            start = GSpace.s5,
                            end = GSpace.s3,
                        )
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MigrationListItem(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.Top)
                            .fillMaxHeight(),
                        manga = item.manga,
                        source = item.source,
                        chapterCount = item.chapterCount,
                        latestChapter = item.latestChapter,
                        onClick = { onItemClick(item.manga) },
                    )

                    Icon(
                        imageVector = Lucide.AutoMirrored.ArrowRight,
                        contentDescription = null,
                        modifier = Modifier.weight(0.2f),
                    )

                    val result by item.searchResult.collectAsState()
                    MigrationListItemResult(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.Top)
                            .fillMaxHeight(),
                        result = result,
                        onItemClick = onItemClick,
                    )

                    MigrationListItemAction(
                        modifier = Modifier.weight(0.2f),
                        result = result,
                        onSearchManually = { onSearchManually(item) },
                        onSkip = { onSkip(item.manga.id) },
                        onMigrate = { onMigrate(item.manga.id) },
                        onCopy = { onCopy(item.manga.id) },
                    )
                }
            }
        }
    }
}

@Composable
fun MigrationListItem(
    modifier: Modifier,
    manga: Manga,
    source: String,
    chapterCount: Int,
    latestChapter: Double?,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .widthIn(max = 150.dp)
            .fillMaxWidth()
            .clip(GRadius.control)
            .clickable(onClick = onClick)
            .padding(GSpace.s2),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(MangaCover.Book.ratio),
        ) {
            MangaCover.Book(
                modifier = Modifier.fillMaxWidth(),
                data = manga,
            )
            Box(
                modifier = Modifier
                    .clip(GRadius.tag)
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            1f to Graphite.colors.canvas,
                        ),
                    )
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            )
            Text(
                modifier = Modifier
                    .padding(GSpace.s3)
                    .align(Alignment.BottomStart),
                text = manga.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = Graphite.type.caption,
            )
            BadgeGroup(modifier = Modifier.padding(GSpace.s2)) {
                Badge(text = "$chapterCount")
            }
        }

        Column(
            modifier = Modifier
                .padding(GSpace.s2),
            verticalArrangement = Arrangement.spacedBy(GSpace.s1),
        ) {
            Text(
                text = source,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Graphite.type.label,
            )
            val formattedLatestChapters = remember(latestChapter) {
                latestChapter?.let(::formatChapterNumber)
            }
            Text(
                text = stringResource(
                    MR.strings.migrationListScreen_latestChapterLabel,
                    formattedLatestChapters ?: stringResource(MR.strings.migrationListScreen_unknownLatestChapter),
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Graphite.type.body,
            )
        }
    }
}

@Composable
fun MigrationListItemResult(
    modifier: Modifier,
    result: MigratingManga.SearchResult,
    onItemClick: (Manga) -> Unit,
) {
    Box(modifier.height(IntrinsicSize.Min)) {
        when (result) {
            MigratingManga.SearchResult.Searching -> {
                Box(
                    modifier = Modifier
                        .widthIn(max = 150.dp)
                        .fillMaxSize()
                        .aspectRatio(MangaCover.Book.ratio),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            MigratingManga.SearchResult.NotFound -> {
                Column(
                    Modifier
                        .widthIn(max = 150.dp)
                        .fillMaxSize()
                        .padding(GSpace.s2),
                ) {
                    Image(
                        painter = rememberResourceBitmapPainter(id = R.drawable.cover_error),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(MangaCover.Book.ratio)
                            .clip(GRadius.tag),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = stringResource(MR.strings.migrationListScreen_noMatchFoundText),
                        modifier = Modifier.padding(GSpace.s2),
                        style = Graphite.type.label,
                    )
                }
            }
            is MigratingManga.SearchResult.Success -> {
                MigrationListItem(
                    modifier = Modifier.fillMaxSize(),
                    manga = result.manga,
                    source = result.source,
                    chapterCount = result.chapterCount,
                    latestChapter = result.latestChapter,
                    onClick = { onItemClick(result.manga) },
                )
            }
        }
    }
}

@Composable
private fun MigrationListItemAction(
    modifier: Modifier,
    result: MigratingManga.SearchResult,
    onSearchManually: () -> Unit,
    onSkip: () -> Unit,
    onMigrate: () -> Unit,
    onCopy: () -> Unit,
) {
    var menuExpanded by rememberSaveable { mutableStateOf(false) }
    val closeMenu = { menuExpanded = false }
    Box(modifier) {
        when (result) {
            MigratingManga.SearchResult.Searching -> {
                GIconButton(
                    icon = Lucide.Icons.X,
                    contentDescription = null,
                    onClick = onSkip,
                )
            }
            MigratingManga.SearchResult.NotFound, is MigratingManga.SearchResult.Success -> {
                GIconButton(
                    icon = Lucide.Icons.EllipsisVertical,
                    contentDescription = null,
                    onClick = { menuExpanded = true },
                )
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = closeMenu,
                    offset = DpOffset(8.dp, (-56).dp),
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(MR.strings.migrationListScreen_searchManuallyActionLabel)) },
                        onClick = {
                            closeMenu()
                            onSearchManually()
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(MR.strings.migrationListScreen_skipActionLabel)) },
                        onClick = {
                            closeMenu()
                            onSkip()
                        },
                    )
                    if (result is MigratingManga.SearchResult.Success) {
                        DropdownMenuItem(
                            text = { Text(stringResource(MR.strings.migrationListScreen_migrateNowActionLabel)) },
                            onClick = {
                                closeMenu()
                                onMigrate()
                            },
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(MR.strings.migrationListScreen_copyNowActionLabel)) },
                            onClick = {
                                closeMenu()
                                onCopy()
                            },
                        )
                    }
                }
            }
        }
    }
}
