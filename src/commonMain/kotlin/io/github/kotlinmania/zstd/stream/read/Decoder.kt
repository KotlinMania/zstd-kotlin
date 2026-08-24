// port-lint: source stream/read/mod.rs
package io.github.kotlinmania.zstd.stream.read

import io.github.kotlinmania.zstd.DParameter
import io.github.kotlinmania.zstd.dict.DecoderDictionary
import io.github.kotlinmania.zstd.stream.raw.Decoder as RawDecoder
import io.github.kotlinmania.zstd.stream.zio.Reader as ZioReader

/**
 * A decoder that decompresses input data from a byte stream.
 */
public class Decoder(
    private val reader: ZioReader,
) {
    public fun singleFrame(): Decoder {
        reader.setSingleFrame()
        (reader.operationMut() as? RawDecoder)?.singleFrame = true
        return this
    }

    public fun read(destination: ByteArray): Int = reader.read(destination)

    public fun readAll(): ByteArray = reader.readAll()

    public fun setParameter(parameter: DParameter) {
        (reader.operationMut() as? RawDecoder)?.setParameter(parameter)
    }

    public companion object {
        public fun new(source: ByteArray): Decoder {
            val decoder = RawDecoder.new()
            return Decoder(ZioReader.new(source, decoder))
        }

        public fun withDictionary(source: ByteArray, dictionary: ByteArray): Decoder {
            val decoder = RawDecoder.withDictionary(dictionary)
            return Decoder(ZioReader.new(source, decoder))
        }

        public fun withPreparedDictionary(source: ByteArray, dictionary: DecoderDictionary): Decoder {
            val decoder = RawDecoder.withPreparedDictionary(dictionary)
            return Decoder(ZioReader.new(source, decoder))
        }

        public fun withRefPrefix(source: ByteArray, refPrefix: ByteArray): Decoder {
            val decoder = RawDecoder.withRefPrefix(refPrefix)
            return Decoder(ZioReader.new(source, decoder))
        }
    }
}
