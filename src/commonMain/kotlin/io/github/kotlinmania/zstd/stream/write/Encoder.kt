// port-lint: source stream/write/mod.rs
package io.github.kotlinmania.zstd.stream.write

import io.github.kotlinmania.zstd.CParameter
import io.github.kotlinmania.zstd.DEFAULT_COMPRESSION_LEVEL
import io.github.kotlinmania.zstd.DParameter
import io.github.kotlinmania.zstd.dict.DecoderDictionary
import io.github.kotlinmania.zstd.dict.EncoderDictionary
import io.github.kotlinmania.zstd.stream.raw.Decoder as RawDecoder
import io.github.kotlinmania.zstd.stream.raw.Encoder as RawEncoder
import io.github.kotlinmania.zstd.stream.zio.Writer as ZioWriter

/**
 * A wrapper around an [Encoder] that finishes the stream on drop / completion.
 */
public class AutoFinishEncoder(
    private var encoder: Encoder?,
    private var onFinish: ((ByteArray) -> Unit)? = null,
) {
    public fun getRef(): ByteArray = encoder?.getRef() ?: ByteArray(0)

    public fun getMut(): ByteArray = encoder?.getMut() ?: ByteArray(0)

    public fun write(data: ByteArray) {
        encoder?.write(data)
    }

    public fun flush() {
        encoder?.flush()
    }
}

/**
 * A wrapper around a [Decoder] that flushes the stream on drop / completion.
 */
public class AutoFlushDecoder(
    private var decoder: Decoder?,
    private var onFlush: (() -> Unit)? = null,
) {
    public fun getRef(): ByteArray = decoder?.getRef() ?: ByteArray(0)

    public fun getMut(): ByteArray = decoder?.getMut() ?: ByteArray(0)

    public fun write(data: ByteArray) {
        decoder?.write(data)
    }

    public fun flush() {
        decoder?.flush()
    }
}

/**
 * An encoder that compresses input data to a byte stream.
 */
public class Encoder(
    private val writer: ZioWriter,
) {
    public fun write(data: ByteArray) {
        writer.write(data)
    }

    public fun flush() {
        writer.flush()
    }

    public fun getRef(): ByteArray = writer.writer()

    public fun getMut(): ByteArray = writer.writerMut()

    public fun finish(): ByteArray = writer.finish()

    public fun tryFinish(): ByteArray = finish()

    public fun doFinish() {
        writer.finish()
    }

    public fun autoFinish(): AutoFinishEncoder = AutoFinishEncoder(this)

    public fun onFinish(callback: (ByteArray) -> Unit): AutoFinishEncoder =
        AutoFinishEncoder(this, callback)

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

        public fun withWriter(writer: ZioWriter): Encoder = Encoder(writer)

        public fun withEncoder(encoder: RawEncoder): Encoder = Encoder(ZioWriter.new(encoder))

        public fun withContext(context: Any? = null): Encoder =
            Encoder(ZioWriter.new(RawEncoder.withContext(context)))

        public fun withPreparedDictionary(dictionary: EncoderDictionary): Encoder {
            val encoder = RawEncoder.withPreparedDictionary(dictionary)
            return Encoder(ZioWriter.new(encoder))
        }

        public fun withRefPrefix(level: Int, refPrefix: ByteArray): Encoder {
            val encoder = RawEncoder.withRefPrefix(level, refPrefix)
            return Encoder(ZioWriter.new(encoder))
        }

        public fun recommendedInputSize(): Int = 128 * 1024
    }
}

/**
 * A decoder that decompresses input data and forwards it to a writer.
 */
public class Decoder(
    private val writer: ZioWriter,
) {
    public fun write(data: ByteArray) {
        writer.write(data)
    }

    public fun flush() {
        writer.flush()
    }

    public fun getRef(): ByteArray = writer.writer()

    public fun getMut(): ByteArray = writer.writerMut()

    public fun intoInner(): ByteArray = writer.intoInner().first

    public fun autoFlush(): AutoFlushDecoder = AutoFlushDecoder(this)

    public fun onFlush(callback: () -> Unit): AutoFlushDecoder =
        AutoFlushDecoder(this, callback)

    public fun setParameter(parameter: DParameter) {
        (writer.operationMut() as? RawDecoder)?.setParameter(parameter)
    }

    public companion object {
        public fun new(): Decoder {
            val decoder = RawDecoder.new()
            return Decoder(ZioWriter.new(decoder))
        }

        public fun withDictionary(dictionary: ByteArray): Decoder {
            val decoder = RawDecoder.withDictionary(dictionary)
            return Decoder(ZioWriter.new(decoder))
        }

        public fun withWriter(writer: ZioWriter): Decoder = Decoder(writer)

        public fun withDecoder(decoder: RawDecoder): Decoder = Decoder(ZioWriter.new(decoder))

        public fun withPreparedDictionary(dictionary: DecoderDictionary): Decoder {
            val decoder = RawDecoder.withPreparedDictionary(dictionary)
            return Decoder(ZioWriter.new(decoder))
        }

        public fun recommendedInputSize(): Int = 128 * 1024
    }
}
