// port-lint: source zstd/src/bulk/compressor.rs
package io.github.kotlinmania.zstd.bulk

import io.github.kotlinmania.zstd.CParameter
import io.github.kotlinmania.zstd.DEFAULT_COMPRESSION_LEVEL
import io.github.kotlinmania.zstd.dict.EncoderDictionary

/**
 * Allows to compress independently multiple chunks of data.
 *
 * Each job will be processed entirely in-memory without streaming, so this
 * is most fitting for many small jobs.
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

    public fun includeChecksum(includeChecksum: Boolean) {
        setParameter(CParameter.ChecksumFlag(includeChecksum))
    }

    public fun includeDictid(includeDictid: Boolean) {
        setParameter(CParameter.DictIdFlag(includeDictid))
    }

    public fun includeContentsize(includeContentsize: Boolean) {
        setParameter(CParameter.ContentSizeFlag(includeContentsize))
    }

    public fun longDistanceMatching(longDistanceMatching: Boolean) {
        setParameter(CParameter.EnableLongDistanceMatching(longDistanceMatching))
    }

    public fun setTargetCblockSize(targetSize: Int?) {
        setParameter(CParameter.TargetCBlockSize(targetSize ?: 0))
    }

    public fun windowLog(logDistance: Int) {
        setParameter(CParameter.WindowLog(logDistance))
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
        public fun new(level: Int = DEFAULT_COMPRESSION_LEVEL): Compressor = Compressor(level)

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
