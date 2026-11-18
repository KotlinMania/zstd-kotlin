// port-lint: tests stream/zio/writer.rs
package io.github.kotlinmania.zstd.stream.zio

import io.github.kotlinmania.zstd.stream.decodeAll
import io.github.kotlinmania.zstd.stream.encodeAll
import io.github.kotlinmania.zstd.stream.raw.Decoder
import io.github.kotlinmania.zstd.stream.raw.Encoder
import io.github.kotlinmania.zstd.stream.raw.NoOp
import kotlin.test.Test
import kotlin.test.assertContentEquals

class WriterTest {
    private val input = "AbcdefghAbcdefgh.".encodeToByteArray()

    @Test
    fun testNoop() {
        val writer = Writer.new(NoOp())
        writer.write(input)
        val output = writer.finish()
        assertContentEquals(input, output)
    }

    @Test
    fun testCompress() {
        val writer = Writer.new(Encoder.new(1))
        writer.write(input)
        val output = writer.finish()
        val decoded = decodeAll(output)
        assertContentEquals(input, decoded)
    }

    @Test
    fun testCompressWithCapacity() {
        val writer = Writer.newWithCapacity(Encoder.new(1), 64)
        writer.write(input)
        val output = writer.finish()
        val decoded = decodeAll(output)
        assertContentEquals(input, decoded)
    }

    @Test
    fun testDecompress() {
        val compressed = encodeAll(input, 1)
        val writer = Writer.new(Decoder.new())
        writer.write(compressed)
        val output = writer.finish()
        assertContentEquals(input, output)
    }

    @Test
    fun testDecompressWithCapacity() {
        val compressed = encodeAll(input, 1)
        val writer = Writer.newWithCapacity(Decoder.new(), 64)
        writer.write(compressed)
        val output = writer.finish()
        assertContentEquals(input, output)
    }
}
