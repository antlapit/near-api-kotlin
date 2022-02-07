# NEAR RPC Kotlin API

![ci-badge] ![Tests](.github/badges/tests.svg) ![Coverage](.github/badges/jacoco.svg) ![Branches](.github/badges/branches.svg) ![docs-ci-badge]

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

* [AccessKeysEndpoint] for [`Access keys`] endpoint
* [BlockEndpoint] for [`Block / Chunk`] endpoint
* [ContractsEndpoint] for [`Accounts / Contracts`] endpoint
* [GasEndpoint] for [`Gas`] endpoint
* [ProtocolEndpoint] for [`Protocol`] endpoint
* [NetworkEndpoint] for [`Network`] endpoint
* [TransactionsEndpoint] for [`Transactions`] endpoint
* [SandboxEndpoint] for [`Sandbox`] endpoint

**Models**

* All models are places in **model** package grouped by API groups
* Some types are just aliases to primitives but are named exactly like in [nearcore] (ex. AccountId, BlockHeight, etc)

### Providers implementation

**Dependencies**

* [Ktor] for making HTTP calls
  * Note that Ktor logging configuration is a part of public API of this library
* [Jackson] for serializing/deserializing requests and models
* [Kotest] and [JUnit] for testing
* [`Komputing Sha256`] and [`Tweetnacl Java port`] for end-to-end transaction testing
* [JaCoCo] for test coverage

**JSON serialization/deserialization**
JSON serialization and deserialization are implemented with [Jackson] and additional [NearRpcModelsModule]

**Main classes**
[JsonRpcProvider] implements low level methods for calling RPC API (**sendRpc** and **query** methods). This class
contains [Ktor] client that should be closed after final API call. [JsonRpcProvider] can be configured with
[`Network interfaces`], [Ktor] logging properties and custom [Jackson] ObjectMapper.

[`Network interfaces`] interface can be used for custom network implementation, but it is recommended to use
predefined [NetworkEnum].

Other providers ([AccessKeysRpcEndpoint], [BlockRpcEndpoint], [ContractsRpcEndpoint], [GasRpcEndpoint], [ProtocolRpcEndpoint],
[NetworkRpcEndpoint], [TransactionsRpcEndpoint], [SandboxRpcEndpoint]) implements high level operations of RPC endpoints. These classes
require [JsonRpcProvider] as a constructor parameter. (!) While using the same instance of [JsonRpcProvider] for
different providers, don't forget that closing [JsonRpcProvider] in one place will lead to errors in other places.

See Examples section for additional information.

## Rust enums support

[nearcore] is written in Rust and uses awesome Rust enums in RPC models (see [`Rust enum docs`]). Kotlin does not have
any straightforward implementation of such structure and the closest analogue is [`Kotlin sealed classes`]. For the
purpose of this library there are annotations in [RustBridge], which are used in deserialization process
in [RustEnumSerializers].

## Installation

### BorshJ
Current [BorshJ] implementation is distributed as a single file, which should be places in source code repository.

Example for Gradle
```kotlin
depencencies {
    implementation(fileTree("lib")) // BorshJ JAR file is placed in lib folder
}
```

### Github Packages

![badge][badge-jvm]
![GitHub release (latest by date)](https://img.shields.io/github/v/release/antlapit/near-api-kotlin)

* **NEAR RPC Kotlin API** can be used in your project as a Maven or Gradle dependency. Dependencies are located in Github
packages - https://maven.pkg.github.com/antlapit/near-api-kotlin
* Don't forget to specify your Github credentials.

#### Maven (repository)
```xml
<repositories>
    <repository>
        <id>near-kotlin-api</id>
        <url>https://maven.pkg.github.com/antlapit/near-api-kotlin</url>
    </repository>
</repositories>
```
#### Maven (dependencies)
```xml
<dependencies>
    <dependency>
        <groupId>org.near.api</groupId>
        <artifactId>providers-api</artifactId>
        <version>${version}</version>
    </dependency>
    <dependency>
        <groupId>org.near.api</groupId>
        <artifactId>providers</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```

#### Gradle (repository)
```kotlin
repositories {
    maven {
        name = "NEAR Kotlin API Github Packages"
        url = uri("https://maven.pkg.github.com/antlapit/near-api-kotlin")
    }
}
```
#### Gradle (dependencies)
```kotlin

depencencies {
    implementation("org.near.api:provider-api:${version}")
    implementation("org.near.api:provider:${version}")
}
```

### JitPack

![badge][badge-jvm]
[![](https://jitpack.io/v/antlapit/near-api-kotlin.svg)](https://jitpack.io/#antlapit/near-api-kotlin)

* **NEAR RPC Kotlin API** is available in JitPack.

#### Maven (repository)
```xml
<repositories>
    <repository>
        <id>jitpack</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
#### Maven (dependencies)
```xml
<dependencies>
    <dependency>
        <groupId>com.github.antlapit.near-api-kotlin</groupId>
        <artifactId>providers-api</artifactId>
        <version>${version}</version>
    </dependency>
    <dependency>
        <groupId>com.github.antlapit.near-api-kotlin</groupId>
        <artifactId>providers</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```

#### Gradle (repository)
```kotlin
repositories {
    maven { 
        url = "https://jitpack.io" 
    }
}
```
#### Gradle (dependencies)
```kotlin
depencencies {
    implementation("com.github.antlapit.near-api-kotlin:provider-api:${version}")
    implementation("com.github.antlapit.near-api-kotlin:provider:${version}")
}
```

## Testing

All tests are divided into several groups:

* End-to-end tests
* Deserialization unit tests
* Documentation validation tests

### End-to-end tests

These tests perform calls to NEAR testnet using special account (**api_kotlin.testnet**) if needed. This is useful for
testing the equality of responses by different request parameters. Also end-to-end tests are designed to fail in case of
changing RPC API in testnet without backward compatibility.

### Deserialization unit tests

These tests use prepared JSON responses based on a real for many variants. This stabilizes API and prevents the
appearance of bugs during refactoring of future improvements.

### Documentation validation tests

These tests validate example responses in [`NEAR RPC documentation`]. 
Testing algorithm:
* load markdown documentation from [`near docs`]
* parse markdown and find RPC methods and example responses
* try to deserialize example responses to classes defined in [`index file`]
* fail if:
  * no mapping found OR 
  * existing mapping produced error 

## Known issues

* [BorshJ] implementation in code base should be replaced by normal dependency, when it will be created
* Fixed size arrays in BorshJ serializations should be moved to [BorshJ]
* Rust enums serialization can be moved to external library

## Examples

NEAR Kotlin API examples are placed in [`near-api-kotlin-examples`]

[`NEAR RPC documentation`]: https://docs.near.org/docs/api/rpc

[`Access keys`]: https://docs.near.org/docs/api/rpc/access-keys

[`Accounts / Contracts`]: https://docs.near.org/docs/api/rpc/contracts

[`Block / Chunk`]: https://docs.near.org/docs/api/rpc/block-chunk

[`Gas`]: https://docs.near.org/docs/api/rpc/gas

[`Protocol`]: https://docs.near.org/docs/api/rpc/protocol

[`Network`]: https://docs.near.org/docs/api/rpc/network

[`Transactions`]: https://docs.near.org/docs/api/rpc/transactions

[`Sandbox`]: https://docs.near.org/docs/api/rpc/sandbox


[RepositoryURL]: https://github.com/antlapit/near-api-kotlin

[BorshJ]: https://github.com/near/borshj

[nearcore]: https://github.com/near/nearcore

[`near docs`]: https://github.com/near/docs

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

[AccessKeysEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers-api/src/main/kotlin/org/near/api/endpoints/AccessKeysEndpoint.kt

[BlockEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers-api/src/main/kotlin/org/near/api/endpoints/BlockEndpoint.kt

[ContractsEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers-api/src/main/kotlin/org/near/api/endpoints/ContractsEndpoint.kt

[GasEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers-api/src/main/kotlin/org/near/api/endpoints/GasEndpoint.kt

[NetworkEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers-api/src/main/kotlin/org/near/api/endpoints/NetworkEndpoint.kt

[ProtocolEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers-api/src/main/kotlin/org/near/api/endpoints/ProtocolEndpoint.kt

[TransactionsEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers-api/src/main/kotlin/org/near/api/endpoints/TransactionsEndpoint.kt

[SandboxEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers-api/src/main/kotlin/org/near/api/endpoints/SandboxEndpoint.kt

[RustBridge]: https://github.com/antlapit/near-api-kotlin/blob/main/providers-api/src/main/kotlin/org/near/api/model/rust/RustBridge.kt


[`Network interfaces`]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/provider/config/Network.kt

[NetworkEnum]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/provider/config/NetworkEnum.kt

[JsonRpcProvider]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/provider/JsonRpcProvider.kt

[AccessKeysRpcEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/endpoints/AccessKeysRpcEndpoint.kt

[BlockRpcEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/endpoints/BlockRpcEndpoint.kt

[ContractsRpcEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/endpoints/ContractsRpcEndpoint.kt

[GasRpcEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/endpoints/GasRpcEndpoint.kt

[NetworkRpcEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/endpoints/NetworkRpcEndpoint.kt

[ProtocolRpcEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/endpoints/ProtocolRpcEndpoint.kt

[TransactionsRpcEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/endpoints/TransactionsRpcEndpoint.kt

[SandboxRpcEndpoint]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/endpoints/SandboxRpcEndpoint.kt

[RustEnumSerializers]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/json/RustEnumSerializers.kt

[NearRpcModelsModule]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/main/kotlin/org/near/api/json/NearRpcModelsModule.kt

[`index file`]: https://github.com/antlapit/near-api-kotlin/blob/main/providers/src/test/resources/docs/.index

[ci-badge]:https://github.com/antlapit/near-api-kotlin/actions/workflows/main.yml/badge.svg "CI build status"

[docs-ci-badge]:https://github.com/antlapit/near-api-kotlin/actions/workflows/docs.yml/badge.svg "Docs validation status"

[badge-jdk-11]: https://img.shields.io/badge/jdk-11-green.svg "JDK-11 or higher"

[badge-kotlin-1.5]: https://img.shields.io/badge/kotlin-1.5-green.svg "Kotlin 1.5.0 or higher"

[badge-gradle]: https://img.shields.io/badge/tool-gradle-blue.svg

[badge-jvm]: http://img.shields.io/badge/platform-jvm-orange.svg?style=flat

[`near-api-kotlin-examples`]: https://github.com/antlapit/near-api-kotlin/blob/main/examples.md
