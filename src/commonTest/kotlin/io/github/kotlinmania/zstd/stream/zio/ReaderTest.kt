// port-lint: tests stream/zio/reader.rs
package io.github.kotlinmania.zstd.stream.zio

import io.github.kotlinmania.zstd.stream.decodeAll
import io.github.kotlinmania.zstd.stream.raw.Encoder
import io.github.kotlinmania.zstd.stream.raw.NoOp
import kotlin.test.Test
import kotlin.test.assertContentEquals

class ReaderTest {
    private val input = "AbcdefghAbcdefgh.".encodeToByteArray()

    @Test
    fun testNoop() {
        val reader = Reader.new(input, NoOp())
        val output = reader.readAll()
        assertContentEquals(input, output)
    }

    @Test
    fun testCompress() {
        val reader = Reader.new(input, Encoder.new(1))
        val output = reader.readAll()
        val decoded = decodeAll(output)
        assertContentEquals(input, decoded)
    }
}
