package eu.kanade.presentation.reader.appbars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import eu.kanade.presentation.reader.components.ChapterNavigator
import eu.kanade.presentation.reader.components.ChapterNavigatorType
import eu.kanade.tachiyomi.ui.reader.setting.ReaderOrientation
import eu.kanade.tachiyomi.ui.reader.setting.ReadingMode
import tachiyomi.presentation.core.theme.GSize
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

private val readerBarsSlideAnimationSpec = tween<IntOffset>(200)
private val readerBarsFadeAnimationSpec = tween<Float>(150)

@Composable
fun ReaderAppBars(
    visible: Boolean,

    mangaTitle: String?,
    chapterTitle: String?,
    navigateUp: () -> Unit,
    onClickTopAppBar: () -> Unit,
    bookmarked: Boolean,
    onToggleBookmarked: () -> Unit,
    onOpenInWebView: (() -> Unit)?,
    onOpenInBrowser: (() -> Unit)?,
    onShare: (() -> Unit)?,
    onOpenTableOfContents: (() -> Unit)?,

    chapterNavigatorType: ChapterNavigatorType,
    verticalNavigatorHeight: Float,
    onNextChapter: () -> Unit,
    enabledNext: Boolean,
    onPreviousChapter: () -> Unit,
    enabledPrevious: Boolean,
    currentPage: Int,
    totalPages: Int,
    onPageIndexChange: (Int) -> Unit,
    onPageIndexChangeFinished: () -> Unit,

    readingMode: ReadingMode,
    onClickReadingMode: () -> Unit,
    orientation: ReaderOrientation,
    onClickOrientation: () -> Unit,
    cropEnabled: Boolean,
    onClickCropBorder: () -> Unit,
    onClickSettings: () -> Unit,
    isTextChapter: Boolean,
) {
    // Translucent so the page shows through the bars, as before.
    val backgroundColor = Graphite.colors.raised
        .copy(alpha = if (isSystemInDarkTheme()) 0.9f else 0.95f)

    Column(modifier = Modifier.fillMaxHeight()) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(readerBarsSlideAnimationSpec) { -it } + fadeIn(readerBarsFadeAnimationSpec),
            exit = slideOutVertically(readerBarsSlideAnimationSpec) { -it } + fadeOut(readerBarsFadeAnimationSpec),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                ReaderTopBar(
                    modifier = Modifier
                        .background(backgroundColor)
                        .clickable(onClick = onClickTopAppBar),
                    mangaTitle = mangaTitle,
                    chapterTitle = chapterTitle,
                    navigateUp = navigateUp,
                    bookmarked = bookmarked,
                    onToggleBookmarked = onToggleBookmarked,
                    onOpenInWebView = onOpenInWebView,
                    onOpenInBrowser = onOpenInBrowser,
                    onShare = onShare,
                    onOpenTableOfContents = onOpenTableOfContents,
                )
                HorizontalDivider(thickness = GSize.border, color = Graphite.colors.borderSubtle)
            }
        }

        if (!chapterNavigatorType.isHorizontal()) {
            val sliderOnLeft = chapterNavigatorType == ChapterNavigatorType.VERTICAL_LEFT
            CompositionLocalProvider(
                LocalLayoutDirection provides if (sliderOnLeft) LayoutDirection.Ltr else LayoutDirection.Rtl,
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    AnimatedVisibility(
                        visible = visible,
                        enter = slideInHorizontally(readerBarsSlideAnimationSpec) { if (sliderOnLeft) -it else it } +
                            fadeIn(readerBarsFadeAnimationSpec),
                        exit = slideOutHorizontally(readerBarsSlideAnimationSpec) { if (sliderOnLeft) -it else it } +
                            fadeOut(readerBarsFadeAnimationSpec),
                    ) {
                        Row {
                            Spacer(modifier = Modifier.width(GSpace.s3))
                            Box(
                                modifier = Modifier.fillMaxHeight(),
                                contentAlignment = Alignment.BottomCenter,
                            ) {
                                ChapterNavigator(
                                    modifier = Modifier.fillMaxHeight(verticalNavigatorHeight),
                                    type = chapterNavigatorType,
                                    onNextChapter = onNextChapter,
                                    enabledNext = enabledNext,
                                    onPreviousChapter = onPreviousChapter,
                                    enabledPrevious = enabledPrevious,
                                    currentPage = currentPage,
                                    totalPages = totalPages,
                                    onPageIndexChange = onPageIndexChange,
                                    onPageIndexChangeFinished = onPageIndexChangeFinished,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        } else {
            Spacer(Modifier.weight(1f))
        }

        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(readerBarsSlideAnimationSpec) { it } + fadeIn(readerBarsFadeAnimationSpec),
            exit = slideOutVertically(readerBarsSlideAnimationSpec) { it } + fadeOut(readerBarsFadeAnimationSpec),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(GSpace.s3)) {
                if (chapterNavigatorType.isHorizontal()) {
                    ChapterNavigator(
                        type = chapterNavigatorType,
                        onNextChapter = onNextChapter,
                        enabledNext = enabledNext,
                        onPreviousChapter = onPreviousChapter,
                        enabledPrevious = enabledPrevious,
                        currentPage = currentPage,
                        totalPages = totalPages,
                        onPageIndexChange = onPageIndexChange,
                        onPageIndexChangeFinished = onPageIndexChangeFinished,
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    HorizontalDivider(thickness = GSize.border, color = Graphite.colors.borderSubtle)
                    ReaderBottomBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor)
                            .padding(horizontal = GSpace.s3)
                            .windowInsetsPadding(WindowInsets.navigationBars),
                        readingMode = readingMode,
                        onClickReadingMode = onClickReadingMode,
                        orientation = orientation,
                        onClickOrientation = onClickOrientation,
                        cropEnabled = cropEnabled,
                        onClickCropBorder = onClickCropBorder,
                        onClickSettings = onClickSettings,
                        isTextChapter = isTextChapter,
                    )
                }
            }
        }
    }
}
