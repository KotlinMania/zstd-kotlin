// port-lint: tests zstd/src/bulk/tests.rs
package io.github.kotlinmania.zstd.bulk

import io.github.kotlinmania.zstd.decodeAll
import io.github.kotlinmania.zstd.dict.DecoderDictionary
import io.github.kotlinmania.zstd.dict.EncoderDictionary
import io.github.kotlinmania.zstd.encodeAll
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BulkTest {
    private val sampleText = TEXT

    companion object {
        private const val TEXT: String = "Hello, Zstandard Kotlin Multiplatform world! This is a test."
    }

    @Test
    fun testDirectCompressAndDecompress() {
        val originalBytes = sampleText.encodeToByteArray()
        val compressed = compress(originalBytes, 1)
        val decompressed = decompress(compressed, originalBytes.size)
        assertContentEquals(originalBytes, decompressed)
    }

    @Test
    fun testDirect() {
        testDirectCompressAndDecompress()
    }

    @Test
    fun testCompressAndDecompressToBuffer() {
        val originalBytes = sampleText.encodeToByteArray()
        val destBuffer = ByteArray(originalBytes.size + 10)
        val written = compressToBuffer(originalBytes, destBuffer, 3)

        val outBuffer = ByteArray(originalBytes.size)
        val decompressedLen = decompressToBuffer(destBuffer.copyOf(written), outBuffer)

        assertEquals(originalBytes.size, decompressedLen)
        assertContentEquals(originalBytes, outBuffer)
    }

    @Test
    fun testCompressorAndDecompressorWithPreparedDict() {
        val dictBytes = "common_dictionary_bytes".encodeToByteArray()
        val encDict = EncoderDictionary.copy(dictBytes, 3)
        val decDict = DecoderDictionary.copy(dictBytes)

        val compressor = Compressor.withPreparedDictionary(encDict)
        val decompressor = Decompressor.withPreparedDictionary(decDict)

        val originalBytes = sampleText.encodeToByteArray()
        val compressed = compressor.compress(originalBytes)
        val decompressed = decompressor.decompress(compressed, originalBytes.size)

        assertContentEquals(originalBytes, decompressed)
    }

    @Test
    fun testStreamCompat() {
        val originalBytes = sampleText.encodeToByteArray()

        // We can bulk-compress and stream-decode
        val compressed = compress(originalBytes, 1)
        val decoded = decodeAll(compressed)
        assertContentEquals(originalBytes, decoded)

        // We can stream-encode and bulk-decompress
        val encoded = encodeAll(originalBytes, 1)
        val decompressed = decompress(encoded, originalBytes.size)
        assertContentEquals(originalBytes, decompressed)
    }

    @Test
    fun hasContentSize() {
        val originalBytes = sampleText.encodeToByteArray()
        val compressed = compress(originalBytes, 1)

        // Bulk functions by default include the frame header
        assertTrue(compressed.size >= 4)
    }
}
