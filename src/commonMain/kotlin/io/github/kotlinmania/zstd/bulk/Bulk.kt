// port-lint: source bulk/mod.rs
package io.github.kotlinmania.zstd.bulk

import io.github.kotlinmania.zstd.DEFAULT_COMPRESSION_LEVEL

/**
 * Compresses a single block of data to the given destination buffer.
 *
 * Returns the number of bytes written, or throws an error if the destination buffer was too small.
 *
 * A level of `0` uses Zstandard default (currently `3`).
 */
public fun compressToBuffer(
    source: ByteArray,
    destination: ByteArray,
    level: Int = DEFAULT_COMPRESSION_LEVEL,
): Int {
    return Compressor.new(level).compressToBuffer(source, destination)
}

/**
 * Compresses a block of data and returns the compressed result.
 *
 * A level of `0` uses Zstandard default (currently `3`).
 */
public fun compress(
    data: ByteArray,
    level: Int = DEFAULT_COMPRESSION_LEVEL,
): ByteArray {
    return Compressor.new(level).compress(data)
}

/**
 * Decompress a single block of data to the given destination buffer.
 *
 * Returns the number of bytes written, or throws an error if the destination buffer was too small.
 */
public fun decompressToBuffer(
    source: ByteArray,
    destination: ByteArray,
): Int {
    return Decompressor.new().decompressToBuffer(source, destination)
}

/**
 * Decompresses a block of data and returns the decompressed result.
 *
 * The decompressed data should be at most `capacity` bytes, or an error will be returned.
 */
public fun decompress(
    data: ByteArray,
    capacity: Int,
): ByteArray {
    return Decompressor.new().decompress(data, capacity)
}

