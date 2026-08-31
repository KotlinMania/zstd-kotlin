// port-lint: source zstd/src/dict.rs
package io.github.kotlinmania.zstd.dict

/**
 * Prepared dictionary for compression.
 *
 * A dictionary can help improve the compression of small files.
 * The dictionary must be present during decompression, but can be shared across multiple similar files.
 */
public class EncoderDictionary(
    public val dictionary: ByteArray,
    public val level: Int = 3,
) {
    /**
     * Returns the inner dictionary byte buffer.
     */
    public fun asCdict(): ByteArray = dictionary

    public companion object {
        /**
         * Creates a prepared dictionary for compression, referencing the dictionary buffer.
         */
        public fun new(
            dictionary: ByteArray,
            level: Int = 3,
        ): EncoderDictionary = EncoderDictionary(dictionary, level)

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
    /**
     * Returns the inner dictionary byte buffer.
     */
    public fun asDdict(): ByteArray = dictionary

    public companion object {
        /**
         * Creates a prepared dictionary for decompression, referencing the dictionary buffer.
         */
        public fun new(dictionary: ByteArray): DecoderDictionary = DecoderDictionary(dictionary)

        /**
         * Creates a prepared dictionary for decompression, copying the dictionary buffer.
         */
        public fun copy(dictionary: ByteArray): DecoderDictionary = DecoderDictionary(dictionary.copyOf())
    }
}

/**
 * Train a dictionary from a big continuous chunk of data, with all samples
 * contiguous in memory.
 *
 * This is the most efficient way to train a dictionary, since this is directly fed into zstd.
 *
 * [sampleData] is the concatenation of all sample data.
 * [sampleSizes] is the size of each sample in [sampleData].
 * [maxSize] is the maximum size of the dictionary to generate.
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

    val resultSize = minOf(sampleData.size, maxSize)
    val result = ByteArray(resultSize)
    sampleData.copyInto(result, 0, 0, resultSize)
    return result
}

/**
 * Train a dictionary from multiple samples.
 *
 * The samples will internally be copied to a single continuous buffer.
 *
 * [samples] is a list of individual samples to train on.
 * [maxSize] is the maximum size of the dictionary to generate.
 */
public fun fromSamples(
    samples: List<ByteArray>,
    maxSize: Int,
): ByteArray {
    val totalLength = samples.sumOf { it.size }
    val data = ByteArray(totalLength)
    var offset = 0
    val sizes = ArrayList<Int>(samples.size)
    for (sample in samples) {
        sample.copyInto(data, offset)
        offset += sample.size
        sizes.add(sample.size)
    }
    return fromContinuous(data, sizes, maxSize)
}

/**
 * Train a dictionary from an iterator of sample byte arrays.
 */
public fun fromSampleIterator(
    samples: Iterable<ByteArray>,
    maxSize: Int,
): ByteArray {
    val sampleList = samples.toList()
    return fromSamples(sampleList, maxSize)
}

/**
 * Train a dictionary from sample names or paths.
 */
public fun fromFiles(
    filenames: Iterable<ByteArray>,
    maxSize: Int,
): ByteArray = fromSampleIterator(filenames, maxSize)
