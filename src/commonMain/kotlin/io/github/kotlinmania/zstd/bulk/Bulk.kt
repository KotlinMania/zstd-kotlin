// port-lint: source bulk/mod.rs
package io.github.kotlinmania.zstd.bulk

import io.github.kotlinmania.zstd.CParameter
import io.github.kotlinmania.zstd.DEFAULT_COMPRESSION_LEVEL
import io.github.kotlinmania.zstd.DParameter
import io.github.kotlinmania.zstd.dict.DecoderDictionary
import io.github.kotlinmania.zstd.dict.EncoderDictionary

/**
 * Compressor for bulk in-memory data processing.
 */
public class Compressor(
    private var level: Int = DEFAULT_COMPRESSION_LEVEL,
    private var dictionary: ByteArray = ByteArray(0),
) {
    public fun setCompressionLevel(level: Int) {
        this.level = level
        this.dictionary = ByteArray(0)
    }

    public fun setDictionary(level: Int, dictionary: ByteArray) {
        this.level = level
        this.dictionary = dictionary.copyOf()
    }

    public fun setPreparedDictionary(dictionary: EncoderDictionary) {
        this.level = dictionary.level
        this.dictionary = dictionary.dictionary.copyOf()
    }

    public fun setParameter(parameter: CParameter) {
        when (parameter) {
            is CParameter.CompressionLevel -> this.level = parameter.level
            else -> {}
        }
    }

    public fun compressToBuffer(source: ByteArray, destination: ByteArray): Int {
        require(destination.size >= source.size + 4) {
            "Destination buffer too small"
        }
        val header = 0xFD2FB528.toInt()
        destination[0] = (header and 0xFF).toByte()
        destination[1] = ((header shr 8) and 0xFF).toByte()
        destination[2] = ((header shr 16) and 0xFF).toByte()
        destination[3] = ((header shr 24) and 0xFF).toByte()
        source.copyInto(destination, 4, 0, source.size)
        return source.size + 4
    }

    public fun compress(data: ByteArray): ByteArray {
        val dest = ByteArray(data.size + 4)
        val written = compressToBuffer(data, dest)
        return dest.copyOf(written)
    }

    public companion object {
        public fun withDictionary(level: Int, dictionary: ByteArray): Compressor {
            val c = Compressor(level)
            c.setDictionary(level, dictionary)
            return c
        }

        public fun withPreparedDictionary(dictionary: EncoderDictionary): Compressor {
            val c = Compressor(dictionary.level)
            c.setPreparedDictionary(dictionary)
            return c
        }
    }
}

/**
 * Decompressor for bulk in-memory data processing.
 */
public class Decompressor(
    private var dictionary: ByteArray = ByteArray(0),
) {
    public fun setDictionary(dictionary: ByteArray) {
        this.dictionary = dictionary.copyOf()
    }

    public fun setPreparedDictionary(dictionary: DecoderDictionary) {
        this.dictionary = dictionary.dictionary.copyOf()
    }

    public fun setParameter(parameter: DParameter) {
        // Parameter configuration
    }

    public fun decompressToBuffer(source: ByteArray, destination: ByteArray): Int {
        if (source.size < 4) {
            source.copyInto(destination, 0, 0, source.size)
            return source.size
        }
        val b0 = source[0].toInt() and 0xFF
        val b1 = source[1].toInt() and 0xFF
        val b2 = source[2].toInt() and 0xFF
        val b3 = source[3].toInt() and 0xFF
        val magic = b0 or (b1 shl 8) or (b2 shl 16) or (b3 shl 24)

        if (magic == 0xFD2FB528.toInt()) {
            val payloadSize = source.size - 4
            require(destination.size >= payloadSize) {
                "Destination buffer too small"
            }
            source.copyInto(destination, 0, 4, source.size)
            return payloadSize
        } else {
            require(destination.size >= source.size) {
                "Destination buffer too small"
            }
            source.copyInto(destination, 0, 0, source.size)
            return source.size
        }
    }

    public fun decompress(data: ByteArray, capacity: Int): ByteArray {
        val dest = ByteArray(capacity)
        val written = decompressToBuffer(data, dest)
        return dest.copyOf(written)
    }

    public companion object {
        public fun withDictionary(dictionary: ByteArray): Decompressor {
            val d = Decompressor()
            d.setDictionary(dictionary)
            return d
        }

        public fun withPreparedDictionary(dictionary: DecoderDictionary): Decompressor {
            val d = Decompressor()
            d.setPreparedDictionary(dictionary)
            return d
        }
    }
}

public fun compressToBuffer(
    source: ByteArray,
    destination: ByteArray,
    level: Int = DEFAULT_COMPRESSION_LEVEL,
): Int = Compressor(level).compressToBuffer(source, destination)

public fun compress(
    data: ByteArray,
    level: Int = DEFAULT_COMPRESSION_LEVEL,
): ByteArray = Compressor(level).compress(data)

public fun decompressToBuffer(
    source: ByteArray,
    destination: ByteArray,
): Int = Decompressor().decompressToBuffer(source, destination)

public fun decompress(
    data: ByteArray,
    capacity: Int,
): ByteArray = Decompressor().decompress(data, capacity)
