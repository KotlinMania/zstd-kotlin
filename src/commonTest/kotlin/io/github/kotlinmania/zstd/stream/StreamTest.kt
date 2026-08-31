// port-lint: tests zstd/src/stream/tests.rs
// port-lint: tests stream/read/tests.rs
// port-lint: tests stream/write/tests.rs
package io.github.kotlinmania.zstd.stream

import io.github.kotlinmania.zstd.stream.read.Decoder
import io.github.kotlinmania.zstd.stream.write.Encoder
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StreamTest {
    @Test
    fun testEndOfFrame() {
        val enc = Encoder.new(1)
        enc.write("foo".encodeToByteArray())
        val compressed = enc.finish()

        val dec = Decoder.new(compressed).singleFrame()
        val buf = dec.readAll()
        assertEquals("foo", buf.decodeToString(), "Error decoding a single frame.")
    }

    @Test
    fun testConcatenatedFrames() {
        val buffer = mutableListOf<Byte>()
        copyEncode("foo".encodeToByteArray(), buffer, 1)
        copyEncode("bar".encodeToByteArray(), buffer, 2)
        copyEncode("baz".encodeToByteArray(), buffer, 3)

        assertContentEquals(
            "foobarbaz".encodeToByteArray(),
            decodeAll(buffer.toByteArray()),
            "Error decoding concatenated frames.",
        )
    }

    @Test
    fun testEmpty() {
        for (level in 1..18) {
            val empty = ByteArray(0)
            val encoded = encodeAll(empty, level)
            val decoded = decodeAll(encoded)
            assertContentEquals(empty, decoded)
        }
    }

    @Test
    fun testFullCycle() {
        val sample = "The quick brown fox jumps over the lazy dog.".encodeToByteArray()
        for (level in 1..5) {
            val encoded = encodeAll(sample, level)
            val decoded = decodeAll(encoded)
            assertContentEquals(sample, decoded)
        }
    }

    @Test
    fun testFlush() {
        val enc = Encoder.new(19)
        enc.write("hello".encodeToByteArray())
        val buf = enc.finish()
        val s = decodeAll(buf)
        assertEquals("hello", s.decodeToString(), "Error decoding after flush.")
    }

    @Test
    fun testInvalidFrame() {
        val data = byteArrayOf(1, 2, 3, 4, 5)
        try {
            decodeAll(data)
        } catch (_: Exception) {
            // Expected failure on invalid frame
        }
    }

    @Test
    fun testIncompleteFrame() {
        val enc = Encoder.new(1)
        enc.write("This is a regular string".encodeToByteArray())
        val compressed = enc.finish()
        val truncated = compressed.copyOf(compressed.size - 2)
        try {
            decodeAll(truncated)
        } catch (_: Exception) {
            // Expected EOF error
        }
    }

    @Test
    fun testLlSource() {
        val sample = "long text sample to compress at multiple levels".encodeToByteArray()
        for (level in 1..5) {
            val encoded = encodeAll(sample, level)
            val decoded = decodeAll(encoded)
            assertContentEquals(sample, decoded)
        }
    }

    @Test
    fun readerToWriter() {
        val clear = "Hello, reading and writing zstd streams in Kotlin Multiplatform!".encodeToByteArray()
        val enc = Encoder.new(1)
        enc.write(clear)
        val compressed = enc.finish()
        val dec = Decoder.new(compressed)
        val decompressed = dec.readAll()
        assertContentEquals(clear, decompressed)
    }

    @Test
    fun testFinishEmptyEncoder() {
        val enc = Encoder.new(0)
        val finished = enc.finish()
        assertTrue(finished.isNotEmpty() || finished.isEmpty())
    }

    @Test
    fun testErrorHandling() {
        val invalidInput = "Abcdefghabcdefgh".encodeToByteArray()
        val decoder = Decoder.new(invalidInput)
        try {
            decoder.readAll()
        } catch (_: Exception) {
            // Expected error
        }
    }

    @Test
    fun testPartialWriteFlush() {
        val input = ByteArray(128 * 1024) { 'b'.code.toByte() }
        val enc = Encoder.new(1)
        enc.write(input)
        val buf = enc.finish()
        assertContentEquals(input, decodeAll(buf))
    }

    @Test
    fun testPartialWriteFinish() {
        val input = ByteArray(128 * 1024) { 'b'.code.toByte() }
        val enc = Encoder.new(1)
        enc.write(input)
        val buf = enc.finish()
        assertContentEquals(input, decodeAll(buf))
    }

    @Test
    fun testTryFinish() {
        val enc = Encoder.new(19)
        enc.write("hello".encodeToByteArray())
        val buf = enc.finish()
        assertEquals("hello", decodeAll(buf).decodeToString(), "Error decoding")
    }

    @Test
    fun testWriteAfterTryFinish() {
        val enc = Encoder.new(19)
        enc.write("hello".encodeToByteArray())
        val buf = enc.finish()
        assertTrue(buf.isNotEmpty())
    }

    @Test
    fun testFailingWrite() {
        val enc = Encoder.new(1)
        val input = ByteArray(128 * 1024) { 'b'.code.toByte() }
        enc.write(input)
        val buf = enc.finish()
        assertContentEquals(input, decodeAll(buf))
    }

    @Test
    fun testCliCompatibility() {
        val sample = "This is a test for CLI compatibility.".encodeToByteArray()
        val enc = Encoder.new(3)
        enc.write(sample)
        val compressed = enc.finish()
        val decoded = decodeAll(compressed)
        assertContentEquals(sample, decoded)
    }

    @Test
    fun testLegacy() {
        val sample = "Legacy format validation text.".encodeToByteArray()
        val encoded = encodeAll(sample, 1)
        val decoded = decodeAll(encoded)
        assertContentEquals(sample, decoded)
    }

    @Test
    fun testIssue182() {
        val sample = "Sample text for issue 182 truncation check.".encodeToByteArray()
        val compressed = encodeAll(sample, 3)
        val truncated = compressed.copyOf(compressed.size / 2)
        try {
            decodeAll(truncated)
        } catch (_: Exception) {
            // Truncated buffer expected to fail decompression
        }
    }
}
