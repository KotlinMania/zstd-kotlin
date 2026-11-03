#if canImport(Testing)
import Testing
import Zstd

@Suite("Zstd Swift Export Suite")
struct ZstdExportTests {
    @Test("Swift module loads cleanly")
    func swiftModuleLoads() {
        #expect(Bool(true), "Zstd swift module imported cleanly")
    }
}
#elseif canImport(XCTest)
import XCTest
import Zstd

final class ZstdExportTests: XCTestCase {
    func testSwiftModuleLoads() {
        XCTAssertTrue(true, "Zstd swift module imported cleanly")
    }
}
#endif

