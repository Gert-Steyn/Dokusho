package mihon.core.archive.book

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.nio.ByteBuffer

class MobiDocumentTest {

    @Test
    fun `uncompressed utf-8 book splits sections on page breaks and builds toc`() {
        val html = "<h1>One</h1><p>Alpha</p><mbp:pagebreak/><h2>Two</h2><p>Beta</p>"
        val doc = parse(record0(), html.toByteArray())

        doc.book.sections.size shouldBe 2
        doc.book.sections[1].blocks shouldBe listOf(
            BookBlock.Heading(2, BookText("Two")),
            BookBlock.Paragraph(BookText("Beta")),
        )
        doc.book.toc shouldBe listOf(TocEntry("One", 0, 0, 0), TocEntry("Two", 1, 1, 0))
    }

    @Test
    fun `title comes from the full title field`() {
        val doc = parse(record0(title = "Full".toByteArray()), "<p>x</p>".toByteArray())
        doc.book.title shouldBe "Full"
    }

    @Test
    fun `exth 503 overrides the full title`() {
        val doc = parse(
            record0(title = "Full".toByteArray(), exth = exth(503 to "Updated".toByteArray())),
            "<p>x</p>".toByteArray(),
        )
        doc.book.title shouldBe "Updated"
    }

    @Test
    fun `windows-1252 text is decoded`() {
        val text = "<p>caf".toByteArray() + byteArrayOf(0xE9.toByte()) + "</p>".toByteArray()
        val doc = parse(record0(encoding = 1252), text)
        doc.book.sections.single().blocks shouldBe listOf(BookBlock.Paragraph(BookText("café")))
    }

    @Test
    fun `palmdoc decompression handles every byte class`() {
        val input = byteArrayOf(
            0x41, // literal A
            0x00, // literal NUL
            0x03, 0x78, 0x79, 0x7A, // verbatim run of three bytes: x y z
            0x80.toByte(), 0x09, // distance 1, length 4: overlapping copy of the last byte
            0xC1.toByte(), // space followed by 'A'
            0x51, // literal Q
        )
        String(decompressPalmDoc(input), Charsets.ISO_8859_1) shouldBe "A\u0000xyzzzzz AQ"
    }

    @Test
    fun `multibyte trailing entry is stripped`() {
        stripTrailingEntries(byteArrayOf(0x61, 0x62, 0x63, 0x64, 0x00, 0x01), extraFlags = 0x1) shouldBe
            "abcd".toByteArray()
    }

    @Test
    fun `sized trailing entry is stripped`() {
        val record = "abcd".toByteArray() + "xy".toByteArray() + byteArrayOf(0x83.toByte())
        stripTrailingEntries(record, extraFlags = 0x2) shouldBe "abcd".toByteArray()
    }

    @Test
    fun `multibyte and sized trailing entries are both stripped`() {
        val record = "abcd".toByteArray() + byteArrayOf(0x00, 0x01) + "xy".toByteArray() + byteArrayOf(0x83.toByte())
        stripTrailingEntries(record, extraFlags = 0x3) shouldBe "abcd".toByteArray()
    }

    @Test
    fun `multi-byte trailing size is read backwards`() {
        trailingEntrySize(byteArrayOf(0x81.toByte(), 0x48), end = 2) shouldBe 200
    }

    @Test
    fun `recindex images map to records and open their bytes`() {
        val doc = parse(
            record0(firstImage = 2),
            "<img recindex=\"00001\"/><img recindex=\"00002\"/>".toByteArray(),
            "IMG-A".toByteArray(),
            "IMG-B".toByteArray(),
        )

        doc.book.sections.single().blocks shouldBe listOf(BookBlock.Image("2"), BookBlock.Image("3"))
        doc.openImage("3")!!.readBytes().decodeToString() shouldBe "IMG-B"
    }

    @Test
    fun `kf8 kindle embed uses base 32 index and the first fdst flow`() {
        val flowOne = "<p>Kindle</p><img src=\"kindle:embed:0002?mime=image/jpeg\"/>".toByteArray()
        val flowTwo = "body{}".toByteArray()
        val text = flowOne + flowTwo
        val fdst = "FDST".toByteArray() + int(12) + int(2) +
            int(0) + int(flowOne.size) + int(flowOne.size) + int(text.size)

        val doc = parse(
            record0(version = 8, firstImage = 3, fdst = 2),
            text,
            fdst,
            "IMG-1".toByteArray(),
            "IMG-2".toByteArray(),
        )

        doc.book.sections.size shouldBe 1
        doc.book.sections.single().blocks shouldBe listOf(
            BookBlock.Paragraph(BookText("Kindle")),
            BookBlock.Image("4"),
        )
        doc.openImage("4")!!.readBytes().decodeToString() shouldBe "IMG-2"
    }

    @Test
    fun `cover comes from the exth cover offset`() {
        val doc = parse(
            record0(firstImage = 2, exth = exth(201 to int(1))),
            "<p>x</p>".toByteArray(),
            "COVER-A".toByteArray(),
            "COVER-B".toByteArray(),
        )
        doc.cover!!.decodeToString() shouldBe "COVER-B"
    }

    @Test
    fun `cover falls back to the first image in the book`() {
        val doc = parse(
            record0(firstImage = 2),
            "<img recindex=\"00002\"/>".toByteArray(),
            "COVER-A".toByteArray(),
            "COVER-B".toByteArray(),
        )
        doc.cover!!.decodeToString() shouldBe "COVER-B"
    }

    @Test
    fun `drm protected book is rejected`() {
        shouldThrow<UnsupportedMobiException> {
            parse(record0(encryption = 2), "<p>x</p>".toByteArray())
        }.reason shouldBe UnsupportedMobiException.Reason.Drm
    }

    @Test
    fun `huff cdic book is rejected`() {
        shouldThrow<UnsupportedMobiException> {
            parse(record0(compression = 17480), "<p>x</p>".toByteArray())
        }.reason shouldBe UnsupportedMobiException.Reason.Compression
    }

    private fun parse(vararg records: ByteArray): MobiDocument {
        return parseMobi(palmDatabase(records.toList()).inputStream())
    }

    /** A Palm database with a 78-byte header, 8-byte record entries and 2 padding bytes before the records. */
    private fun palmDatabase(records: List<ByteArray>): ByteArray {
        val dataStart = 78 + records.size * 8 + 2
        val offsets = records.runningFold(dataStart) { offset, record -> offset + record.size }
        val buffer = ByteBuffer.allocate(offsets.last())
        buffer.putShort(76, records.size.toShort())
        records.indices.forEach { i ->
            buffer.putInt(78 + i * 8, offsets[i])
            buffer.putInt(82 + i * 8, 0)
        }
        buffer.position(dataStart)
        records.forEach { buffer.put(it) }
        return buffer.array()
    }

    /** Record 0 with a PalmDOC header, a 232-byte MOBI header, an optional EXTH block and the full title. */
    private fun record0(
        textCount: Int = 1,
        compression: Int = 1,
        encryption: Int = 0,
        encoding: Int = 65001,
        version: Int = 6,
        firstImage: Int = -1,
        fdst: Int = -1,
        extraFlags: Int = 0,
        title: ByteArray = ByteArray(0),
        exth: ByteArray? = null,
    ): ByteArray {
        val headerEnd = 16 + MOBI_HEADER_LENGTH
        val titleOffset = headerEnd + (exth?.size ?: 0)
        val bytes = ByteArray(titleOffset + title.size)
        val buffer = ByteBuffer.wrap(bytes)
        buffer.putShort(0, compression.toShort())
        buffer.putShort(8, textCount.toShort())
        buffer.putShort(12, encryption.toShort())
        "MOBI".toByteArray().copyInto(bytes, 16)
        buffer.putInt(20, MOBI_HEADER_LENGTH)
        buffer.putInt(28, encoding)
        buffer.putInt(36, version)
        buffer.putInt(84, titleOffset)
        buffer.putInt(88, title.size)
        buffer.putInt(108, firstImage)
        buffer.putInt(128, if (exth != null) 0x40 else 0)
        buffer.putInt(192, fdst)
        buffer.putShort(242, extraFlags.toShort())
        exth?.copyInto(bytes, headerEnd)
        title.copyInto(bytes, titleOffset)
        return bytes
    }

    private fun exth(vararg entries: Pair<Int, ByteArray>): ByteArray {
        val body = entries.fold(ByteArray(0)) { acc, (type, data) -> acc + int(type) + int(data.size + 8) + data }
        return "EXTH".toByteArray() + int(12 + body.size) + int(entries.size) + body
    }

    private fun int(value: Int): ByteArray = ByteBuffer.allocate(4).putInt(value).array()

    private companion object {
        const val MOBI_HEADER_LENGTH = 232
    }
}
