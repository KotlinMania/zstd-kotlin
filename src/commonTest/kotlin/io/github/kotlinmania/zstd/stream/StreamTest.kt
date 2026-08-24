// port-lint: source stream/tests.rs
package io.github.kotlinmania.zstd.stream

import io.github.kotlinmania.zstd.stream.read.Decoder
import io.github.kotlinmania.zstd.stream.write.Encoder
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

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
}
