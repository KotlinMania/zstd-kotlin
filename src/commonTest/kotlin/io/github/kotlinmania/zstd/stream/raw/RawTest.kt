// port-lint: tests zstd/src/stream/raw.rs
package io.github.kotlinmania.zstd.stream.raw

import kotlin.test.Test
import kotlin.test.assertEquals

class RawTest {
    @Test
    fun testNoOpOperation() {
        val noOp = NoOp()
        val src = byteArrayOf(1, 2, 3, 4, 5)
        val dst = ByteArray(5)
        val status = noOp.runOnBuffers(src, dst)
        assertEquals(0, status.remaining)
        assertEquals(5, status.bytesRead)
        assertEquals(5, status.bytesWritten)
        assertEquals(src.toList(), dst.toList())
    }

    @Test
    fun testInBufferAndOutBuffer() {
        val inBuf = InBuffer.around(byteArrayOf(1, 2, 3))
        assertEquals(0, inBuf.pos)
        assertEquals(3, inBuf.src.size)

        val outBuf = OutBuffer.around(ByteArray(10))
        assertEquals(0, outBuf.pos)
        assertEquals(10, outBuf.capacity())
    }
}
