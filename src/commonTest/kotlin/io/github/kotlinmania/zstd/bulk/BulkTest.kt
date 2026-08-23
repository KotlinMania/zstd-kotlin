// port-lint: source bulk/tests.rs
package io.github.kotlinmania.zstd.bulk

import io.github.kotlinmania.zstd.dict.DecoderDictionary
import io.github.kotlinmania.zstd.dict.EncoderDictionary
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class BulkTest {
    private val sampleText = "Hello, Zstandard Kotlin Multiplatform world! This is a test."

    @Test
    fun testDirectCompressAndDecompress() {
        val originalBytes = sampleText.encodeToByteArray()
        val compressed = compress(originalBytes, 1)
        val decompressed = decompress(compressed, originalBytes.size)
        assertContentEquals(originalBytes, decompressed)
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
}
