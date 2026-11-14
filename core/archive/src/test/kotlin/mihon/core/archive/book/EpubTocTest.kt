package mihon.core.archive.book

import io.kotest.matchers.shouldBe
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import org.junit.jupiter.api.Test

class EpubTocTest {

    private val resolver: (String) -> TocTarget? = { href ->
        when (href) {
            "ch1.xhtml" -> TocTarget(0, 0)
            "ch1.xhtml#s2" -> TocTarget(0, 3)
            "ch2.xhtml" -> TocTarget(1, 0)
            else -> null
        }
    }

    @Test
    fun `nav document gives nested entries with depth`() {
        val nav = Jsoup.parse(
            """
            <html><body><nav epub:type="toc"><ol>
              <li><a href="ch1.xhtml">One</a>
                <ol><li><a href="ch1.xhtml#s2">One.Two</a></li></ol>
              </li>
              <li><a href="ch2.xhtml">Two</a></li>
              <li><a href="missing.xhtml">Gone</a></li>
            </ol></nav></body></html>
            """.trimIndent(),
        )

        resolveToc(parseNavToc(nav), resolver) shouldBe listOf(
            TocEntry("One", depth = 0, section = 0, block = 0),
            TocEntry("One.Two", depth = 1, section = 0, block = 3),
            TocEntry("Two", depth = 0, section = 1, block = 0),
        )
    }

    @Test
    fun `ncx navPoints give nested entries with depth`() {
        val ncx = Jsoup.parse(
            """
            <ncx xmlns="http://www.daisy.org/z3986/2005/ncx/"><navMap>
              <navPoint id="a"><navLabel><text>Part</text></navLabel><content src="ch1.xhtml"/>
                <navPoint id="b"><navLabel><text>Sub</text></navLabel><content src="ch1.xhtml#s2"/></navPoint>
              </navPoint>
            </navMap></ncx>
            """.trimIndent(),
            "",
            Parser.xmlParser(),
        )

        resolveToc(parseNcxToc(ncx), resolver) shouldBe listOf(
            TocEntry("Part", depth = 0, section = 0, block = 0),
            TocEntry("Sub", depth = 1, section = 0, block = 3),
        )
    }
}
