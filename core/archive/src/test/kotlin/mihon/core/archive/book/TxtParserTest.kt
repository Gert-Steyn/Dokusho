package mihon.core.archive.book

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TxtParserTest {

    private fun paragraphs(book: Book): List<String> {
        return book.sections.flatMap { it.blocks }
            .filterIsInstance<BookBlock.Paragraph>()
            .map { it.text.text }
    }

    @Test
    fun `blank lines separate paragraphs and single newlines become spaces`() {
        val book = parseTxt("First line\nstill first\n\n\nSecond para\n".byteInputStream())
        paragraphs(book) shouldBe listOf("First line still first", "Second para")
    }

    @Test
    fun `without blank lines every line is a paragraph`() {
        val book = parseTxt("one\ntwo  \tthree\r\n".byteInputStream())
        paragraphs(book) shouldBe listOf("one", "two three")
    }

    @Test
    fun `utf-8 bom is skipped`() {
        paragraphs(parseTxt("﻿hello".toByteArray(Charsets.UTF_8).inputStream())) shouldBe listOf("hello")
    }

    @Test
    fun `utf-16le with bom is decoded`() {
        val bytes = byteArrayOf(0xFF.toByte(), 0xFE.toByte()) + "hi".toByteArray(Charsets.UTF_16LE)
        paragraphs(parseTxt(bytes.inputStream())) shouldBe listOf("hi")
    }

    @Test
    fun `invalid utf-8 falls back to windows-1252`() {
        val bytes = byteArrayOf(0x63, 0x61, 0x66, 0xE9.toByte())
        paragraphs(parseTxt(bytes.inputStream())) shouldBe listOf("café")
    }

    @Test
    fun `paragraphs are chunked into sections of 200`() {
        val text = (1..250).joinToString("\n") { "line $it" }
        parseTxt(text.byteInputStream()).sections.map { it.blocks.size } shouldBe listOf(200, 50)
    }
}
