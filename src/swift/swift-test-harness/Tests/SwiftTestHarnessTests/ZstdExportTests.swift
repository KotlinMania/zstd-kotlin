#if canImport(Testing)
import ExportedKotlinPackages
import Testing
import Zstd

@Suite("Zstd Swift Export Suite")
struct ZstdExportTests {
    private typealias KotlinByteArray = ExportedKotlinPackages.kotlin.ByteArray

    private func toKotlinBytes(_ string: String) -> KotlinByteArray {
        let utf8 = Array(string.utf8)
        let bytes = KotlinByteArray(size: Int32(utf8.count))
        for (i, b) in utf8.enumerated() {
            bytes[Int32(i)] = Int8(bitPattern: b)
        }
        return bytes
    }

    private func toSwiftString(_ bytes: KotlinByteArray) -> String {
        var result: [UInt8] = []
        result.reserveCapacity(Int(bytes.size))
        for i in 0..<bytes.size {
            result.append(UInt8(bitPattern: bytes[i]))
        }
        return String(decoding: result, as: UTF8.self)
    }

    @Test("Compression level constants and round-trip stream/bulk encoding")
    func compressionRoundTrip() {
        #expect(DEFAULT_COMPRESSION_LEVEL == 3)
        #expect(MIN_COMPRESSION_LEVEL == 1)
        #expect(MAX_COMPRESSION_LEVEL == 22)

        let payload = "Hello, Zstandard Kotlin Multiplatform world! Repeatable text repeatable text."
        let input = toKotlinBytes(payload)

        let encoded = stream.encodeAll(source: input, level: DEFAULT_COMPRESSION_LEVEL)
        let decoded = stream.decodeAll(source: encoded)
        #expect(toSwiftString(decoded) == payload)

        let bulkCompressed = bulk.compress(data: input, level: 1)
        let bulkDecompressed = bulk.decompress(data: bulkCompressed, capacity: input.size)
        #expect(toSwiftString(bulkDecompressed) == payload)
    }
}
#elseif canImport(XCTest)
import ExportedKotlinPackages
import XCTest
import Zstd

final class ZstdExportTests: XCTestCase {
    private typealias KotlinByteArray = ExportedKotlinPackages.kotlin.ByteArray

    private func toKotlinBytes(_ string: String) -> KotlinByteArray {
        let utf8 = Array(string.utf8)
        let bytes = KotlinByteArray(size: Int32(utf8.count))
        for (i, b) in utf8.enumerated() {
            bytes[Int32(i)] = Int8(bitPattern: b)
        }
        return bytes
    }

    private func toSwiftString(_ bytes: KotlinByteArray) -> String {
        var result: [UInt8] = []
        result.reserveCapacity(Int(bytes.size))
        for i in 0..<bytes.size {
            result.append(UInt8(bitPattern: bytes[i]))
        }
        return String(decoding: result, as: UTF8.self)
    }

    func testCompressionRoundTrip() {
        XCTAssertEqual(DEFAULT_COMPRESSION_LEVEL, 3)
        XCTAssertEqual(MIN_COMPRESSION_LEVEL, 1)
        XCTAssertEqual(MAX_COMPRESSION_LEVEL, 22)

        let payload = "Hello, Zstandard Kotlin Multiplatform world! Repeatable text repeatable text."
        let input = toKotlinBytes(payload)
        let encoded = stream.encodeAll(source: input, level: DEFAULT_COMPRESSION_LEVEL)
        let decoded = stream.decodeAll(source: encoded)
        XCTAssertEqual(toSwiftString(decoded), payload)
    }
}
#endif

