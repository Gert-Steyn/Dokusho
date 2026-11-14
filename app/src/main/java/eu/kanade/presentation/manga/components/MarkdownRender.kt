package eu.kanade.presentation.manga.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.coil3.Coil3ImageTransformerImpl
import com.mikepenz.markdown.compose.LocalBulletListHandler
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownBulletList
import com.mikepenz.markdown.compose.elements.MarkdownDivider
import com.mikepenz.markdown.compose.elements.MarkdownOrderedList
import com.mikepenz.markdown.compose.elements.MarkdownTable
import com.mikepenz.markdown.compose.elements.MarkdownTableHeader
import com.mikepenz.markdown.compose.elements.MarkdownTableRow
import com.mikepenz.markdown.compose.elements.MarkdownText
import com.mikepenz.markdown.compose.elements.listDepth
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.DefaultMarkdownInlineContent
import com.mikepenz.markdown.model.DefaultMarkdownTypography
import com.mikepenz.markdown.model.MarkdownAlertPadding
import com.mikepenz.markdown.model.MarkdownAnnotator
import com.mikepenz.markdown.model.MarkdownColors
import com.mikepenz.markdown.model.MarkdownPadding
import com.mikepenz.markdown.model.MarkdownTypography
import com.mikepenz.markdown.model.NoOpImageTransformerImpl
import com.mikepenz.markdown.model.markdownAlertPadding
import com.mikepenz.markdown.model.markdownAnnotator
import com.mikepenz.markdown.model.parseMarkdown
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.Image
import org.intellij.markdown.MarkdownTokenTypes.Companion.HTML_TAG
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.flavours.commonmark.CommonMarkMarkerProcessor
import org.intellij.markdown.flavours.gfm.table.GitHubTableMarkerProvider
import org.intellij.markdown.parser.MarkerProcessor
import org.intellij.markdown.parser.MarkerProcessorFactory
import org.intellij.markdown.parser.ProductionHolder
import org.intellij.markdown.parser.constraints.CommonMarkdownConstraints
import org.intellij.markdown.parser.constraints.MarkdownConstraints
import org.intellij.markdown.parser.markerblocks.MarkerBlockProvider
import org.intellij.markdown.parser.markerblocks.providers.AtxHeaderProvider
import org.intellij.markdown.parser.markerblocks.providers.BlockQuoteProvider
import org.intellij.markdown.parser.markerblocks.providers.CodeBlockProvider
import org.intellij.markdown.parser.markerblocks.providers.CodeFenceProvider
import org.intellij.markdown.parser.markerblocks.providers.HorizontalRuleProvider
import org.intellij.markdown.parser.markerblocks.providers.ListMarkerProvider
import org.intellij.markdown.parser.markerblocks.providers.SetextHeaderProvider
import tachiyomi.presentation.core.theme.GSpace
import tachiyomi.presentation.core.theme.Graphite

const val MARKDOWN_INLINE_IMAGE_TAG = "MARKDOWN_INLINE_IMAGE"

@Composable
fun MarkdownRender(
    content: String,
    modifier: Modifier = Modifier,
    flavour: MarkdownFlavourDescriptor = SimpleMarkdownFlavourDescriptor,
    annotator: MarkdownAnnotator = remember { markdownAnnotator() },
    loadImages: Boolean = true,
) {
    Markdown(
        // rememberMarkdownState parses again in the background even when it already parsed immediately, and its
        // new result recomposes the whole content a second time
        state = remember(content, flavour) { parseMarkdown(content = content, flavour = flavour) },
        annotator = annotator,
        colors = getMarkdownColors(),
        typography = getMarkdownTypography(),
        padding = markdownPadding,
        components = markdownComponents,
        imageTransformer = remember(loadImages) {
            if (loadImages) Coil3ImageTransformerImpl else NoOpImageTransformerImpl()
        },
        inlineContent = getMarkdownInlineContent(),
        modifier = modifier,
    )
}

@Composable
@ReadOnlyComposable
private fun getMarkdownColors(): MarkdownColors {
    val codeBackground = Graphite.colors.hover
    return DefaultMarkdownColors(
        text = Graphite.colors.text,
        codeBackground = codeBackground,
        inlineCodeBackground = codeBackground,
        dividerColor = Graphite.colors.borderSubtle,
        tableBackground = Graphite.colors.surface,
        highlightText = Graphite.colors.accent,
        highlightBackground = Graphite.colors.accentSubtle,
    )
}

@Composable
@ReadOnlyComposable
fun getMarkdownLinkStyle() = Graphite.type.body.copy(
    color = Graphite.colors.accent,
    fontWeight = FontWeight.SemiBold,
)

@Composable
@ReadOnlyComposable
private fun getMarkdownTypography(): MarkdownTypography {
    val link = getMarkdownLinkStyle()
    return DefaultMarkdownTypography(
        h1 = Graphite.type.title,
        h2 = Graphite.type.title,
        h3 = Graphite.type.title,
        h4 = Graphite.type.heading,
        h5 = Graphite.type.label,
        h6 = Graphite.type.body,
        text = Graphite.type.body,
        code = Graphite.type.mono,
        inlineCode = Graphite.type.mono,
        quote = Graphite.type.body.copy(color = Graphite.colors.textSecondary),
        paragraph = Graphite.type.body,
        ordered = Graphite.type.body,
        bullet = Graphite.type.body,
        list = Graphite.type.body,
        textLink = TextLinkStyles(style = link.toSpanStyle()),
        table = Graphite.type.body,
    )
}

private val markdownPadding = object : MarkdownPadding {
    override val alert: MarkdownAlertPadding = markdownAlertPadding()
    override val block: Dp = GSpace.s1
    override val blockQuote: PaddingValues = PaddingValues(horizontal = GSpace.s5, vertical = 0.dp)
    override val blockQuoteBar: PaddingValues.Absolute = PaddingValues.Absolute(
        left = GSpace.s2,
        top = GSpace.s1,
        right = GSpace.s2,
        bottom = GSpace.s1,
    )
    override val blockQuoteText: PaddingValues = PaddingValues(vertical = GSpace.s2)
    override val codeBlock: PaddingValues = PaddingValues(GSpace.s3)
    override val list: Dp = 0.dp
    override val listIndent: Dp = GSpace.s3
    override val listItemBottom: Dp = 0.dp
    override val listItemTop: Dp = 0.dp
}

private val markdownComponents = markdownComponents(
    horizontalRule = {
        MarkdownDivider(
            modifier = Modifier
                .padding(vertical = GSpace.s2)
                .fillMaxWidth(),
        )
    },
    orderedList = { ol ->
        Column(modifier = Modifier.padding(start = GSpace.s3)) {
            MarkdownOrderedList(
                content = ol.content,
                node = ol.node,
                style = ol.typography.ordered,
                depth = ol.listDepth,
                markerModifier = { Modifier.alignBy(FirstBaseline) },
                listModifier = { Modifier.alignBy(FirstBaseline) },
            )
        }
    },
    unorderedList = { ul ->
        val markers = listOf("•", "◦", "▸", "▹")

        CompositionLocalProvider(
            LocalBulletListHandler provides { _, _, _, _, _ -> "${markers[ul.listDepth % markers.size]} " },
        ) {
            Column(modifier = Modifier.padding(start = GSpace.s3)) {
                MarkdownBulletList(
                    content = ul.content,
                    node = ul.node,
                    style = ul.typography.bullet,
                    markerModifier = { Modifier.alignBy(FirstBaseline) },
                    listModifier = { Modifier.alignBy(FirstBaseline) },
                )
            }
        }
    },
    table = { t ->
        MarkdownTable(
            content = t.content,
            node = t.node,
            style = t.typography.text,
            headerBlock = { content, header, tableWidth, style ->
                MarkdownTableHeader(
                    content = content,
                    header = header,
                    tableWidth = tableWidth,
                    style = style,
                    maxLines = Int.MAX_VALUE,
                )
            },
            rowBlock = { content, header, tableWidth, style ->
                MarkdownTableRow(
                    content = content,
                    header = header,
                    tableWidth = tableWidth,
                    style = style,
                    maxLines = Int.MAX_VALUE,
                )
            },
        )
    },
    custom = { type, model ->
        if (type in DISALLOWED_MARKDOWN_TYPES) {
            MarkdownText(
                content = model.content.substring(model.node.startOffset, model.node.endOffset),
                node = model.node,
                style = model.typography.text,
            )
        }
    },
)

@Composable
@ReadOnlyComposable
private fun getMarkdownInlineContent() = DefaultMarkdownInlineContent(
    inlineContent = mapOf(
        MARKDOWN_INLINE_IMAGE_TAG to InlineTextContent(
            placeholder = Placeholder(
                width = Graphite.type.body.fontSize * 1.25,
                height = Graphite.type.body.fontSize * 1.25,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
            ),
            children = {
                Icon(
                    imageVector = Lucide.Icons.Image,
                    contentDescription = null,
                    tint = Graphite.colors.accent,
                )
            },
        ),
    ),
)

private object SimpleMarkdownFlavourDescriptor : CommonMarkFlavourDescriptor() {
    override val markerProcessorFactory: MarkerProcessorFactory = SimpleMarkdownProcessFactory
}

private object SimpleMarkdownProcessFactory : MarkerProcessorFactory {
    override fun createMarkerProcessor(productionHolder: ProductionHolder): MarkerProcessor<*> {
        return SimpleMarkdownMarkerProcessor(productionHolder, CommonMarkdownConstraints.BASE)
    }
}

/**
 * Like `CommonMarkFlavour`, but with html blocks and reference links removed and
 * table support added
 */
private class SimpleMarkdownMarkerProcessor(
    productionHolder: ProductionHolder,
    constraints: MarkdownConstraints,
) : CommonMarkMarkerProcessor(productionHolder, constraints) {
    private val markerBlockProviders = listOf(
        CodeBlockProvider(),
        HorizontalRuleProvider(),
        CodeFenceProvider(),
        SetextHeaderProvider(),
        BlockQuoteProvider(),
        ListMarkerProvider(),
        AtxHeaderProvider(),
        GitHubTableMarkerProvider(),
    )

    override fun getMarkerBlockProviders(): List<MarkerBlockProvider<StateInfo>> {
        return markerBlockProviders
    }
}

val DISALLOWED_MARKDOWN_TYPES = arrayOf(HTML_TAG)
