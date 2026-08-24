# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 11/24 (45.8%)
- **Function parity:** 71/166 matched (target 108) — 42.8%
- **Class/type parity:** 13/23 matched (target 42) — 56.5%
- **Combined symbol parity:** 84/189 matched (target 150) — 44.4%
- **Average inline-code cosine:** 0.40 (function body across 8 matched files)
- **Average documentation cosine:** 0.49 (doc text across 8 matched files)
- **Cheat-zeroed Files:** 3
- **Critical Issues:** 11 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. zio.writer

- **Target:** `zio.Writer [PROVENANCE-FALLBACK]`
- **Similarity:** 0.16
- **Dependents:** 1
- **Priority Score:** 1152108.4
- **Functions:** 5/20 matched (target 5)
- **Missing functions:** `with_output_buffer`, `with_buffer`, `write_from_offset`, `into_inner`, `writer`, `writer_mut`, `operation`, `offset`, `buffer`, `flush`, `test_noop`, `test_compress`, `test_compress_with_capacity`, `test_decompress`, `test_decompress_with_capacity`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Tests:** 0/7 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `stream/zio/writer.rs` vs expected `stream/zio/writer.rs`
- **Proposed provenance header:** `// port-lint: source stream/zio/writer.rs` (current: `// port-lint: source stream/zio/writer.rs`)
- **Lint issues:** 1

### 2. zio.reader

- **Target:** `zio.Reader [PROVENANCE-FALLBACK]`
- **Similarity:** 0.33
- **Dependents:** 1
- **Priority Score:** 1071306.8
- **Functions:** 5/11 matched (target 6)
- **Missing functions:** `reader_mut`, `reader`, `into_inner`, `fill_buf`, `test_noop`, `test_compress`
- **Types:** 1/2 matched (target 1)
- **Missing types:** `State`
- **Tests:** 0/2 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `stream/zio/reader.rs` vs expected `stream/zio/reader.rs`
- **Proposed provenance header:** `// port-lint: source stream/zio/reader.rs` (current: `// port-lint: source stream/zio/reader.rs`)
- **Lint issues:** 1

### 3. bulk.compressor

- **Target:** `bulk.Compressor [PROVENANCE-FALLBACK]`
- **Similarity:** 0.49
- **Dependents:** 1
- **Priority Score:** 1031305.1
- **Functions:** 9/12 matched (target 15)
- **Missing functions:** `context_mut`, `_assert_traits`, `_assert_send`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `bulk/compressor.rs` vs expected `bulk/compressor.rs`
- **Proposed provenance header:** `// port-lint: source bulk/compressor.rs` (current: `// port-lint: source bulk/compressor.rs`)
- **Lint issues:** 1

### 4. bulk.decompressor

- **Target:** `bulk.Decompressor [PROVENANCE-FALLBACK]`
- **Similarity:** 0.49
- **Dependents:** 1
- **Priority Score:** 1021205.1
- **Functions:** 9/11 matched (target 10)
- **Missing functions:** `_assert_traits`, `_assert_send`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `bulk/decompressor.rs` vs expected `bulk/decompressor.rs`
- **Proposed provenance header:** `// port-lint: source bulk/decompressor.rs` (current: `// port-lint: source bulk/decompressor.rs`)
- **Lint issues:** 2

### 5. write.mod

- **Target:** `write.Encoder [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 212810.0
- **Functions:** 6/24 matched (target 7)
- **Missing functions:** `get_ref`, `get_mut`, `drop`, `flush`, `with_writer`, `with_encoder`, `with_context`, `auto_finish`, `on_finish`, `try_finish`, `do_finish`, `recommended_input_size`, `with_decoder`, `into_inner`, `auto_flush`, `on_flush`, `_assert_traits`, `_assert_send`
- **Types:** 1/4 matched (target 1)
- **Missing types:** `Decoder`, `AutoFinishEncoder`, `AutoFlushDecoder`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `stream/write/mod.rs` vs expected `stream/write/mod.rs`
- **Proposed provenance header:** `// port-lint: source stream/write/mod.rs` (current: `// port-lint: source stream/write/mod.rs`)
- **Lint issues:** 1

### 6. read.mod

- **Target:** `read.Decoder [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 101710.0
- **Functions:** 6/15 matched (target 8)
- **Missing functions:** `with_buffer`, `with_context`, `recommended_output_size`, `get_ref`, `get_mut`, `finish`, `flush`, `_assert_traits`, `_assert_send`
- **Types:** 1/2 matched (target 1)
- **Missing types:** `Encoder`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `stream/read/mod.rs` vs expected `stream/read/mod.rs`
- **Proposed provenance header:** `// port-lint: source stream/read/mod.rs` (current: `// port-lint: source stream/read/mod.rs`)
- **Lint issues:** 1

### 7. stream.raw

- **Target:** `raw.Raw [PROVENANCE-FALLBACK]`
- **Similarity:** 0.48
- **Dependents:** 0
- **Priority Score:** 42005.2
- **Functions:** 11/13 matched (target 28)
- **Missing functions:** `with_context`, `test_cycle`
- **Types:** 5/7 matched
- **Missing types:** `MaybeOwnedCCtx`, `MaybeOwnedDCtx`
- **Tests:** 0/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `stream/raw.rs` vs expected `stream/raw.rs`
- **Proposed provenance header:** `// port-lint: source stream/raw.rs` (current: `// port-lint: source stream/raw.rs`)
- **Lint issues:** 2

### 8. lib

- **Target:** `zstd.Lib [PROVENANCE-FALLBACK]`
- **Similarity:** 0.24
- **Dependents:** 0
- **Priority Score:** 20507.6
- **Functions:** 3/5 matched (target 7)
- **Missing functions:** `test_cycle`, `test_cycle_unwrap`
- **Types:** 0/0 matched (target 26)
- **Missing types:** _none_
- **Tests:** 1/3 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `lib.rs` vs expected `lib.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source lib.rs`)
- **Proposed provenance header:** `// port-lint: tests lib.rs` (current: `// port-lint: tests lib.rs`)
- **Lint issues:** 2

### 9. dict

- **Target:** `dict.Dict [PROVENANCE-FALLBACK]`
- **Similarity:** 0.52
- **Dependents:** 0
- **Priority Score:** 1104.8
- **Functions:** 9/9 matched (target 14)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_
- **Tests:** 1/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `dict.rs` vs expected `dict.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:dict.rs` vs expected `dict.rs`
- **Proposed provenance header:** `// port-lint: source dict.rs` (current: `// port-lint: source dict.rs`)
- **Proposed provenance header:** `// port-lint: tests dict.rs` (current: `// port-lint: tests dict.rs`)
- **Lint issues:** 2

### 10. bulk.mod

- **Target:** `bulk.Bulk [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 410.0
- **Functions:** 4/4 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `bulk/mod.rs` vs expected `bulk/mod.rs`
- **Proposed provenance header:** `// port-lint: source bulk/mod.rs` (current: `// port-lint: source bulk/mod.rs`)
- **Lint issues:** 1

### 11. stream.functions

- **Target:** `stream.Functions [PROVENANCE-FALLBACK]`
- **Similarity:** 0.50
- **Dependents:** 0
- **Priority Score:** 405.0
- **Functions:** 4/4 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `stream/functions.rs` vs expected `stream/functions.rs`
- **Proposed provenance header:** `// port-lint: source stream/functions.rs` (current: `// port-lint: source stream/functions.rs`)
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

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `stream.mod` | `stream.Mod` | 0 | `src/stream/mod.rs` | `stream/Mod.kt` |
| `zio.mod` | `stream.zio.Mod` | 0 | `src/stream/zio/mod.rs` | `stream/zio/Mod.kt` |

