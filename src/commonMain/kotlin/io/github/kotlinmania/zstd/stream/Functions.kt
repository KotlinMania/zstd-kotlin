// port-lint: source zstd/src/stream/functions.rs
package io.github.kotlinmania.zstd.stream

import io.github.kotlinmania.zstd.DEFAULT_COMPRESSION_LEVEL
import io.github.kotlinmania.zstd.stream.read.Decoder
import io.github.kotlinmania.zstd.stream.write.Encoder

/**
 * Decompress from the given source as if using a [Decoder].
 *
 * The input data must be in the zstd frame format.
 */
public fun decodeAll(source: ByteArray): ByteArray {
    val decoder = Decoder.new(source)
    return decoder.readAll()
}

/**
 * Decompress from the given source as if using a [Decoder], copying to destination.
 */
public fun copyDecode(source: ByteArray, destination: MutableList<Byte>) {
    val decoded = decodeAll(source)
    for (b in decoded) {
        destination.add(b)
    }
}

/**
 * Compress all data from the given source as if using an [Encoder].
 *
 * Result will be in the zstd frame format.
 */
public fun encodeAll(source: ByteArray, level: Int = DEFAULT_COMPRESSION_LEVEL): ByteArray {
    val encoder = Encoder.new(level)
    encoder.write(source)
    return encoder.finish()
}

/**
 * Compress all data from the given source as if using an [Encoder], copying to destination.
 */
public fun copyEncode(source: ByteArray, destination: MutableList<Byte>, level: Int = DEFAULT_COMPRESSION_LEVEL) {
    val encoded = encodeAll(source, level)
    for (b in encoded) {
        destination.add(b)
    }
}
