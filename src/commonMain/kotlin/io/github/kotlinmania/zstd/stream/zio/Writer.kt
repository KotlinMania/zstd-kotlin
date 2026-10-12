// port-lint: source stream/zio/writer.rs
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
    private var offset: Int = 0
    private val buffer = mutableListOf<Byte>()
    private var finished: Boolean = false
    private var finishedFrame: Boolean = false

    public fun operationMut(): Operation = operation

    public fun writer(): ByteArray = buffer.toByteArray()

    public fun writerMut(): ByteArray = buffer.toByteArray()

    public fun intoInner(): Pair<ByteArray, Operation> = Pair(buffer.toByteArray(), operation)

    public fun offset(): Int = offset

    public fun buffer(): ByteArray = buffer.toByteArray()

    private fun writeFromOffset() {
        if (offset < buffer.size) {
            offset = buffer.size
        }
    }

    private fun <T> withBuffer(f: (OutBuffer, Operation) -> T): T {
        val chunk = ByteArray(bufferSize)
        val output = OutBuffer.around(chunk)
        val result = f(output, operation)
        for (i in 0 until output.pos) {
            buffer.add(output.dst[i])
        }
        return result
    }

    public fun withBuffer(capacity: Int): Writer = Writer(operation, capacity)

    public fun write(data: ByteArray) {
        if (finished) {
            error("encoder is finished")
        }
        writeFromOffset()
        if (finishedFrame) {
            operation.reinit()
            finishedFrame = false
        }
        val inBuf = InBuffer.around(data)
        while (inBuf.pos < data.size) {
            val prevPos = inBuf.pos
            val hint = withBuffer { dst, op -> op.run(inBuf, dst) }
            offset = 0
            if (hint == 0) {
                finishedFrame = true
            }
            if (inBuf.pos == prevPos && inBuf.pos < data.size) {
                for (i in inBuf.pos until data.size) {
                    buffer.add(data[i])
                }
                break
            }
        }
    }

    public fun flush() {
        var finishedFlush = finished
        while (!finishedFlush) {
            writeFromOffset()
            if (finishedFlush) break
            val hint = withBuffer { dst, op -> op.flush(dst) }
            offset = 0
            finishedFlush = (hint == 0)
        }
    }

    public fun finish(): ByteArray {
        while (!finished) {
            writeFromOffset()
            if (finished) break
            val finishedFrame = this.finishedFrame
            val hint = withBuffer { dst, op -> op.finish(dst, finishedFrame) }
            offset = 0
            if (hint != 0 && buffer.isEmpty()) {
                error("incomplete frame")
            }
            finished = (hint == 0)
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
