# port-lint Proposed Changes

**Generated:** 2026-08-31
**Source:** tmp/zstd/src
**Target:** src/commonMain/kotlin/io/github/kotlinmania/zstd

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/zstd/stream/zio/Writer.kt` | `// port-lint: source zstd/src/stream/zio/writer.rs` | `// port-lint: source stream/zio/writer.rs` | `stream/zio/writer.rs` | `port-lint provenance header matched only after fallback normalization: 'zstd/src/stream/zio/writer.rs' vs expected 'stream/zio/writer.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/zstd/bulk/Compressor.kt` | `// port-lint: source zstd/src/bulk/compressor.rs` | `// port-lint: source bulk/compressor.rs` | `bulk/compressor.rs` | `port-lint provenance header matched only after fallback normalization: 'zstd/src/bulk/compressor.rs' vs expected 'bulk/compressor.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/zstd/stream/zio/Reader.kt` | `// port-lint: source zstd/src/stream/zio/reader.rs` | `// port-lint: source stream/zio/reader.rs` | `stream/zio/reader.rs` | `port-lint provenance header matched only after fallback normalization: 'zstd/src/stream/zio/reader.rs' vs expected 'stream/zio/reader.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/zstd/stream/zio/ZioTest.kt` | `// port-lint: tests zstd/src/stream/zio/reader.rs` | `// port-lint: tests stream/zio/reader.rs` | `stream/zio/reader.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:zstd/src/stream/zio/reader.rs' vs expected 'stream/zio/reader.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/zstd/bulk/Decompressor.kt` | `// port-lint: source zstd/src/bulk/decompressor.rs` | `// port-lint: source bulk/decompressor.rs` | `bulk/decompressor.rs` | `port-lint provenance header matched only after fallback normalization: 'zstd/src/bulk/decompressor.rs' vs expected 'bulk/decompressor.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/zstd/stream/raw/Raw.kt` | `// port-lint: source zstd/src/stream/raw.rs` | `// port-lint: source stream/raw.rs` | `stream/raw.rs` | `port-lint provenance header matched only after fallback normalization: 'zstd/src/stream/raw.rs' vs expected 'stream/raw.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/zstd/stream/raw/RawTest.kt` | `// port-lint: tests zstd/src/stream/raw.rs` | `// port-lint: tests stream/raw.rs` | `stream/raw.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:zstd/src/stream/raw.rs' vs expected 'stream/raw.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/zstd/Lib.kt` | `// port-lint: source zstd/src/lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'zstd/src/lib.rs' vs expected 'lib.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/zstd/LibTest.kt` | `// port-lint: tests zstd/src/lib.rs` | `// port-lint: tests lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:zstd/src/lib.rs' vs expected 'lib.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/zstd/dict/Dict.kt` | `// port-lint: source zstd/src/dict.rs` | `// port-lint: source dict.rs` | `dict.rs` | `port-lint provenance header matched only after fallback normalization: 'zstd/src/dict.rs' vs expected 'dict.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/zstd/dict/DictTest.kt` | `// port-lint: tests zstd/src/dict.rs` | `// port-lint: tests dict.rs` | `dict.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:zstd/src/dict.rs' vs expected 'dict.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/zstd/stream/Functions.kt` | `// port-lint: source zstd/src/stream/functions.rs` | `// port-lint: source stream/functions.rs` | `stream/functions.rs` | `port-lint provenance header matched only after fallback normalization: 'zstd/src/stream/functions.rs' vs expected 'stream/functions.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/zstd/stream/zio/Mod.kt` | `// port-lint: source zstd/src/stream/zio/mod.rs` | `// port-lint: source stream/zio/mod.rs` | `stream/zio/mod.rs` | `port-lint provenance header matched only after fallback normalization: 'zstd/src/stream/zio/mod.rs' vs expected 'stream/zio/mod.rs'` |
