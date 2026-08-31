# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 13/17 (76.5%)
- **Function parity:** 68/85 matched (target 121) — 80.0%
- **Class/type parity:** 11/14 matched (target 43) — 78.6%
- **Combined symbol parity:** 79/99 matched (target 164) — 79.8%
- **Average inline-code cosine:** 0.47 (function body across 7 matched files)
- **Average documentation cosine:** 0.52 (doc text across 7 matched files)
- **Cheat-zeroed Files:** 2
- **Critical Issues:** 13 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. zio.writer

- **Target:** `zio.Writer [PROVENANCE-FALLBACK]`
- **Similarity:** 0.31
- **Dependents:** 1
- **Priority Score:** 1082106.9
- **Functions:** 12/20 matched (target 14)
- **Missing functions:** `operation`, `offset`, `buffer`, `test_noop`, `test_compress`, `test_compress_with_capacity`, `test_decompress`, `test_decompress_with_capacity`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Tests:** 0/7 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `zstd/src/stream/zio/writer.rs` vs expected `stream/zio/writer.rs`
- **Proposed provenance header:** `// port-lint: source stream/zio/writer.rs` (current: `// port-lint: source zstd/src/stream/zio/writer.rs`)
- **Lint issues:** 1

### 2. bulk.compressor

- **Target:** `bulk.Compressor [PROVENANCE-FALLBACK]`
- **Similarity:** 0.49
- **Dependents:** 1
- **Priority Score:** 1031305.1
- **Functions:** 9/12 matched (target 15)
- **Missing functions:** `context_mut`, `_assert_traits`, `_assert_send`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `zstd/src/bulk/compressor.rs` vs expected `bulk/compressor.rs`
- **Proposed provenance header:** `// port-lint: source bulk/compressor.rs` (current: `// port-lint: source zstd/src/bulk/compressor.rs`)
- **Lint issues:** 1

### 3. zio.reader

- **Target:** `zio.Reader [PROVENANCE-FALLBACK]`
- **Similarity:** 0.44
- **Dependents:** 1
- **Priority Score:** 1021305.6
- **Functions:** 10/11 matched (target 23)
- **Missing functions:** `fill_buf`
- **Types:** 1/2 matched
- **Missing types:** `State`
- **Tests:** 2/2 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `zstd/src/stream/zio/reader.rs` vs expected `stream/zio/reader.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:zstd/src/stream/zio/reader.rs` vs expected `stream/zio/reader.rs`
- **Proposed provenance header:** `// port-lint: source stream/zio/reader.rs` (current: `// port-lint: source zstd/src/stream/zio/reader.rs`)
- **Proposed provenance header:** `// port-lint: tests stream/zio/reader.rs` (current: `// port-lint: tests zstd/src/stream/zio/reader.rs`)
- **Lint issues:** 2

### 4. bulk.decompressor

- **Target:** `bulk.Decompressor [PROVENANCE-FALLBACK]`
- **Similarity:** 0.49
- **Dependents:** 1
- **Priority Score:** 1021205.1
- **Functions:** 9/11 matched (target 10)
- **Missing functions:** `_assert_traits`, `_assert_send`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `zstd/src/bulk/decompressor.rs` vs expected `bulk/decompressor.rs`
- **Proposed provenance header:** `// port-lint: source bulk/decompressor.rs` (current: `// port-lint: source zstd/src/bulk/decompressor.rs`)
- **Lint issues:** 2

### 5. stream.raw

- **Target:** `raw.Raw [PROVENANCE-FALLBACK]`
- **Similarity:** 0.53
- **Dependents:** 0
- **Priority Score:** 32004.7
- **Functions:** 12/13 matched (target 32)
- **Missing functions:** `test_cycle`
- **Types:** 5/7 matched (target 8)
- **Missing types:** `MaybeOwnedCCtx`, `MaybeOwnedDCtx`
- **Tests:** 0/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `zstd/src/stream/raw.rs` vs expected `stream/raw.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:zstd/src/stream/raw.rs` vs expected `stream/raw.rs`
- **Proposed provenance header:** `// port-lint: source stream/raw.rs` (current: `// port-lint: source zstd/src/stream/raw.rs`)
- **Proposed provenance header:** `// port-lint: tests stream/raw.rs` (current: `// port-lint: tests zstd/src/stream/raw.rs`)
- **Lint issues:** 3

### 6. lib

- **Target:** `zstd.Lib [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 20510.0
- **Functions:** 3/5 matched (target 9)
- **Missing functions:** `test_cycle`, `test_cycle_unwrap`
- **Types:** 0/0 matched (target 26)
- **Missing types:** _none_
- **Tests:** 1/3 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `zstd/src/lib.rs` vs expected `lib.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:zstd/src/lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source zstd/src/lib.rs`)
- **Proposed provenance header:** `// port-lint: tests lib.rs` (current: `// port-lint: tests zstd/src/lib.rs`)
- **Lint issues:** 2

### 7. dict

- **Target:** `dict.Dict [PROVENANCE-FALLBACK]`
- **Similarity:** 0.52
- **Dependents:** 0
- **Priority Score:** 1104.8
- **Functions:** 9/9 matched (target 14)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_
- **Tests:** 1/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `zstd/src/dict.rs` vs expected `dict.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:zstd/src/dict.rs` vs expected `dict.rs`
- **Proposed provenance header:** `// port-lint: source dict.rs` (current: `// port-lint: source zstd/src/dict.rs`)
- **Proposed provenance header:** `// port-lint: tests dict.rs` (current: `// port-lint: tests zstd/src/dict.rs`)
- **Lint issues:** 2

### 8. stream.functions

- **Target:** `stream.Functions [PROVENANCE-FALLBACK]`
- **Similarity:** 0.50
- **Dependents:** 0
- **Priority Score:** 405.0
- **Functions:** 4/4 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `zstd/src/stream/functions.rs` vs expected `stream/functions.rs`
- **Proposed provenance header:** `// port-lint: source stream/functions.rs` (current: `// port-lint: source zstd/src/stream/functions.rs`)
- **Lint issues:** 1

### 9. zio.mod

- **Target:** `zio.Mod [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `zstd/src/stream/zio/mod.rs` vs expected `stream/zio/mod.rs`
- **Proposed provenance header:** `// port-lint: source stream/zio/mod.rs` (current: `// port-lint: source zstd/src/stream/zio/mod.rs`)
- **Lint issues:** 1

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Matched

| Source | Target | Path |
|--------|--------|------|
| `write.mod` | `write.Encoder` | `stream/write/mod` |
| `read.mod` | `read.Decoder` | `stream/read/mod` |
| `bulk.mod` | `bulk.Bulk` | `bulk/mod` |
| `stream.mod` | `stream.Mod` | `stream/mod` |

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `bulk.tests` | `bulk.Tests` | 0 | `bulk/tests.rs` | `bulk/Tests.kt` |
| `read.tests` | `stream.read.Tests` | 0 | `stream/read/tests.rs` | `stream/read/Tests.kt` |
| `stream.tests` | `stream.Tests` | 0 | `stream/tests.rs` | `stream/Tests.kt` |
| `write.tests` | `stream.write.Tests` | 0 | `stream/write/tests.rs` | `stream/write/Tests.kt` |

