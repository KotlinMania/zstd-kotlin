# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 7/17 (41.2%)
- **Function parity:** 94/96 matched (target 117) — 97.9%
- **Class/type parity:** 14/14 matched (target 22) — 100.0%
- **Combined symbol parity:** 108/110 matched (target 139) — 98.2%
- **Average inline-code cosine:** 0.13 (function body across 7 matched files)
- **Average documentation cosine:** 0.80 (doc text across 7 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 7 files with <0.60 function similarity
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
- **Similarity:** 0.15
- **Dependents:** 1
- **Priority Score:** 1022108.4
- **Functions:** 18/20 matched (target 21)
- **Missing functions:** `offset`, `buffer`
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 5/7 matched

### 2. zio.reader

- **Target:** `zio.Reader`
- **Similarity:** 0.11
- **Dependents:** 1
- **Priority Score:** 1001308.9
- **Functions:** 11/11 matched (target 15)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_
- **Tests:** 2/2 matched

### 3. bulk.compressor

- **Target:** `bulk.Compressor`
- **Similarity:** 0.15
- **Dependents:** 1
- **Priority Score:** 1001308.5
- **Functions:** 12/12 matched (target 18)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 4. bulk.decompressor

- **Target:** `bulk.Decompressor`
- **Similarity:** 0.14
- **Dependents:** 1
- **Priority Score:** 1001208.6
- **Functions:** 11/11 matched (target 12)
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

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
| `bulk.mod` | `bulk.Mod` | 0 | `bulk/mod.rs` | `bulk/Mod.kt` |
| `bulk.tests` | `bulk.Tests` | 0 | `bulk/tests.rs` | `bulk/Tests.kt` |
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |
| `stream.mod` | `stream.Mod` | 0 | `stream/mod.rs` | `stream/Mod.kt` |
| `read.mod` | `stream.read.Mod` | 0 | `stream/read/mod.rs` | `stream/read/Mod.kt` |
| `read.tests` | `stream.read.Tests` | 0 | `stream/read/tests.rs` | `stream/read/Tests.kt` |
| `stream.tests` | `stream.Tests` | 0 | `stream/tests.rs` | `stream/Tests.kt` |
| `write.mod` | `stream.write.Mod` | 0 | `stream/write/mod.rs` | `stream/write/Mod.kt` |
| `write.tests` | `stream.write.Tests` | 0 | `stream/write/tests.rs` | `stream/write/Tests.kt` |
| `zio.mod` | `stream.zio.Mod` | 0 | `stream/zio/mod.rs` | `stream/zio/Mod.kt` |

