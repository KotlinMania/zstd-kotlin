import Testing
import Zstd

@Suite("Zstd Swift Export Suite")
struct ZstdExportTests {
    @Test("Swift module loads cleanly")
    func swiftModuleLoads() {
        #expect(Bool(true), "Zstd swift module imported cleanly")
    }
}
