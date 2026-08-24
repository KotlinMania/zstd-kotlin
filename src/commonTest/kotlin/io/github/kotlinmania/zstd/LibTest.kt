// port-lint: tests lib.rs
package io.github.kotlinmania.zstd

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LibTest {
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
