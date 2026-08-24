# port-lint Proposed Changes

**Generated:** 2026-08-24
**Source:** tmp/zstd
**Target:** src

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `commonMain/kotlin/io/github/kotlinmania/zstd/stream/zio/Writer.kt` | `// port-lint: source stream/zio/writer.rs` | `// port-lint: source stream/zio/writer.rs` | `stream/zio/writer.rs` | `port-lint provenance header matched only after fallback normalization: 'stream/zio/writer.rs' vs expected 'stream/zio/writer.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/zstd/stream/zio/Reader.kt` | `// port-lint: source stream/zio/reader.rs` | `// port-lint: source stream/zio/reader.rs` | `stream/zio/reader.rs` | `port-lint provenance header matched only after fallback normalization: 'stream/zio/reader.rs' vs expected 'stream/zio/reader.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/zstd/bulk/Compressor.kt` | `// port-lint: source bulk/compressor.rs` | `// port-lint: source bulk/compressor.rs` | `bulk/compressor.rs` | `port-lint provenance header matched only after fallback normalization: 'bulk/compressor.rs' vs expected 'bulk/compressor.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/zstd/bulk/Decompressor.kt` | `// port-lint: source bulk/decompressor.rs` | `// port-lint: source bulk/decompressor.rs` | `bulk/decompressor.rs` | `port-lint provenance header matched only after fallback normalization: 'bulk/decompressor.rs' vs expected 'bulk/decompressor.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/zstd/stream/write/Encoder.kt` | `// port-lint: source stream/write/mod.rs` | `// port-lint: source stream/write/mod.rs` | `stream/write/mod.rs` | `port-lint provenance header matched only after fallback normalization: 'stream/write/mod.rs' vs expected 'stream/write/mod.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/zstd/stream/read/Decoder.kt` | `// port-lint: source stream/read/mod.rs` | `// port-lint: source stream/read/mod.rs` | `stream/read/mod.rs` | `port-lint provenance header matched only after fallback normalization: 'stream/read/mod.rs' vs expected 'stream/read/mod.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/zstd/stream/raw/Raw.kt` | `// port-lint: source stream/raw.rs` | `// port-lint: source stream/raw.rs` | `stream/raw.rs` | `port-lint provenance header matched only after fallback normalization: 'stream/raw.rs' vs expected 'stream/raw.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/zstd/Lib.kt` | `// port-lint: source lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'lib.rs' vs expected 'lib.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/zstd/LibTest.kt` | `// port-lint: tests lib.rs` | `// port-lint: tests lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:lib.rs' vs expected 'lib.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/zstd/dict/Dict.kt` | `// port-lint: source dict.rs` | `// port-lint: source dict.rs` | `dict.rs` | `port-lint provenance header matched only after fallback normalization: 'dict.rs' vs expected 'dict.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/zstd/dict/DictTest.kt` | `// port-lint: tests dict.rs` | `// port-lint: tests dict.rs` | `dict.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:dict.rs' vs expected 'dict.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/zstd/bulk/Bulk.kt` | `// port-lint: source bulk/mod.rs` | `// port-lint: source bulk/mod.rs` | `bulk/mod.rs` | `port-lint provenance header matched only after fallback normalization: 'bulk/mod.rs' vs expected 'bulk/mod.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/zstd/stream/Functions.kt` | `// port-lint: source stream/functions.rs` | `// port-lint: source stream/functions.rs` | `stream/functions.rs` | `port-lint provenance header matched only after fallback normalization: 'stream/functions.rs' vs expected 'stream/functions.rs'` |
