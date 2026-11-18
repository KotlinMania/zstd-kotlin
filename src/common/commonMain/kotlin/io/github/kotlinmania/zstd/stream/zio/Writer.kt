// port-lint: source stream/zio/writer.rs
package io.github.kotlinmania.zstd.stream.zio

import io.github.kotlinmania.zstd.stream.raw.InBuffer
import io.github.kotlinmania.zstd.stream.raw.Operation
import io.github.kotlinmania.zstd.stream.raw.OutBuffer

/**
 * Implements the Write API around an [Operation].
 *
 * This can be used to wrap a raw in-memory operation in a write-focused API.
 *
 * It can be used with either compression or decompression, and forwards the
 * output to a wrapped writer.
 */
public class Writer(
    private val operation: Operation,
    private val bufferSize: Int = 32 * 1024,
) {
    private var offset: Int = 0
    private val buffer = mutableListOf<Byte>()
    private var finished: Boolean = false
    private var finishedFrame: Boolean = false

    /**
     * Gives a reference to the inner operation.
     */
    public fun operation(): Operation = operation

    /**
     * Gives a mutable reference to the inner operation.
     */
    public fun operationMut(): Operation = operation

    /**
     * Gives a reference to the inner writer.
     */
    public fun writer(): ByteArray = buffer.toByteArray()

    /**
     * Gives a mutable reference to the inner writer.
     */
    public fun writerMut(): ByteArray = buffer.toByteArray()

    /**
     * Return the wrapped writer and operation.
     */
    public fun intoInner(): Pair<ByteArray, Operation> = Pair(buffer.toByteArray(), operation)

    /**
     * Returns the offset in the current buffer.
     */
    public fun offset(): Int = offset

    /**
     * Returns the current buffer.
     */
    public fun buffer(): ByteArray = buffer.toByteArray()

    /**
     * Attempt to write buffer to the wrapped writer.
     */
    private fun writeFromOffset() {
        if (offset < buffer.size) {
            offset = buffer.size
        }
    }

    /**
     * Run the given closure on the buffer.
     */
    private fun <T> withBuffer(f: (OutBuffer, Operation) -> T): T {
        val chunk = ByteArray(bufferSize)
        val output = OutBuffer.around(chunk)
        val result = f(output, operation)
        for (i in 0 until output.pos) {
            buffer.add(output.dst[i])
        }
        return result
    }

    /**
     * Creates a writer with user defined capacity.
     */
    public fun withBuffer(capacity: Int): Writer = Writer(operation, capacity)

    /**
     * Writes a buffer of bytes.
     */
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

    /**
     * Flushes any internal buffer.
     */
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

    /**
     * Ends the stream.
     *
     * This must be called after all data has been written to finish the stream.
     */
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
        /**
         * Creates a new [Writer] with a fixed buffer capacity of 32KB.
         */
        public fun new(operation: Operation): Writer = Writer(operation)

        /**
         * Creates a new [Writer] with user defined capacity.
         */
        public fun newWithCapacity(operation: Operation, capacity: Int): Writer =
            Writer(operation, capacity)

        /**
         * Creates a new [Writer] using the given output buffer.
         */
        public fun withOutputBuffer(operation: Operation, capacity: Int): Writer =
            Writer(operation, capacity)
    }
}
