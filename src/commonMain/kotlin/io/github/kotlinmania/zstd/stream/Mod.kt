// port-lint: source stream/mod.rs
package io.github.kotlinmania.zstd.stream

/**
 * Stream module ledger and re-exports for zstd streaming compression and decompression.
 */
public object Mod {
    public fun decodeAll(source: ByteArray): ByteArray = io.github.kotlinmania.zstd.stream.decodeAll(source)

    public fun encodeAll(source: ByteArray, level: Int = io.github.kotlinmania.zstd.DEFAULT_COMPRESSION_LEVEL): ByteArray =
        io.github.kotlinmania.zstd.stream.encodeAll(source, level)

    public fun copyDecode(source: ByteArray, destination: MutableList<Byte>): Unit =
        io.github.kotlinmania.zstd.stream.copyDecode(source, destination)

    public fun copyEncode(
        source: ByteArray,
        destination: MutableList<Byte>,
        level: Int = io.github.kotlinmania.zstd.DEFAULT_COMPRESSION_LEVEL,
    ): Unit = io.github.kotlinmania.zstd.stream.copyEncode(source, destination, level)
}
