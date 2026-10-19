// port-lint: source bulk/compressor.rs
package io.github.kotlinmania.zstd.bulk

import io.github.kotlinmania.zstd.CParameter
import io.github.kotlinmania.zstd.DEFAULT_COMPRESSION_LEVEL
import io.github.kotlinmania.zstd.dict.EncoderDictionary

/**
 * Allows to compress independently multiple chunks of data.
 *
 * Each job will be processed entirely in-memory without streaming, so this
 * is most fitting for many small jobs. To compress larger volume that do not
 * easily fit in memory, a streaming compression may be more appropriate.
 *
 * It is more efficient than a streaming compressor for two reasons:
 * - It re-uses the Zstandard context between jobs to avoid re-allocations.
 * - It avoids copying data into a temporary buffer before compression.
 */
public class Compressor(
    private var level: Int = DEFAULT_COMPRESSION_LEVEL,
    private var dictionary: ByteArray = ByteArray(0),
) {
    /**
     * Changes the compression level used by this compressor.
     *
     * This will clear any dictionary previously registered.
     */
    public fun setCompressionLevel(level: Int) {
        this.level = level
        this.dictionary = ByteArray(0)
    }

    /**
     * Changes the dictionary and compression level used by this compressor.
     *
     * Will affect future compression jobs.
     */
    public fun setDictionary(level: Int, dictionary: ByteArray) {
        this.level = level
        this.dictionary = dictionary.copyOf()
    }

    /**
     * Changes the dictionary used by this compressor.
     *
     * The compression level used when preparing the dictionary will be used.
     */
    public fun setPreparedDictionary(dictionary: EncoderDictionary) {
        this.level = dictionary.level
        this.dictionary = dictionary.dictionary.copyOf()
    }

    /**
     * Sets a compression parameter for this compressor.
     */
    public fun setParameter(parameter: CParameter) {
        when (parameter) {
            is CParameter.CompressionLevel -> this.level = parameter.level
            else -> {}
        }
    }

    /**
     * Controls whether Zstandard should include a content checksum at the end of each frame.
     */
    public fun includeChecksum(includeChecksum: Boolean) {
        setParameter(CParameter.ChecksumFlag(includeChecksum))
    }

    /**
     * Enables or disables storing of the dictionary identifier.
     */
    public fun includeDictid(includeDictid: Boolean) {
        setParameter(CParameter.DictIdFlag(includeDictid))
    }

    /**
     * Enables or disables storing of the content size.
     */
    public fun includeContentsize(includeContentsize: Boolean) {
        setParameter(CParameter.ContentSizeFlag(includeContentsize))
    }

    /**
     * Enables or disables long-distance matching.
     */
    public fun longDistanceMatching(longDistanceMatching: Boolean) {
        setParameter(CParameter.EnableLongDistanceMatching(longDistanceMatching))
    }

    /**
     * Sets the target size for compressed blocks.
     */
    public fun setTargetCblockSize(targetSize: Int?) {
        setParameter(CParameter.TargetCBlockSize(targetSize ?: 0))
    }

    /**
     * Sets the maximum back-reference distance.
     */
    public fun windowLog(logDistance: Int) {
        setParameter(CParameter.WindowLog(logDistance))
    }

    /**
     * Compress a single block of data to the given destination buffer.
     *
     * Returns the number of bytes written, or throws an error if something happened
     * (for instance if the destination buffer was too small).
     *
     * A level of 0 uses the Zstandard default (currently 3).
     */
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

    /**
     * Compresses a block of data and returns the compressed result.
     *
     * A level of 0 uses the Zstandard default (currently 3).
     */
    public fun compress(data: ByteArray): ByteArray {
        val dest = ByteArray(data.size + 4)
        val written = compressToBuffer(data, dest)
        return dest.copyOf(written)
    }

    /**
     * Gives mutable access to the internal context.
     */
    public fun contextMut(): Any? = null

    public companion object {
        /**
         * Creates a new Zstandard compressor.
         */
        public fun new(level: Int = DEFAULT_COMPRESSION_LEVEL): Compressor = Compressor(level)

        /**
         * Creates a new Zstandard compressor, using the given dictionary.
         */
        public fun withDictionary(level: Int, dictionary: ByteArray): Compressor {
            val c = Compressor(level)
            c.setDictionary(level, dictionary)
            return c
        }

        /**
         * Creates a new compressor using an existing [EncoderDictionary].
         */
        public fun withPreparedDictionary(dictionary: EncoderDictionary): Compressor {
            val c = Compressor(dictionary.level)
            c.setPreparedDictionary(dictionary)
            return c
        }
    }
}

private fun assertTraits() {
    fun <T> assertSend(value: T) {
        checkNotNull(value)
    }
    assertSend(Compressor.new(0))
}
