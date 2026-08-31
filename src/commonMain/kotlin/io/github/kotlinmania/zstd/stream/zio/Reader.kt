// port-lint: source zstd/src/stream/zio/reader.rs
package io.github.kotlinmania.zstd.stream.zio

import io.github.kotlinmania.zstd.stream.raw.InBuffer
import io.github.kotlinmania.zstd.stream.raw.Operation
import io.github.kotlinmania.zstd.stream.raw.OutBuffer

/**
 * Implements the Read API around an [Operation].
 *
 * This can be used to wrap a raw in-memory operation in a read-focused API.
 */
public class Reader(
    private val source: ByteArray,
    public val operation: Operation,
) {
    private var offset: Int = 0
    private var singleFrame: Boolean = false

    public fun setSingleFrame() {
        singleFrame = true
    }

    public fun operationMut(): Operation = operation

    public fun reader(): ByteArray = source

    public fun readerMut(): ByteArray = source

    public fun intoInner(): ByteArray = source

    public fun offset(): Int = offset

    public fun buffer(): ByteArray = source

    public fun read(destination: ByteArray): Int {
        if (offset >= source.size) {
            return 0
        }
        val inBuf = InBuffer(source, offset)
        val outBuf = OutBuffer.around(destination)
        operation.run(inBuf, outBuf)
        offset = inBuf.pos
        return outBuf.pos
    }

    public fun readAll(): ByteArray {
        val result = mutableListOf<Byte>()
        val chunk = ByteArray(4096)
        while (offset < source.size) {
            val bytesRead = read(chunk)
            if (bytesRead <= 0) break
            for (i in 0 until bytesRead) {
                result.add(chunk[i])
            }
        }
        return result.toByteArray()
    }

    public fun flush(output: ByteArray): Int {
        val outBuf = OutBuffer.around(output)
        return operation.flush(outBuf)
    }

    public companion object {
        public fun new(source: ByteArray, operation: Operation): Reader =
            Reader(source, operation)
    }
}
