package tachiyomi.presentation.core.components.material

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun AlertDialogContent(
    buttons: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    text: @Composable () -> Unit,
) {
    AlertDialogContent(
        modifier = modifier,
        icon = icon,
        title = title,
        content = {
            Column {
                CompositionLocalProvider(
                    LocalContentColor provides Graphite.colors.textSecondary,
                ) {
                    ProvideTextStyle(Graphite.type.body) {
                        Box(
                            Modifier
                                .weight(weight = 1f, fill = false)
                                .padding(horizontal = DialogPadding)
                                .padding(TextPadding)
                                .align(Alignment.Start),
                        ) {
                            text()
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(
                            start = DialogPadding,
                            end = DialogPadding,
                            bottom = DialogPadding,
                        )
                        .align(Alignment.End),
                ) {
                    CompositionLocalProvider(
                        LocalContentColor provides Graphite.colors.accent,
                    ) {
                        ProvideTextStyle(value = Graphite.type.label, content = buttons)
                    }
                }
            }
        },
    )
}

@Composable
fun AlertDialogContent(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    content: @Composable (ColumnScope.() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .sizeIn(minWidth = MinWidth, maxWidth = MaxWidth),
    ) {
        if (icon != null || title != null) {
            Column(
                modifier = Modifier
                    .padding(
                        start = DialogPadding,
                        top = DialogPadding,
                        end = DialogPadding,
                    )
                    .fillMaxWidth(),
            ) {
                icon?.let {
                    CompositionLocalProvider(
                        LocalContentColor provides Graphite.colors.accent,
                    ) {
                        Box(
                            Modifier
                                .padding(IconPadding)
                                .align(Alignment.CenterHorizontally),
                        ) {
                            icon()
                        }
                    }
                }
                title?.let {
                    CompositionLocalProvider(
                        LocalContentColor provides Graphite.colors.text,
                    ) {
                        ProvideTextStyle(Graphite.type.title) {
                            Box(
                                // Align the title to the center when an icon is present.
                                Modifier
                                    .padding(TitlePadding)
                                    .align(
                                        if (icon == null) {
                                            Alignment.Start
                                        } else {
                                            Alignment.CenterHorizontally
                                        },
                                    ),
                            ) {
                                title()
                            }
                        }
                    }
                }
            }
        }
        content?.invoke(this)
    }
}

// Paddings for each of the dialog's parts.
private val DialogPadding = GSpace.s5
private val IconPadding = PaddingValues(bottom = GSpace.s5)
private val TitlePadding = PaddingValues(bottom = GSpace.s3)
private val TextPadding = PaddingValues(bottom = GSpace.s6)

private val MinWidth = 280.dp
private val MaxWidth = 420.dp
