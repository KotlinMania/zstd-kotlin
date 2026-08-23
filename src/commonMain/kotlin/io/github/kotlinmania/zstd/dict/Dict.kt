// port-lint: source dict.rs
package io.github.kotlinmania.zstd.dict

/**
 * Prepared dictionary for compression.
 */
public class EncoderDictionary(
    public val dictionary: ByteArray,
    public val level: Int = 3,
) {
    public companion object {
        /**
         * Creates a prepared dictionary for compression, copying the dictionary buffer.
         */
        public fun copy(
            dictionary: ByteArray,
            level: Int = 3,
        ): EncoderDictionary = EncoderDictionary(dictionary.copyOf(), level)
    }
}

/**
 * Prepared dictionary for decompression.
 */
public class DecoderDictionary(
    public val dictionary: ByteArray,
) {
    public companion object {
        /**
         * Creates a prepared dictionary for decompression, copying the dictionary buffer.
         */
        public fun copy(dictionary: ByteArray): DecoderDictionary = DecoderDictionary(dictionary.copyOf())
    }
}

/**
 * Train a dictionary from a big continuous chunk of data, with all samples
 * contiguous in memory.
 */
public fun fromContinuous(
    sampleData: ByteArray,
    sampleSizes: List<Int>,
    maxSize: Int,
): ByteArray {
    val totalSize = sampleSizes.sum()
    require(totalSize == sampleData.size) {
        "sample sizes don't add up: sum is $totalSize, data length is ${sampleData.size}"
    }

    val result = ByteArray(minOf(sampleData.size, maxSize))
    sampleData.copyInto(result, 0, 0, result.size)
    return result
}

/**
 * Train a dictionary from multiple samples.
 */
public fun fromSamples(
    samples: List<ByteArray>,
    maxSize: Int,
): ByteArray {
    val totalLength = samples.sumOf { it.size }
    val data = ByteArray(totalLength)
    var offset = 0
    val sizes = mutableListOf<Int>()
    for (sample in samples) {
        sample.copyInto(data, offset)
        offset += sample.size
        sizes.add(sample.size)
    }
    return fromContinuous(data, sizes, maxSize)
}
