// port-lint: source lib.rs
package io.github.kotlinmania.zstd

/**
 * Default compression level.
 */
public const val DEFAULT_COMPRESSION_LEVEL: Int = 3

/**
 * Minimum supported compression level.
 */
public const val MIN_COMPRESSION_LEVEL: Int = 1

/**
 * Maximum supported compression level.
 */
public const val MAX_COMPRESSION_LEVEL: Int = 22

/**
 * The accepted range of compression levels.
 */
public fun compressionLevelRange(): IntRange = MIN_COMPRESSION_LEVEL..MAX_COMPRESSION_LEVEL

/**
 * Format of a Zstandard frame.
 */
public enum class FrameFormat {
    /**
     * Standard Zstandard frame with 4-byte magic number header.
     */
    One,

    /**
     * Frame without the 4-byte magic number header.
     */
    Magicless,
}

/**
 * Compression parameters for Zstandard encoder.
 */
public sealed class CParameter {
    public data class CompressionLevel(
        public val level: Int,
    ) : CParameter()

    public data class WindowLog(
        public val logDistance: Int,
    ) : CParameter()

    public data class HashLog(
        public val logLength: Int,
    ) : CParameter()

    public data class ChainLog(
        public val logLength: Int,
    ) : CParameter()

    public data class SearchLog(
        public val logLength: Int,
    ) : CParameter()

    public data class MinMatch(
        public val length: Int,
    ) : CParameter()

    public data class TargetLength(
        public val length: Int,
    ) : CParameter()

    public data class Strategy(
        public val strategy: Int,
    ) : CParameter()

    public data class EnableLongDistanceMatching(
        public val enabled: Boolean,
    ) : CParameter()

    public data class LdmHashLog(
        public val logLength: Int,
    ) : CParameter()

    public data class LdmMinMatch(
        public val length: Int,
    ) : CParameter()

    public data class LdmBucketSizeLog(
        public val logLength: Int,
    ) : CParameter()

    public data class LdmHashRateLog(
        public val logLength: Int,
    ) : CParameter()

    public data class ContentSizeFlag(
        public val enabled: Boolean,
    ) : CParameter()

    public data class ChecksumFlag(
        public val enabled: Boolean,
    ) : CParameter()

    public data class DictIdFlag(
        public val enabled: Boolean,
    ) : CParameter()

    public data class NbWorkers(
        public val workers: Int,
    ) : CParameter()

    public data class JobSize(
        public val size: Int,
    ) : CParameter()

    public data class OverlapLog(
        public val logLength: Int,
    ) : CParameter()

    public data class TargetCBlockSize(
        public val size: Int,
    ) : CParameter()

    public data class Format(
        public val format: FrameFormat,
    ) : CParameter()
}

/**
 * Decompression parameters for Zstandard decoder.
 */
public sealed class DParameter {
    public data class WindowLogMax(
        public val logDistance: Int,
    ) : DParameter()

    public data class Format(
        public val format: FrameFormat,
    ) : DParameter()
}

/**
 * Returns an exception representing the specified error code.
 */
public fun mapErrorCode(code: Int): Exception = IllegalArgumentException("Zstandard error code $code")

/**
 * Decompress from the given source.
 */
public fun decodeAll(source: ByteArray): ByteArray = io.github.kotlinmania.zstd.stream.decodeAll(source)

/**
 * Compress all data from the given source.
 */
public fun encodeAll(source: ByteArray, level: Int = DEFAULT_COMPRESSION_LEVEL): ByteArray =
    io.github.kotlinmania.zstd.stream.encodeAll(source, level)


