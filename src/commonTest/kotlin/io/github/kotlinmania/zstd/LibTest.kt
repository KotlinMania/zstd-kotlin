// port-lint: tests zstd/src/lib.rs
package io.github.kotlinmania.zstd

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LibTest {
    fun testCycle(data: ByteArray, f: (ByteArray) -> ByteArray, g: (ByteArray) -> ByteArray) {
        val mid = f(data)
        val end = g(mid)
        kotlin.test.assertContentEquals(data, end)
    }

    fun testCycleUnwrap(data: ByteArray, f: (ByteArray) -> ByteArray, g: (ByteArray) -> ByteArray) {
        testCycle(data, f, g)
    }

    @Test
    fun defaultCompressionLevelInRange() {
        val range = compressionLevelRange()
        assertTrue(DEFAULT_COMPRESSION_LEVEL in range)
        assertEquals(1, MIN_COMPRESSION_LEVEL)
        assertEquals(22, MAX_COMPRESSION_LEVEL)
    }

    @Test
    fun frameFormatVariants() {
        val formats = FrameFormat.values()
        assertEquals(2, formats.size)
        assertEquals(FrameFormat.One, FrameFormat.valueOf("One"))
        assertEquals(FrameFormat.Magicless, FrameFormat.valueOf("Magicless"))
    }

    @Test
    fun testMapErrorCode() {
        val ex = mapErrorCode(42)
        assertTrue(ex.message?.contains("42") == true)
    }
}
