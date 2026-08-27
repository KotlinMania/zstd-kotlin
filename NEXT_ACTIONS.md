# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 11/17 (64.7%)
- **Function parity:** 71/153 matched (target 112) — 46.4%
- **Class/type parity:** 13/20 matched (target 43) — 65.0%
- **Combined symbol parity:** 84/173 matched (target 155) — 48.6%
- **Average inline-code cosine:** 0.43 (function body across 8 matched files)
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

- **Target:** `zio.Writer`
- **Similarity:** 0.16
- **Dependents:** 1
- **Priority Score:** 1152108.4
- **Functions:** 5/20 matched (target 5)
- **Missing functions:** `with_output_buffer`, `with_buffer`, `write_from_offset`, `into_inner`, `writer`, `writer_mut`, `operation`, `offset`, `buffer`, `flush`, `test_noop`, `test_compress`, `test_compress_with_capacity`, `test_decompress`, `test_decompress_with_capacity`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Tests:** 0/7 matched

### 2. zio.reader

- **Target:** `zio.Reader`
- **Similarity:** 0.33
- **Dependents:** 1
- **Priority Score:** 1071306.8
- **Functions:** 5/11 matched (target 6)
- **Missing functions:** `reader_mut`, `reader`, `into_inner`, `fill_buf`, `test_noop`, `test_compress`
- **Types:** 1/2 matched (target 1)
- **Missing types:** `State`
- **Tests:** 0/2 matched

### 3. bulk.compressor

- **Target:** `bulk.Compressor`
- **Similarity:** 0.49
- **Dependents:** 1
- **Priority Score:** 1031305.1
- **Functions:** 9/12 matched (target 15)
- **Missing functions:** `context_mut`, `_assert_traits`, `_assert_send`
- **Types:** 1/1 matched
- **Missing types:** _none_

### 4. bulk.decompressor

- **Target:** `bulk.Decompressor`
- **Similarity:** 0.49
- **Dependents:** 1
- **Priority Score:** 1021205.1
- **Functions:** 9/11 matched (target 10)
- **Missing functions:** `_assert_traits`, `_assert_send`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Lint issues:** 1

### 5. write.mod

- **Target:** `write.Encoder [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 212810.0
- **Functions:** 6/24 matched (target 7)
- **Missing functions:** `get_ref`, `get_mut`, `drop`, `flush`, `with_writer`, `with_encoder`, `with_context`, `auto_finish`, `on_finish`, `try_finish`, `do_finish`, `recommended_input_size`, `with_decoder`, `into_inner`, `auto_flush`, `on_flush`, `_assert_traits`, `_assert_send`
- **Types:** 1/4 matched (target 1)
- **Missing types:** `Decoder`, `AutoFinishEncoder`, `AutoFlushDecoder`

### 6. read.mod

- **Target:** `read.Decoder [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 101710.0
- **Functions:** 6/15 matched (target 8)
- **Missing functions:** `with_buffer`, `with_context`, `recommended_output_size`, `get_ref`, `get_mut`, `finish`, `flush`, `_assert_traits`, `_assert_send`
- **Types:** 1/2 matched (target 1)
- **Missing types:** `Encoder`

### 7. stream.raw

- **Target:** `raw.Raw`
- **Similarity:** 0.48
- **Dependents:** 0
- **Priority Score:** 42005.2
- **Functions:** 11/13 matched (target 30)
- **Missing functions:** `with_context`, `test_cycle`
- **Types:** 5/7 matched (target 8)
- **Missing types:** `MaybeOwnedCCtx`, `MaybeOwnedDCtx`
- **Tests:** 0/1 matched
- **Lint issues:** 1

### 8. lib

- **Target:** `zstd.Lib`
- **Similarity:** 0.47
- **Dependents:** 0
- **Priority Score:** 20505.3
- **Functions:** 3/5 matched (target 9)
- **Missing functions:** `test_cycle`, `test_cycle_unwrap`
- **Types:** 0/0 matched (target 26)
- **Missing types:** _none_
- **Tests:** 1/3 matched

### 9. dict

- **Target:** `dict.Dict`
- **Similarity:** 0.52
- **Dependents:** 0
- **Priority Score:** 1104.8
- **Functions:** 9/9 matched (target 14)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 10. bulk.mod

- **Target:** `bulk.Bulk [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 410.0
- **Functions:** 4/4 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 11. stream.functions

- **Target:** `stream.Functions`
- **Similarity:** 0.50
- **Dependents:** 0
- **Priority Score:** 405.0
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

