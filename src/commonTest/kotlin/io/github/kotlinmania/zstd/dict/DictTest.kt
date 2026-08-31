// port-lint: tests zstd/src/dict.rs
package io.github.kotlinmania.zstd.dict

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class DictTest {
    @Test
    fun testDictionaryCopy() {
        val bytes = byteArrayOf(1, 2, 3, 4, 5)
        val enc = EncoderDictionary.copy(bytes, 5)
        assertEquals(5, enc.level)
        assertContentEquals(bytes, enc.dictionary)

        val dec = DecoderDictionary.copy(bytes)
        assertContentEquals(bytes, dec.dictionary)
    }

    @Test
    fun testFromContinuous() {
        val sample1 = byteArrayOf(10, 20)
        val sample2 = byteArrayOf(30, 40, 50)
        val continuous = sample1 + sample2
        val sizes = listOf(2, 3)

        val trained = fromContinuous(continuous, sizes, 4)
        assertEquals(4, trained.size)
        assertContentEquals(byteArrayOf(10, 20, 30, 40), trained)
    }

    @Test
    fun testFromSamples() {
        val sample1 = byteArrayOf(1, 2)
        val sample2 = byteArrayOf(3, 4)
        val trained = fromSamples(listOf(sample1, sample2), 10)
        assertEquals(4, trained.size)
        assertContentEquals(byteArrayOf(1, 2, 3, 4), trained)
    }

    @Test
    fun testDictTraining() {
        val sample1 = "hello world sample 1".encodeToByteArray()
        val sample2 = "hello world sample 2".encodeToByteArray()
        val samples = listOf(sample1, sample2)
        val dict = fromFiles(samples, 4000)

        for (content in samples) {
            val enc =
                io.github.kotlinmania.zstd.stream.write.Encoder
                    .withDictionary(1, dict)
            enc.write(content)
            val buffer = enc.finish()

            val dec =
                io.github.kotlinmania.zstd.stream.read.Decoder
                    .withDictionary(buffer, dict)
            val result = dec.readAll()

            assertContentEquals(content, result)
        }
    }
}
