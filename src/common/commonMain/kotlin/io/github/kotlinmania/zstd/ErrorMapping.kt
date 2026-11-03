package io.github.kotlinmania.zstd

/**
 * Returns an exception representing the specified error code.
 */
public fun mapErrorCode(code: Int): Exception = IllegalArgumentException("Zstandard error code $code")
