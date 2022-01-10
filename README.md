# NEAR RPC Kotlin API

![ci-badge] ![Tests](.github/badges/tests.svg) ![Coverage](.github/badges/jacoco.svg) ![Branches](.github/badges/branches.svg)

**NEAR RPC Kotlin API** is used to interact with NEAR network via RPC API. RPC API documentation can be found
on [`NEAR RPC documentation`] website.

## License [![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](LICENSE-APACHE) [![license](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

This repository is distributed under the terms of both the MIT license and the Apache License (Version 2.0). See LICENSE
and LICENSE-APACHE for details.

## Prerequisites ![badge-jdk-11] ![badge-kotlin-1.5] ![badge-gradle]

- [Kotlin] 1.5.31+ (library is written on pure Kotlin)
- [Java] 11+ (this version is the jvm target version for this library)
- [Gradle] (when building from source code)

## Modules

This library consists of 2 modules:

* **providers-api** - all models, exceptions and contracts of RPC API
* **providers** - API implementation with [Ktor] and [Jackson]

It is possible to use only **providers-api** and create custom implementation

### Providers API

**Dependencies**

* [`Komputing Base58`] for encoding and decoding into base58

**Interfaces**
Every API group in [`NEAR RPC documentation`] has a separate interface:

* [AccessKeyProvider] for [`Access keys`] endpoint
* [BlockProvider] for [`Block / Chunk`] endpoint
* [ContractProvider] for [`Accounts / Contracts`] endpoint
* [GasProvider] for [`Gas`] endpoint
* [NetworkProvider] for [`Network`] endpoint
* [TransactionProvider] for [`Transactions`] endpoint

**Models**

* All models are places in **model** package grouped by API groups
* Some types are just aliases to primitives but are named exactly like in [nearcore] (ex. AccountId, BlockHeight, etc)

### Providers implementation

**Dependencies**

* [Ktor] for making HTTP calls
* [Jackson] for serializing/deserializing requests and models
* [Kotest] and [JUnit] for testing
* [`Komputing Sha256`] and [`Tweetnacl Java port`] for end-to-end transaction testing
* [JaCoCo] for test coverage

**JSON serialization/deserialization**
JSON serialization and deserialization are implemented with [Jackson] and additional [NearRpcModelsModule]

**Main classes**
[JsonRpcProvider] implements low level methods for calling RPC API (**sendRpc** and **query** methods). This class
contains [Ktor] client that should be closed after final API call. [JsonRpcProvider] can be configured with
[Network], [Ktor] logging properties and custom [Jackson] ObjectMapper.

[Network] interface can be used for custom network implementation, but it is recommended to use predefined **
NetworkEnum**.

Other providers ([AccessKeyRpcProvider], [BlockRpcProvider], [ContractRpcProvider], [GasRpcProvider],
[NetworkRpcProvider], [TransactionRpcProvider]) implements high level operations of RPC endpoints. These classes
require [JsonRpcProvider] as a constructor parameter. (!) While using the same instance of [JsonRpcProvider] for
different providers, don't forget that closing [JsonRpcProvider] in one place will lead to errors in other places.

See Examples section for additional information.

## Rust enums support

[nearcore] is written in Rust and uses awesome Rust enums in RPC models (see [`Rust enum docs`]). Kotlin does not have
any straightforward implementation of such structure and the closest analogue is [`Kotlin sealed classes`]. For the
purpose of this library there are annotations in [RustBridge], which are used in deserialization process
in [RustEnumSerializers].

## Installation

**NEAR RPC Kotlin API** can be used in your project as a Maven or Gradle dependency. Dependencies are located in Github
packages - https://maven.pkg.github.com/antlapit/near-api-kotlin

### Adding artifact repository

**Github maven repository**

(!) Don't forget to specify your Github credentials.

```xml

<repositories>
    <repository>
        <id>near-kotlin-api</id>
        <url>https://maven.pkg.github.com/antlapit/near-api-kotlin</url>
    </repository>
</repositories>
```

```groovy
repositories {
    maven {
        url "https://maven.pkg.github.com/antlapit/near-api-kotlin"
    }
}
```

### Providers API

**Maven**

```xml

<dependency>
    <groupId>antlapit.near.api</groupId>
    <artifactId>providers-api</artifactId>
    <version>${version}</version>
</dependency>
```

**Gradle**

```groovy
depencencies {
    implementation 'antlapit.near.api:providers-api:${version}'
}
```

### Providers implementation

**Maven**

```xml

<dependency>
    <groupId>antlapit.near.api</groupId>
    <artifactId>providers</artifactId>
    <version>${version}</version>
</dependency>
```

**Gradle**

```groovy
depencencies {
    implementation 'antlapit.near.api:providers:${version}'
}
```

## Testing

All tests are divided into several groups:

* End-to-end tests
* Deserialization unit tests

### End-to-end tests

These tests perform calls to NEAR testnet using special account (**api_kotlin.testnet**) if needed. This is useful for
testing the equality of responses by different request parameters. Also end-to-end tests are designed to fail in case of
changing RPC API in testnet without backward compatibility.

### Deserialization unit tests

These tests use prepared JSON responses based on a real for many variants. This stabilizes API and prevents the
appearance of bugs during refactoring of future improvements.

## Known issues

* [BorshJ] implementation in code base should be replaced by normal dependency, when it will be created
* Fixed size arrays in BorshJ serializations should be moved to [BorshJ]
* Rust enums serialization can be moved to external library

## Examples

Under construction. Expecting in February 2022.

[`NEAR RPC documentation`]: https://docs.near.org/docs/api/rpc

[`Access keys`]: https://docs.near.org/docs/api/rpc/access-keys

[`Accounts / Contracts`]: https://docs.near.org/docs/api/rpc/contracts

[`Block / Chunk`]: https://docs.near.org/docs/api/rpc/block-chunk

[`Gas`]: https://docs.near.org/docs/api/rpc/gas

[`Protocol`]: https://docs.near.org/docs/api/rpc/protocol

[`Network`]: https://docs.near.org/docs/api/rpc/network

[`Transactions`]: https://docs.near.org/docs/api/rpc/transactions


[RepositoryURL]: https://github.com/antlapit/near-api-kotlin

[BorshJ]: https://github.com/near/borshj

[nearcore]: https://github.com/near/nearcore

[Gradle]: https://gradle.org

[Java]: https://java.com

[Kotlin]: https://kotlinlang.org/

[Ktor]: https://ktor.io/

[Jackson]: https://github.com/FasterXML/jackson

[Kotest]: https://kotest.io/

[JUnit]: https://junit.org/

[JaCoCo]: https://github.com/jacoco/jacoco

[`Komputing Base58`]: https://github.com/komputing/KBase58

[`Komputing Sha256`]: https://github.com/komputing/KHash

[`Tweetnacl Java port`]: https://github.com/InstantWebP2P/tweetnacl-java

[`Rust enum docs`]: https://doc.rust-lang.org/book/ch06-01-defining-an-enum.html

[`Kotlin sealed classes`]: https://kotlinlang.org/docs/sealed-classes.html

[AccessKeyProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers-api/src/main/kotlin/antlapit/near/api/providers/AccessKeyProvider.kt

[BlockProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers-api/src/main/kotlin/antlapit/near/api/providers/BlockProvider.kt

[ContractProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers-api/src/main/kotlin/antlapit/near/api/providers/ContractProvider.kt

[GasProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers-api/src/main/kotlin/antlapit/near/api/providers/GasProvider.kt

[NetworkProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers-api/src/main/kotlin/antlapit/near/api/providers/NetworkProvider.kt

[TransactionProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers-api/src/main/kotlin/antlapit/near/api/providers/TransactionProvider.kt

[RustBridge]: https://github.com/antlapit/near-api-kotlin/tree/main/providers-api/src/main/kotlin/antlapit/near/api/providers/model/rust/RustBridge.kt


[Network]: https://github.com/antlapit/near-api-kotlin/tree/main/providers/src/main/kotlin/antlapit/near/api/providers/base/config/Network.kt

[JsonRpcProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers/src/main/kotlin/antlapit/near/api/providers/base/JsonRpcProvider.kt

[AccessKeyRpcProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/AccessKeyRpcProvider.kt

[BlockRpcProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/BlockRpcProvider.kt

[ContractRpcProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/ContractRpcProvider.kt

[GasRpcProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/GasRpcProvider.kt

[NetworkRpcProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/NetworkRpcProvider.kt

[TransactionRpcProvider]: https://github.com/antlapit/near-api-kotlin/tree/main/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/TransactionRpcProvider.kt

[RustEnumSerializers]: https://github.com/antlapit/near-api-kotlin/tree/main/providers/src/main/kotlin/antlapit/near/api/json/RustEnumSerializers.kt

[NearRpcModelsModule]: https://github.com/antlapit/near-api-kotlin/tree/main/providers/src/main/kotlin/antlapit/near/api/json/NearRpcModelsModule.kt


[ci-badge]:https://github.com/antlapit/near-api-kotlin/actions/workflows/main.yml/badge.svg "CI build status"

[badge-jdk-11]: https://img.shields.io/badge/jdk-11-green.svg "JDK-11 or higher"

[badge-kotlin-1.5]: https://img.shields.io/badge/kotlin-1.5-green.svg "Kotlin 1.5.0 or higher"

[badge-gradle]: https://img.shields.io/badge/tool-gradle-blue.svg
