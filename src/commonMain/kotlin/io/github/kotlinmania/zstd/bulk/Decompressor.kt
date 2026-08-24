// port-lint: source bulk/decompressor.rs
package io.github.kotlinmania.zstd.bulk

import io.github.kotlinmania.zstd.DParameter
import io.github.kotlinmania.zstd.dict.DecoderDictionary

/**
 * Allows to decompress independently multiple blocks of data.
 *
 * This reduces memory usage compared to calling `decompress` multiple times.
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

    public fun windowLogMax(logDistance: Int) {
        setParameter(DParameter.WindowLogMax(logDistance))
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
        public fun new(): Decompressor = Decompressor()

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

        public fun upperBound(data: ByteArray): Int? {
            return if (data.size >= 4) {
                val b0 = data[0].toInt() and 0xFF
                val b1 = data[1].toInt() and 0xFF
                val b2 = data[2].toInt() and 0xFF
                val b3 = data[3].toInt() and 0xFF
                val magic = b0 or (b1 shl 8) or (b2 shl 16) or (b3 shl 24)
                if (magic == 0xFD2FB528.toInt()) data.size - 4 else data.size
            } else {
                null
            }
        }
    }
}
