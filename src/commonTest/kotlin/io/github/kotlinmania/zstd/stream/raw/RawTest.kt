// port-lint: tests stream/raw.rs
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

    @Test
    fun testCycle() {
        val encoder = Encoder.new(1)
        val decoder = Decoder.new()

        val inputData = "AbcdefAbcdefabcdef".encodeToByteArray()
        val input = InBuffer.around(inputData)

        val outputArray = ByteArray(128)
        val output = OutBuffer.around(outputArray)

        while (true) {
            encoder.run(input, output)
            if (input.pos == input.src.size) {
                break
            }
        }
        encoder.finish(output, true)

        val encodedSlice = output.asSlice()

        val decodeInput = InBuffer.around(encodedSlice)
        val decodeOutputArray = ByteArray(128)
        val decodeOutput = OutBuffer.around(decodeOutputArray)

        while (true) {
            decoder.run(decodeInput, decodeOutput)
            if (decodeInput.pos == decodeInput.src.size) {
                break
            }
        }

        assertEquals(inputData.toList(), decodeOutput.asSlice().toList())
    }
}
