package eu.kanade.presentation.manga

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMaxOfOrNull
import coil3.request.ImageRequest
import coil3.request.crossfade
import eu.kanade.presentation.components.AdaptiveSheet
import eu.kanade.presentation.components.TabbedDialogPaddings
import eu.kanade.presentation.manga.components.MangaCover
import eu.kanade.presentation.more.settings.widget.TextPreferenceWidget
import eu.kanade.tachiyomi.source.Source
import eu.kanade.tachiyomi.source.model.SManga
import mihon.app.di.appGraph
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.Ban
import mihon.icons.lucide.icons.Brush
import mihon.icons.lucide.icons.Check
import mihon.icons.lucide.icons.CheckCheck
import mihon.icons.lucide.icons.Clock
import mihon.icons.lucide.icons.DollarSign
import mihon.icons.lucide.icons.Pause
import mihon.icons.lucide.icons.Plus
import mihon.icons.lucide.icons.TriangleAlert
import mihon.icons.lucide.icons.User
import mihon.icons.lucide.icons.X
import tachiyomi.domain.manga.model.Manga
import tachiyomi.domain.manga.model.MangaWithChapterCount
import tachiyomi.domain.source.model.StubSource
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.Badge
import tachiyomi.presentation.core.components.BadgeGroup
import tachiyomi.presentation.core.components.graphite.GGhostButton
import tachiyomi.presentation.core.i18n.pluralStringResource
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GRadius
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite
import tachiyomi.presentation.core.theme.GraphiteType

@Composable
fun DuplicateMangaDialog(
    duplicates: List<MangaWithChapterCount>,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    onOpenManga: (manga: Manga) -> Unit,
    onMigrate: (manga: Manga) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val sourceManager = remember { context.appGraph.sourceManager }
    val horizontalPadding = PaddingValues(horizontal = TabbedDialogPaddings.Horizontal)
    val horizontalPaddingModifier = Modifier.padding(horizontalPadding)

    AdaptiveSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = TabbedDialogPaddings.Vertical)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(GSpace.s5),
        ) {
            Text(
                text = stringResource(MR.strings.possible_duplicates_title),
                style = Graphite.type.title,
                modifier = Modifier
                    .then(horizontalPaddingModifier)
                    .padding(top = GSpace.s3),
            )

            Text(
                text = stringResource(MR.strings.possible_duplicates_summary),
                style = Graphite.type.body,
                modifier = Modifier.then(horizontalPaddingModifier),
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(GSpace.s3),
                modifier = Modifier.height(getMaximumMangaCardHeight(duplicates)),
                contentPadding = horizontalPadding,
            ) {
                items(
                    items = duplicates,
                    key = { it.manga.id },
                ) {
                    DuplicateMangaListItem(
                        duplicate = it,
                        getSource = { sourceManager.getOrStub(it.manga.source) },
                        onMigrate = { onMigrate(it.manga) },
                        onDismissRequest = onDismissRequest,
                        onOpenManga = { onOpenManga(it.manga) },
                    )
                }
            }

            Column(modifier = horizontalPaddingModifier) {
                HorizontalDivider()

                TextPreferenceWidget(
                    title = stringResource(MR.strings.action_add_anyway),
                    icon = Lucide.Icons.Plus,
                    onPreferenceClick = {
                        onDismissRequest()
                        onConfirm()
                    },
                    modifier = Modifier.clip(GRadius.control),
                )
            }

            GGhostButton(
                text = stringResource(MR.strings.action_cancel),
                onClick = onDismissRequest,
                modifier = Modifier
                    .then(horizontalPaddingModifier)
                    .padding(bottom = GSpace.s5)
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun DuplicateMangaListItem(
    duplicate: MangaWithChapterCount,
    getSource: suspend () -> Source,
    onDismissRequest: () -> Unit,
    onOpenManga: () -> Unit,
    onMigrate: () -> Unit,
) {
    val source by produceState<Source?>(initialValue = null) { value = getSource() }
    val manga = duplicate.manga
    Column(
        modifier = Modifier
            .width(MangaCardWidth)
            .clip(GRadius.card)
            .background(Graphite.colors.canvas)
            .combinedClickable(
                onLongClick = { onOpenManga() },
                onClick = {
                    onDismissRequest()
                    onMigrate()
                },
            )
            .padding(GSpace.s3),
    ) {
        Box {
            MangaCover.Book(
                data = ImageRequest.Builder(LocalContext.current)
                    .data(manga)
                    .crossfade(true)
                    .build(),
                modifier = Modifier.fillMaxWidth(),
            )
            BadgeGroup(
                modifier = Modifier
                    .padding(GSpace.s2)
                    .align(Alignment.TopStart),
            ) {
                Badge(
                    color = Graphite.colors.accent,
                    textColor = Graphite.colors.onAccent,
                    text = pluralStringResource(
                        MR.plurals.manga_num_chapters,
                        duplicate.chapterCount.toInt(),
                        duplicate.chapterCount,
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(GSpace.s2))

        Text(
            text = manga.title,
            style = Graphite.type.label,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )

        if (!manga.author.isNullOrBlank()) {
            MangaDetailRow(
                text = manga.author!!,
                iconImageVector = Lucide.Icons.User,
                maxLines = 2,
            )
        }

        if (!manga.artist.isNullOrBlank() && manga.author != manga.artist) {
            MangaDetailRow(
                text = manga.artist!!,
                iconImageVector = Lucide.Icons.Brush,
                maxLines = 2,
            )
        }

        MangaDetailRow(
            text = when (manga.status) {
                SManga.ONGOING.toLong() -> stringResource(MR.strings.ongoing)
                SManga.COMPLETED.toLong() -> stringResource(MR.strings.completed)
                SManga.LICENSED.toLong() -> stringResource(MR.strings.licensed)
                SManga.PUBLISHING_FINISHED.toLong() -> stringResource(MR.strings.publishing_finished)
                SManga.CANCELLED.toLong() -> stringResource(MR.strings.cancelled)
                SManga.ON_HIATUS.toLong() -> stringResource(MR.strings.on_hiatus)
                else -> stringResource(MR.strings.unknown)
            },
            iconImageVector = when (manga.status) {
                SManga.ONGOING.toLong() -> Lucide.Icons.Clock
                SManga.COMPLETED.toLong() -> Lucide.Icons.CheckCheck
                SManga.LICENSED.toLong() -> Lucide.Icons.DollarSign
                SManga.PUBLISHING_FINISHED.toLong() -> Lucide.Icons.Check
                SManga.CANCELLED.toLong() -> Lucide.Icons.X
                SManga.ON_HIATUS.toLong() -> Lucide.Icons.Pause
                else -> Lucide.Icons.Ban
            },
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            if (source is StubSource) {
                Icon(
                    imageVector = Lucide.Icons.TriangleAlert,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Graphite.colors.danger,
                )
            }
            Text(
                text = source?.name.orEmpty(),
                style = Graphite.type.caption,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun MangaDetailRow(
    text: String,
    iconImageVector: ImageVector,
    maxLines: Int = 1,
) {
    Row(
        modifier = Modifier
            .padding(top = GSpace.s2),
        horizontalArrangement = Arrangement.spacedBy(GSpace.s2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = iconImageVector,
            contentDescription = null,
            modifier = Modifier.size(MangaDetailsIconWidth),
            tint = Graphite.colors.textMuted,
        )
        Text(
            text = text,
            style = Graphite.type.small,
            color = Graphite.colors.textSecondary,
            overflow = TextOverflow.Ellipsis,
            maxLines = maxLines,
        )
    }
}

@Composable
private fun getMaximumMangaCardHeight(duplicates: List<MangaWithChapterCount>): Dp {
    val density = LocalDensity.current
    val typography = Graphite.type
    val textMeasurer = rememberTextMeasurer()

    val smallPadding = with(density) { GSpace.s3.roundToPx() }
    val extraSmallPadding = with(density) { GSpace.s2.roundToPx() }

    val width = with(density) { MangaCardWidth.roundToPx() - (2 * smallPadding) }
    val iconWidth = with(density) { MangaDetailsIconWidth.roundToPx() }

    val coverHeight = width / MangaCover.Book.ratio
    val constraints = Constraints(maxWidth = width)
    val detailsConstraints = Constraints(maxWidth = width - iconWidth - extraSmallPadding)

    return remember(
        duplicates,
        density,
        typography,
        textMeasurer,
        smallPadding,
        extraSmallPadding,
        coverHeight,
        constraints,
        detailsConstraints,
    ) {
        duplicates.fastMaxOfOrNull {
            calculateMangaCardHeight(
                manga = it.manga,
                density = density,
                typography = typography,
                textMeasurer = textMeasurer,
                smallPadding = smallPadding,
                extraSmallPadding = extraSmallPadding,
                coverHeight = coverHeight,
                constraints = constraints,
                detailsConstraints = detailsConstraints,
            )
        }
            ?: 0.dp
    }
}

private fun calculateMangaCardHeight(
    manga: Manga,
    density: Density,
    typography: GraphiteType,
    textMeasurer: TextMeasurer,
    smallPadding: Int,
    extraSmallPadding: Int,
    coverHeight: Float,
    constraints: Constraints,
    detailsConstraints: Constraints,
): Dp {
    val titleHeight = textMeasurer.measureHeight(manga.title, typography.label, 2, constraints)
    val authorHeight = if (!manga.author.isNullOrBlank()) {
        textMeasurer.measureHeight(manga.author!!, typography.small, 2, detailsConstraints)
    } else {
        0
    }
    val artistHeight = if (!manga.artist.isNullOrBlank() && manga.author != manga.artist) {
        textMeasurer.measureHeight(manga.artist!!, typography.small, 2, detailsConstraints)
    } else {
        0
    }
    val statusHeight = textMeasurer.measureHeight("", typography.small, 2, detailsConstraints)
    val sourceHeight = textMeasurer.measureHeight("", typography.caption, 1, constraints)

    val totalHeight = coverHeight + titleHeight + authorHeight + artistHeight + statusHeight + sourceHeight
    return with(density) { ((2 * smallPadding) + totalHeight + (5 * extraSmallPadding)).toDp() }
}

private fun TextMeasurer.measureHeight(
    text: String,
    style: TextStyle,
    maxLines: Int,
    constraints: Constraints,
): Int = measure(
    text = text,
    style = style,
    overflow = TextOverflow.Ellipsis,
    maxLines = maxLines,
    constraints = constraints,
)
    .size
    .height

private val MangaCardWidth = 150.dp
private val MangaDetailsIconWidth = 16.dp
