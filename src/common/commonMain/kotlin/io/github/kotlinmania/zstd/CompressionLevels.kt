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
