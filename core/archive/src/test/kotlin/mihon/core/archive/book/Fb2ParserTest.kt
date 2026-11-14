package mihon.core.archive.book

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Fb2ParserTest {

    private val source = """
        <?xml version="1.0" encoding="UTF-8"?>
        <FictionBook xmlns="http://www.gribuser.ru/xml/fictionbook/2.0" xmlns:l="http://www.w3.org/1999/xlink">
          <description>
            <title-info>
              <book-title>My Book</book-title>
            </title-info>
          </description>
          <body>
            <section>
              <title><p>Chapter One</p></title>
              <p>Hello <strong>bold</strong> world</p>
              <empty-line/>
              <section>
                <title><p>Sub</p></title>
                <image l:href="#pic.png"/>
                <p>Deep</p>
              </section>
            </section>
          </body>
          <binary id="pic.png" content-type="image/png">aGVsbG8=</binary>
        </FictionBook>
    """.trimIndent()

    @Test
    fun `parses title sections and nested headings`() {
        val result = parseFb2(source.byteInputStream())

        result.book.title shouldBe "My Book"
        result.book.sections.size shouldBe 1
        result.book.sections.single().blocks shouldBe listOf(
            BookBlock.Heading(1, BookText("Chapter One")),
            BookBlock.Paragraph(
                BookText("Hello bold world", listOf(BookText.Span(6, 10, BookText.Style.Bold))),
            ),
            BookBlock.Heading(2, BookText("Sub")),
            BookBlock.Image("pic.png"),
            BookBlock.Paragraph(BookText("Deep")),
        )
    }

    @Test
    fun `builds toc from section titles`() {
        parseFb2(source.byteInputStream()).book.toc shouldBe listOf(
            TocEntry("Chapter One", depth = 0, section = 0, block = 0),
            TocEntry("Sub", depth = 1, section = 0, block = 2),
        )
    }

    @Test
    fun `decodes binary images by id`() {
        parseFb2(source.byteInputStream()).images.getValue("pic.png").decodeToString() shouldBe "hello"
    }
}
