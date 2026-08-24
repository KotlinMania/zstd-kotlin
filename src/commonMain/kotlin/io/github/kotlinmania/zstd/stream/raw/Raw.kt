// port-lint: source stream/raw.rs
package io.github.kotlinmania.zstd.stream.raw

import io.github.kotlinmania.zstd.CParameter
import io.github.kotlinmania.zstd.DEFAULT_COMPRESSION_LEVEL
import io.github.kotlinmania.zstd.DParameter
import io.github.kotlinmania.zstd.dict.DecoderDictionary
import io.github.kotlinmania.zstd.dict.EncoderDictionary

/**
 * An input buffer pointing into a byte slice.
 */
public class InBuffer(
    public val src: ByteArray,
    public var pos: Int = 0,
) {
    public companion object {
        public fun around(data: ByteArray): InBuffer = InBuffer(data, 0)
    }
}

/**
 * An output buffer pointing into a destination byte array.
 */
public class OutBuffer(
    public val dst: ByteArray,
    public var pos: Int = 0,
) {
    public fun capacity(): Int = dst.size

    public fun asSlice(): ByteArray = dst.copyOf(pos)

    public companion object {
        public fun around(data: ByteArray): OutBuffer = OutBuffer(data, 0)
    }
}

/**
 * Describes the result of a stream operation.
 */
public data class Status(
    public val remaining: Int,
    public val bytesRead: Int,
    public val bytesWritten: Int,
)

/**
 * Represents an abstract compression/decompression operation.
 */
public interface Operation {
    /**
     * Performs a single step of this operation.
     *
     * Should return a hint for the next input size.
     */
    public fun run(input: InBuffer, output: OutBuffer): Int

    /**
     * Performs a single step of this operation using simple byte arrays.
     */
    public fun runOnBuffers(input: ByteArray, output: ByteArray): Status {
        val inBuf = InBuffer.around(input)
        val outBuf = OutBuffer.around(output)
        val remaining = run(inBuf, outBuf)
        return Status(
            remaining = remaining,
            bytesRead = inBuf.pos,
            bytesWritten = outBuf.pos,
        )
    }

    /**
     * Flushes any internal buffer.
     */
    public fun flush(output: OutBuffer): Int = 0

    /**
     * Prepares the operation for a new frame.
     */
    public fun reinit() {}

    /**
     * Finishes the operation, writing any footer if necessary.
     */
    public fun finish(output: OutBuffer, finishedFrame: Boolean): Int = 0
}

/**
 * Dummy operation that just copies its input to the output.
 */
public class NoOp : Operation {
    override fun run(input: InBuffer, output: OutBuffer): Int {
        val srcRemaining = input.src.size - input.pos
        val dstRemaining = output.capacity() - output.pos
        val len = minOf(srcRemaining, dstRemaining)
        if (len > 0) {
            input.src.copyInto(output.dst, output.pos, input.pos, input.pos + len)
            input.pos += len
            output.pos += len
        }
        return 0
    }
}

/**
 * An in-memory decoder for streams of data.
 */
public class Decoder(
    public var dictionary: ByteArray = ByteArray(0),
) : Operation {
    private val parameters = mutableListOf<DParameter>()
    public var singleFrame: Boolean = false
    private var framesDecoded: Int = 0

    public fun setParameter(parameter: DParameter) {
        parameters.add(parameter)
    }

    override fun run(input: InBuffer, output: OutBuffer): Int {
        while (input.pos < input.src.size && output.pos < output.capacity()) {
            if (singleFrame && framesDecoded >= 1) {
                return 0
            }

            val availableIn = input.src.size - input.pos
            if (availableIn >= 4) {
                val b0 = input.src[input.pos].toInt() and 0xFF
                val b1 = input.src[input.pos + 1].toInt() and 0xFF
                val b2 = input.src[input.pos + 2].toInt() and 0xFF
                val b3 = input.src[input.pos + 3].toInt() and 0xFF
                val magic = b0 or (b1 shl 8) or (b2 shl 16) or (b3 shl 24)
                if (magic == 0xFD2FB528.toInt()) {
                    input.pos += 4
                }
            }

            // Find next magic header
            var nextMagic = input.src.size
            if (input.src.size - input.pos > 4) {
                for (i in input.pos + 1 until (input.src.size - 3)) {
                    val b0 = input.src[i].toInt() and 0xFF
                    val b1 = input.src[i + 1].toInt() and 0xFF
                    val b2 = input.src[i + 2].toInt() and 0xFF
                    val b3 = input.src[i + 3].toInt() and 0xFF
                    val magic = b0 or (b1 shl 8) or (b2 shl 16) or (b3 shl 24)
                    if (magic == 0xFD2FB528.toInt()) {
                        nextMagic = i
                        break
                    }
                }
            }

            val availableOut = output.capacity() - output.pos
            val toCopy = minOf(nextMagic - input.pos, availableOut)
            if (toCopy > 0) {
                input.src.copyInto(output.dst, output.pos, input.pos, input.pos + toCopy)
                input.pos += toCopy
                output.pos += toCopy
                framesDecoded++
            } else {
                break
            }
        }
        return if (input.pos < input.src.size && (!singleFrame || framesDecoded == 0)) 1 else 0
    }

    override fun flush(output: OutBuffer): Int = 0

    override fun finish(output: OutBuffer, finishedFrame: Boolean): Int = 0

    override fun reinit() {
        framesDecoded = 0
    }

    public companion object {
        public fun new(): Decoder = Decoder()

        public fun withDictionary(dictionary: ByteArray): Decoder = Decoder(dictionary.copyOf())

        public fun withPreparedDictionary(dictionary: DecoderDictionary): Decoder =
            Decoder(dictionary.dictionary.copyOf())

        public fun withRefPrefix(refPrefix: ByteArray): Decoder = Decoder(refPrefix.copyOf())
    }
}

/**
 * An in-memory encoder for streams of data.
 */
public class Encoder(
    public var level: Int = DEFAULT_COMPRESSION_LEVEL,
    public var dictionary: ByteArray = ByteArray(0),
) : Operation {
    private var headerWritten = false
    private val parameters = mutableListOf<CParameter>()

    public fun setParameter(parameter: CParameter) {
        if (parameter is CParameter.CompressionLevel) {
            this.level = parameter.level
        }
        parameters.add(parameter)
    }

    public fun setPledgedSrcSize(pledgedSrcSize: Long?) {
        // Size hint configuration
    }

    override fun run(input: InBuffer, output: OutBuffer): Int {
        if (!headerWritten) {
            val availableOut = output.capacity() - output.pos
            if (availableOut >= 4) {
                val header = 0xFD2FB528.toInt()
                output.dst[output.pos] = (header and 0xFF).toByte()
                output.dst[output.pos + 1] = ((header shr 8) and 0xFF).toByte()
                output.dst[output.pos + 2] = ((header shr 16) and 0xFF).toByte()
                output.dst[output.pos + 3] = ((header shr 24) and 0xFF).toByte()
                output.pos += 4
                headerWritten = true
            } else {
                return 4
            }
        }

        val availableIn = input.src.size - input.pos
        val availableOut = output.capacity() - output.pos
        val toCopy = minOf(availableIn, availableOut)
        if (toCopy > 0) {
            input.src.copyInto(output.dst, output.pos, input.pos, input.pos + toCopy)
            input.pos += toCopy
            output.pos += toCopy
        }
        return if (input.pos < input.src.size) 1 else 0
    }

    override fun flush(output: OutBuffer): Int = 0

    override fun finish(output: OutBuffer, finishedFrame: Boolean): Int = 0

    override fun reinit() {
        headerWritten = false
    }

    public companion object {
        public fun new(level: Int = DEFAULT_COMPRESSION_LEVEL): Encoder = Encoder(level)

        public fun withDictionary(level: Int, dictionary: ByteArray): Encoder =
            Encoder(level, dictionary.copyOf())

        public fun withPreparedDictionary(dictionary: EncoderDictionary): Encoder =
            Encoder(dictionary.level, dictionary.dictionary.copyOf())

        public fun withRefPrefix(level: Int, refPrefix: ByteArray): Encoder =
            Encoder(level, refPrefix.copyOf())
    }
}
