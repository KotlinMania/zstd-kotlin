# zstd-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Fzstd--kotlin-blue.svg)](https://github.com/KotlinMania/zstd-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/zstd-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/zstd-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/zstd-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/zstd-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`gyscos/zstd-rs`](https://github.com/gyscos/zstd-rs).

**Original Project:** This port is based on [`gyscos/zstd-rs`](https://github.com/gyscos/zstd-rs). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `gyscos/zstd-rs`

> The text below is reproduced and lightly edited from [`https://github.com/gyscos/zstd-rs`](https://github.com/gyscos/zstd-rs). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

## zstd

[![crates.io](https://img.shields.io/crates/v/zstd.svg)](https://crates.io/crates/zstd)
[![BSD-3-Clause licensed](https://img.shields.io/badge/license-BSD--3--Clause-blue.svg)](./LICENSE)

[![Build on Linux](https://github.com/gyscos/zstd-rs/actions/workflows/linux.yml/badge.svg)](https://github.com/gyscos/zstd-rs/actions/workflows/linux.yml)
[![Build on Windows](https://github.com/gyscos/zstd-rs/actions/workflows/windows.yml/badge.svg)](https://github.com/gyscos/zstd-rs/actions/workflows/windows.yml)
[![Build on macOS](https://github.com/gyscos/zstd-rs/actions/workflows/macos.yml/badge.svg)](https://github.com/gyscos/zstd-rs/actions/workflows/macos.yml)
[![Build on wasm](https://github.com/gyscos/zstd-rs/actions/workflows/wasm.yml/badge.svg)](https://github.com/gyscos/zstd-rs/actions/workflows/wasm.yml)


This library is a rust binding for the [zstd compression library][zstd].

# [Documentation][doc]

## 1 - Add to `cargo.toml`

```bash
$ cargo add zstd
```

```toml
# Cargo.toml

[dependencies]
zstd = "0.13"
```

## 2 - Usage

This library provides `Read` and `Write` wrappers to handle (de)compression,
along with convenience functions to made common tasks easier.

For instance, `stream::copy_encode` and `stream::copy_decode` are easy-to-use
wrappers around `std::io::copy`. Check the [stream] example:

```rust
use std::io;

// This function use the convenient `copy_encode` method
fn compress(level: i32) {
    zstd::stream::copy_encode(io::stdin(), io::stdout(), level).unwrap();
}

// This function does the same thing, directly using an `Encoder`:
fn compress_manually(level: i32) {
    let mut encoder = zstd::stream::Encoder::new(io::stdout(), level).unwrap();
    io::copy(&mut io::stdin(), &mut encoder).unwrap();
    encoder.finish().unwrap();
}

fn decompress() {
    zstd::stream::copy_decode(io::stdin(), io::stdout()).unwrap();
}
```

# Asynchronous support

The [`async-compression`](https://github.com/Nemo157/async-compression/) crate
provides an async-ready integration of various compression algorithms,
including `zstd-rs`.

# Compile it yourself

Upstream `zstd-rs` expects the native `zstd` source tree to already be present.
With a prepared checkout, running `cargo build` should take care
of building the C library and linking to it.

# Build-time bindgen

This library includes a pre-generated `bindings.rs` file.
You can also generate new bindings at build-time, using the `bindgen` feature:

```
cargo build --features bindgen
```

# TODO

* Benchmarks, optimizations, ...

# Disclaimer

This implementation is largely inspired by bozaro's [lz4-rs].

# License

* The zstd C library is under a dual BSD/GPLv2 license.
* This zstd-rs binding library is under a [BSD-3-Clause](https://github.com/gyscos/zstd-rs/blob/HEAD/LICENSE) license.

[zstd]: https://github.com/facebook/zstd
[lz4-rs]: https://github.com/bozaro/lz4-rs
[cargo-edit]: https://github.com/killercup/cargo-edit#cargo-add
[doc]: https://docs.rs/zstd
[stream]: examples/stream.rs
[submodule]: https://git-scm.com/book/en/v2/Git-Tools-Submodules

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:zstd-kotlin:0.1.0")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same MIT license as the upstream [`gyscos/zstd-rs`](https://github.com/gyscos/zstd-rs). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the zstd-rs authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`gyscos/zstd-rs`](https://github.com/gyscos/zstd-rs) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
