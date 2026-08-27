// port-lint: tests zstd/src/stream/zio/reader.rs, zstd/src/stream/zio/writer.rs
package io.github.kotlinmania.zstd.stream.zio

import io.github.kotlinmania.zstd.decodeAll
import io.github.kotlinmania.zstd.encodeAll
import io.github.kotlinmania.zstd.stream.raw.Decoder
import io.github.kotlinmania.zstd.stream.raw.Encoder
import io.github.kotlinmania.zstd.stream.raw.NoOp
import kotlin.test.Test
import kotlin.test.assertContentEquals

class ZioTest {
    private val input = "AbcdefghAbcdefgh.".encodeToByteArray()

    @Test
    fun testReaderNoop() {
        val reader = Reader.new(input, NoOp())
        val output = reader.readAll()
        assertContentEquals(input, output)
    }

    @Test
    fun testReaderCompress() {
        val reader = Reader.new(input, Encoder.new(1))
        val output = reader.readAll()
        val decoded = decodeAll(output)
        assertContentEquals(input, decoded)
    }

    @Test
    fun testWriterNoop() {
        val writer = Writer.new(NoOp())
        writer.write(input)
        val output = writer.finish()
        assertContentEquals(input, output)
    }

    @Test
    fun testWriterCompress() {
        val writer = Writer.new(Encoder.new(1))
        writer.write(input)
        val output = writer.finish()
        val decoded = decodeAll(output)
        assertContentEquals(input, decoded)
    }

    @Test
    fun testWriterCompressWithCapacity() {
        val writer = Writer.newWithCapacity(Encoder.new(1), 64)
        writer.write(input)
        val output = writer.finish()
        val decoded = decodeAll(output)
        assertContentEquals(input, decoded)
    }

    @Test
    fun testWriterDecompress() {
        val compressed = encodeAll(input, 1)
        val writer = Writer.new(Decoder.new())
        writer.write(compressed)
        val output = writer.finish()
        assertContentEquals(input, output)
    }

    @Test
    fun testWriterDecompressWithCapacity() {
        val compressed = encodeAll(input, 1)
        val writer = Writer.newWithCapacity(Decoder.new(), 64)
        writer.write(compressed)
        val output = writer.finish()
        assertContentEquals(input, output)
    }

    @Test
    fun testNoop() {
        testReaderNoop()
        testWriterNoop()
    }

    @Test
    fun testCompress() {
        testReaderCompress()
        testWriterCompress()
    }

    @Test
    fun testCompressWithCapacity() {
        testWriterCompressWithCapacity()
    }

    @Test
    fun testDecompress() {
        testWriterDecompress()
    }

    @Test
    fun testDecompressWithCapacity() {
        testWriterDecompressWithCapacity()
    }
}
