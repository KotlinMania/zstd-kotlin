// port-lint: source stream/read/mod.rs
package io.github.kotlinmania.zstd.stream.read

import io.github.kotlinmania.zstd.CParameter
import io.github.kotlinmania.zstd.DEFAULT_COMPRESSION_LEVEL
import io.github.kotlinmania.zstd.DParameter
import io.github.kotlinmania.zstd.dict.DecoderDictionary
import io.github.kotlinmania.zstd.dict.EncoderDictionary
import io.github.kotlinmania.zstd.stream.raw.Decoder as RawDecoder
import io.github.kotlinmania.zstd.stream.raw.Encoder as RawEncoder
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

    public fun getRef(): ByteArray = reader.reader()

    public fun getMut(): ByteArray = reader.readerMut()

    public fun finish(): ByteArray = reader.intoInner()

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

        public fun withBuffer(source: ByteArray): Decoder = new(source)

        public fun withDictionary(source: ByteArray, dictionary: ByteArray): Decoder {
            val decoder = RawDecoder.withDictionary(dictionary)
            return Decoder(ZioReader.new(source, decoder))
        }

        public fun withContext(source: ByteArray, context: Any? = null): Decoder =
            Decoder(ZioReader.new(source, RawDecoder.withContext(context)))

        public fun withPreparedDictionary(source: ByteArray, dictionary: DecoderDictionary): Decoder {
            val decoder = RawDecoder.withPreparedDictionary(dictionary)
            return Decoder(ZioReader.new(source, decoder))
        }

        public fun withRefPrefix(source: ByteArray, refPrefix: ByteArray): Decoder {
            val decoder = RawDecoder.withRefPrefix(refPrefix)
            return Decoder(ZioReader.new(source, decoder))
        }

        public fun recommendedOutputSize(): Int = 128 * 1024
    }
}

/**
 * An encoder that compresses input data from a byte stream.
 */
public class Encoder(
    private val reader: ZioReader,
) {
    public fun getRef(): ByteArray = reader.reader()

    public fun getMut(): ByteArray = reader.readerMut()

    public fun finish(): ByteArray = reader.intoInner()

    public fun read(destination: ByteArray): Int = reader.read(destination)

    public fun readAll(): ByteArray = reader.readAll()

    public fun flush(out: ByteArray): Int = reader.flush(out)

    public fun setParameter(parameter: CParameter) {
        (reader.operationMut() as? RawEncoder)?.setParameter(parameter)
    }

    public companion object {
        public fun new(source: ByteArray, level: Int = DEFAULT_COMPRESSION_LEVEL): Encoder {
            val encoder = RawEncoder.new(level)
            return Encoder(ZioReader.new(source, encoder))
        }

        public fun withBuffer(source: ByteArray, level: Int = DEFAULT_COMPRESSION_LEVEL): Encoder =
            new(source, level)

        public fun withDictionary(
            source: ByteArray,
            level: Int,
            dictionary: ByteArray,
        ): Encoder {
            val encoder = RawEncoder.withDictionary(level, dictionary)
            return Encoder(ZioReader.new(source, encoder))
        }

        public fun withPreparedDictionary(
            source: ByteArray,
            dictionary: EncoderDictionary,
        ): Encoder {
            val encoder = RawEncoder.withPreparedDictionary(dictionary)
            return Encoder(ZioReader.new(source, encoder))
        }

        public fun recommendedOutputSize(): Int = 128 * 1024
    }
}
