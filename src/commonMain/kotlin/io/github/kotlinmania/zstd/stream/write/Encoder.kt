// port-lint: source zstd/src/stream/write/mod.rs
package io.github.kotlinmania.zstd.stream.write

import io.github.kotlinmania.zstd.CParameter
import io.github.kotlinmania.zstd.DEFAULT_COMPRESSION_LEVEL
import io.github.kotlinmania.zstd.dict.EncoderDictionary
import io.github.kotlinmania.zstd.stream.raw.Encoder as RawEncoder
import io.github.kotlinmania.zstd.stream.zio.Writer as ZioWriter

/**
 * An encoder that compresses input data to a byte stream.
 */
public class Encoder(
    private val writer: ZioWriter,
) {
    public fun write(data: ByteArray) {
        writer.write(data)
    }

    public fun finish(): ByteArray = writer.finish()

    public fun setParameter(parameter: CParameter) {
        (writer.operationMut() as? RawEncoder)?.setParameter(parameter)
    }

    public companion object {
        public fun new(level: Int = DEFAULT_COMPRESSION_LEVEL): Encoder {
            val encoder = RawEncoder.new(level)
            return Encoder(ZioWriter.new(encoder))
        }

        public fun withDictionary(level: Int, dictionary: ByteArray): Encoder {
            val encoder = RawEncoder.withDictionary(level, dictionary)
            return Encoder(ZioWriter.new(encoder))
        }

        public fun withPreparedDictionary(dictionary: EncoderDictionary): Encoder {
            val encoder = RawEncoder.withPreparedDictionary(dictionary)
            return Encoder(ZioWriter.new(encoder))
        }

        public fun withRefPrefix(level: Int, refPrefix: ByteArray): Encoder {
            val encoder = RawEncoder.withRefPrefix(level, refPrefix)
            return Encoder(ZioWriter.new(encoder))
        }
    }
}
