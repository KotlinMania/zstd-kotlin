# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 13/17 (76.5%)
- **Function parity:** 88/96 matched (target 120) — 91.7%
- **Class/type parity:** 14/14 matched (target 22) — 100.0%
- **Combined symbol parity:** 102/110 matched (target 142) — 92.7%
- **Average inline-code cosine:** 0.13 (function body across 8 matched files)
- **Average documentation cosine:** 0.49 (doc text across 8 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 13 files with <0.60 function similarity
- **Needs Review:** 0 files with 0.60-0.84 function similarity
- **Excellent:** 0 files with >=0.85 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. zio.writer

- **Target:** `zio.Writer`
- **Similarity:** 0.08
- **Dependents:** 1
- **Priority Score:** 1082109.1
- **Functions:** 12/20 matched (target 15)
- **Missing functions:** `operation`, `offset`, `buffer`, `test_noop`, `test_compress`, `test_compress_with_capacity`, `test_decompress`, `test_decompress_with_capacity`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Tests:** 0/7 matched

### 2. zio.reader

- **Target:** `zio.Reader`
- **Similarity:** 0.07
- **Dependents:** 1
- **Priority Score:** 1001309.2
- **Functions:** 11/11 matched (target 24)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_
- **Tests:** 2/2 matched

### 3. bulk.compressor

- **Target:** `bulk.Compressor`
- **Similarity:** 0.19
- **Dependents:** 1
- **Priority Score:** 1001308.1
- **Functions:** 12/12 matched (target 18)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Lint issues:** 1

### 4. bulk.decompressor

- **Target:** `bulk.Decompressor`
- **Similarity:** 0.18
- **Dependents:** 1
- **Priority Score:** 1001208.2
- **Functions:** 11/11 matched (target 12)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Lint issues:** 2

### 5. stream.raw

- **Target:** `raw.Raw`
- **Similarity:** 0.10
- **Dependents:** 0
- **Priority Score:** 3409.0
- **Functions:** 27/27 matched (target 33)
- **Missing functions:** _none_
- **Types:** 7/7 matched (target 12)
- **Missing types:** _none_
- **Tests:** 1/1 matched
- **Lint issues:** 1

### 6. dict

- **Target:** `dict.Dict`
- **Similarity:** 0.12
- **Dependents:** 0
- **Priority Score:** 1308.8
- **Functions:** 11/11 matched (target 14)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 7. stream.functions

- **Target:** `stream.Functions`
- **Similarity:** 0.14
- **Dependents:** 0
- **Priority Score:** 408.6
- **Functions:** 4/4 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 8. zio.mod

- **Target:** `zio.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

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
| `lib` | `zstd.Lib` | `lib` |
| `bulk.mod` | `bulk.Bulk` | `bulk/mod` |
| `stream.mod` | `stream.Mod` | `stream/mod` |

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `bulk.tests` | `bulk.Tests` | 0 | `bulk/tests.rs` | `bulk/Tests.kt` |
| `read.tests` | `stream.read.Tests` | 0 | `stream/read/tests.rs` | `stream/read/Tests.kt` |
| `stream.tests` | `stream.Tests` | 0 | `stream/tests.rs` | `stream/Tests.kt` |
| `write.tests` | `stream.write.Tests` | 0 | `stream/write/tests.rs` | `stream/write/Tests.kt` |

