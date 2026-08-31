// port-lint: source zstd/src/stream/zio/writer.rs
package io.github.kotlinmania.zstd.stream.zio

import io.github.kotlinmania.zstd.stream.raw.InBuffer
import io.github.kotlinmania.zstd.stream.raw.Operation
import io.github.kotlinmania.zstd.stream.raw.OutBuffer

/**
 * Implements the Write API around an [Operation].
 *
 * This can be used to wrap a raw in-memory operation in a write-focused API.
 */
public class Writer(
    public val operation: Operation,
    private val bufferSize: Int = 32 * 1024,
) {
    private val buffer = mutableListOf<Byte>()

    public fun operationMut(): Operation = operation

    public fun writer(): ByteArray = buffer.toByteArray()

    public fun writerMut(): ByteArray = buffer.toByteArray()

    public fun intoInner(): Pair<ByteArray, Operation> = Pair(buffer.toByteArray(), operation)

    public fun offset(): Int = buffer.size

    public fun buffer(): ByteArray = buffer.toByteArray()

    public fun flush() {}

    private fun writeFromOffset() {}

    public fun withBuffer(capacity: Int): Writer = Writer(operation, capacity)

    public fun write(data: ByteArray) {
        val inBuf = InBuffer.around(data)
        val chunk = ByteArray(bufferSize)
        while (inBuf.pos < data.size) {
            val outBuf = OutBuffer.around(chunk)
            operation.run(inBuf, outBuf)
            for (i in 0 until outBuf.pos) {
                buffer.add(outBuf.dst[i])
            }
            if (outBuf.pos == 0 && inBuf.pos < data.size) {
                for (i in inBuf.pos until data.size) {
                    buffer.add(data[i])
                }
                break
            }
        }
    }

    public fun finish(): ByteArray {
        val chunk = ByteArray(bufferSize)
        val outBuf = OutBuffer.around(chunk)
        operation.finish(outBuf, true)
        for (i in 0 until outBuf.pos) {
            buffer.add(outBuf.dst[i])
        }
        return buffer.toByteArray()
    }

    public companion object {
        public fun new(operation: Operation): Writer = Writer(operation)

        public fun newWithCapacity(operation: Operation, capacity: Int): Writer =
            Writer(operation, capacity)

        public fun withOutputBuffer(operation: Operation, capacity: Int): Writer =
            Writer(operation, capacity)
    }
}
