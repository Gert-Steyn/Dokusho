package mihon.core.archive.book

import io.kotest.matchers.shouldBe
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test

class HtmlBlockParserTest {

    private fun parse(html: String, resolveImage: (String) -> String? = { it }): ParsedHtml {
        return parseHtmlBlocks(Jsoup.parse(html).body(), resolveImage)
    }

    @Test
    fun `paragraphs and headings`() {
        parse("<h1>Title</h1><p>First</p><p>Second</p>").blocks shouldBe listOf(
            BookBlock.Heading(1, BookText("Title")),
            BookBlock.Paragraph(BookText("First")),
            BookBlock.Paragraph(BookText("Second")),
        )
    }

    @Test
    fun `whitespace is collapsed and empty paragraphs dropped`() {
        parse("<p>  a \n\t b   <span>c</span>\n</p><p>   </p>").blocks shouldBe listOf(
            BookBlock.Paragraph(BookText("a b c")),
        )
    }

    @Test
    fun `nested styles produce offset spans`() {
        parse("<p>x <b>y <i>z</i></b> w</p>").blocks shouldBe listOf(
            BookBlock.Paragraph(
                BookText(
                    "x y z w",
                    listOf(
                        BookText.Span(2, 5, BookText.Style.Bold),
                        BookText.Span(4, 5, BookText.Style.Italic),
                    ),
                ),
            ),
        )
    }

    @Test
    fun `br becomes a newline inside the paragraph`() {
        parse("<p>one<br>two</p>").blocks shouldBe listOf(
            BookBlock.Paragraph(BookText("one\ntwo")),
        )
    }

    @Test
    fun `blockquote and pre set their flags`() {
        parse("<blockquote><p>quoted</p></blockquote><pre>  a\n  b</pre>").blocks shouldBe listOf(
            BookBlock.Paragraph(BookText("quoted"), quote = true),
            BookBlock.Paragraph(BookText("  a\n  b"), preformatted = true),
        )
    }

    @Test
    fun `list items get bullets`() {
        parse("<ul><li>one</li><li><p>two</p></li></ul>").blocks shouldBe listOf(
            BookBlock.Paragraph(BookText("• one")),
            BookBlock.Paragraph(BookText("• two")),
        )
    }

    @Test
    fun `hr becomes a rule`() {
        parse("<p>a</p><hr><p>b</p>").blocks shouldBe listOf(
            BookBlock.Paragraph(BookText("a")),
            BookBlock.Rule,
            BookBlock.Paragraph(BookText("b")),
        )
    }

    @Test
    fun `images are resolved and unresolved ones dropped`() {
        val result = parse("<p>x<img src=\"a.png\"></p><img src=\"drop.png\">") { src ->
            if (src == "drop.png") null else "res/$src"
        }
        result.blocks shouldBe listOf(
            BookBlock.Paragraph(BookText("x")),
            BookBlock.Image("res/a.png"),
        )
    }

    @Test
    fun `anchors map ids and named links to the next block`() {
        val result = parse("<p id=\"a\">one</p><p>two</p><p><a name=\"b\"></a>three</p>")
        result.anchors shouldBe mapOf("a" to 0, "b" to 2)
    }

    @Test
    fun `script and style content is skipped`() {
        parse("<div><script>var x = 1;</script>a<style>p { }</style></div>").blocks shouldBe listOf(
            BookBlock.Paragraph(BookText("a")),
        )
    }
}
