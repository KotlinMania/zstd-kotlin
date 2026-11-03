// port-lint: source bulk/decompressor.rs
package io.github.kotlinmania.zstd.bulk

import io.github.kotlinmania.zstd.DParameter
import io.github.kotlinmania.zstd.dict.DecoderDictionary

/**
 * Allows to decompress independently multiple blocks of data.
 *
 * This reduces memory usage compared to calling decompress multiple times.
 */
public class Decompressor(
    private var dictionary: ByteArray = ByteArray(0),
) {
    /**
     * Changes the dictionary used by this decompressor.
     *
     * Will affect future compression jobs.
     *
     * Note that using a dictionary means that compression will need to use
     * the same dictionary.
     */
    public fun setDictionary(dictionary: ByteArray) {
        this.dictionary = dictionary.copyOf()
    }

    /**
     * Changes the dictionary used by this decompressor.
     *
     * Note that using a dictionary means that compression will need to use
     * the same dictionary.
     */
    public fun setPreparedDictionary(dictionary: DecoderDictionary) {
        this.dictionary = dictionary.dictionary.copyOf()
    }

    private val parameters = mutableListOf<DParameter>()

    /**
     * Sets a decompression parameter for this decompressor.
     */
    public fun setParameter(parameter: DParameter) {
        parameters.add(parameter)
    }

    /**
     * Sets the maximum back-reference distance.
     *
     * The actual maximum distance is going to be 2^logDistance.
     */
    public fun windowLogMax(logDistance: Int) {
        setParameter(DParameter.WindowLogMax(logDistance))
    }

    /**
     * Decompress a single block of data to the given destination buffer.
     *
     * Returns the number of bytes written, or throws an error if something happened
     * (for instance if the destination buffer was too small).
     */
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

    /**
     * Decompress a block of data, and return the result in a byte array.
     *
     * The decompressed data should be at most capacity bytes,
     * or an error will be returned.
     */
    public fun decompress(data: ByteArray, capacity: Int): ByteArray {
        val dest = ByteArray(capacity)
        val written = decompressToBuffer(data, dest)
        return dest.copyOf(written)
    }

    public companion object {
        /**
         * Creates a new Zstandard decompressor.
         */
        public fun new(): Decompressor = Decompressor()

        /**
         * Creates a new Zstandard decompressor, using the given dictionary.
         */
        public fun withDictionary(dictionary: ByteArray): Decompressor {
            val d = Decompressor()
            d.setDictionary(dictionary)
            return d
        }

        /**
         * Creates a new decompressor using an existing [DecoderDictionary].
         *
         * Note that using a dictionary means that compression will need to use
         * the same dictionary.
         */
        public fun withPreparedDictionary(dictionary: DecoderDictionary): Decompressor {
            val d = Decompressor()
            d.setPreparedDictionary(dictionary)
            return d
        }

        /**
         * Get an upper bound on the decompressed size of data, if available.
         *
         * This can be used to pre-allocate enough capacity for [decompressToBuffer]
         * and is used by [decompress] to ensure that it does not over-allocate if
         * you supply a large capacity.
         */
        public fun upperBound(data: ByteArray): Int? =
            if (data.size >= 4) {
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

private fun assertTraits() {
    fun <T> assertSend(value: T) {
        checkNotNull(value)
    }
    assertSend(Decompressor.new())
}
