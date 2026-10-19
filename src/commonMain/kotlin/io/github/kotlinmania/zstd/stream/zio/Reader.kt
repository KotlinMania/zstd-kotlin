// port-lint: source stream/zio/reader.rs
package io.github.kotlinmania.zstd.stream.zio

import io.github.kotlinmania.zstd.stream.raw.InBuffer
import io.github.kotlinmania.zstd.stream.raw.Operation
import io.github.kotlinmania.zstd.stream.raw.OutBuffer

/**
 * State of the stream reader.
 */
public enum class State {
    Reading,
    PastEof,
    Finished,
}

/**
 * Implements the Read API around an [Operation].
 *
 * This can be used to wrap a raw in-memory operation in a read-focused API.
 *
 * It can be used with either compression or decompression, and pulls input
 * data from a wrapped byte source.
 */
public class Reader(
    private val source: ByteArray,
    private val operation: Operation,
) {
    private var offset: Int = 0
    private var state: State = State.Reading
    private var singleFrame: Boolean = false
    private var finishedFrame: Boolean = false

    /**
     * Sets reader to stop after the first frame.
     *
     * By default, it will keep reading until the input is exhausted.
     */
    public fun setSingleFrame() {
        singleFrame = true
    }

    /**
     * Gives a reference to the inner operation.
     */
    public fun operation(): Operation = operation

    /**
     * Gives a mutable reference to the inner operation.
     */
    public fun operationMut(): Operation = operation

    /**
     * Gives a reference to the inner reader.
     */
    public fun reader(): ByteArray = source

    /**
     * Gives a mutable reference to the inner reader.
     */
    public fun readerMut(): ByteArray = source

    /**
     * Returns the inner reader and operation.
     */
    public fun intoInner(): ByteArray = source

    /**
     * Returns the offset in the current buffer.
     */
    public fun offset(): Int = offset

    /**
     * Returns the current buffer.
     */
    public fun buffer(): ByteArray = source

    /**
     * Returns the unread slice of the buffer.
     */
    public fun fillBuf(): ByteArray =
        if (offset < source.size) source.copyOfRange(offset, source.size) else ByteArray(0)

    /**
     * Pull some bytes from this source into the specified destination buffer.
     */
    public fun read(destination: ByteArray): Int {
        if (offset >= source.size) {
            if (state == State.Reading) {
                state = State.PastEof
                val outBuf = OutBuffer.around(destination)
                val hint = operation.finish(outBuf, finishedFrame)
                if (hint == 0) {
                    state = State.Finished
                }
                return outBuf.pos
            }
            return 0
        }
        val inBuf = InBuffer(source, offset)
        val outBuf = OutBuffer.around(destination)
        if (finishedFrame) {
            operation.reinit()
            finishedFrame = false
        }
        val hint = operation.run(inBuf, outBuf)
        offset = inBuf.pos
        if (hint == 0) {
            finishedFrame = true
            if (singleFrame) {
                state = State.Finished
            }
        }
        return outBuf.pos
    }

    /**
     * Reads all bytes from this source until EOF.
     */
    public fun readAll(): ByteArray {
        val result = mutableListOf<Byte>()
        val chunk = ByteArray(4096)
        while (true) {
            val bytesRead = read(chunk)
            if (bytesRead <= 0) break
            for (i in 0 until bytesRead) {
                result.add(chunk[i])
            }
        }
        return result.toByteArray()
    }

    /**
     * Flushes any internal buffer.
     */
    public fun flush(output: ByteArray): Int {
        val outBuf = OutBuffer.around(output)
        return operation.flush(outBuf)
    }

    public companion object {
        /**
         * Creates a new [Reader].
         */
        public fun new(source: ByteArray, operation: Operation): Reader =
            Reader(source, operation)
    }
}
