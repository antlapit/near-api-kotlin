# NEAR RPC Kotlin API

**NEAR RPC Kotlin API** is used to interact with NEAR network via RPC API. 
RPC API documentation can be found on [`NEAR RPC documentation`] website.

## License
This repository is distributed under the terms of both the MIT license and the Apache License (Version 2.0). See LICENSE and LICENSE-APACHE for details.

## Prerequisites
- [Kotlin] 1.5.31+ (library is )
- [Java] 11+ (this version is the jvm target version for this library)
- [Gradle] (when building from source code)

## Modules
This library consists of 2 modules:  
* **providers-api** - all models, exceptions and contracts of RPC API 
* **providers** - API implementation with [Ktor] and [Jackson]  

It is possible to use only **providers-api** and create custom implementation

### Providers API

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
**Key features**
* [Ktor] for making HTTP calls
* [Jackson] for serializing/deserializing requests and models
* [Kotest] and [JUnit] for testing

## Installation
**NEAR RPC Kotlin API** can be used in your project as a Maven or Gradle dependency.
Dependencies are located in Github packages - https://maven.pkg.github.com/antlapit/near-api-kotlin

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
These tests perform calls to NEAR testnet using special account (**api_kotlin.testnet**) if needed.
This is useful for testing the equality of responses by different request parameters.
Also end-to-end tests are designed to fail in case of changing RPC API in testnet without backward compatibility.

### Deserialization unit tests
These tests use prepared JSON responses based on a real for many variants.
This stabilizes API and prevents the appearance of bugs during refactoring of future improvements.

## Usage

## Known issues
* [BorshJ] implementation in code base should be replaced by normal dependency, when it will be created
* Fixed size arrays in BorshJ serializations should be moved to [BorshJ]

## Examples

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

[AccessKeyProvider]: https://github.com/antlapit/near-api-kotlin/providers-api/src/main/kotlin/antlapit/near/api/providers/AccessKeyProvider.kt
[BlockProvider]: https://github.com/antlapit/near-api-kotlin/providers-api/src/main/kotlin/antlapit/near/api/providers/BlockProvider.kt
[ContractProvider]: https://github.com/antlapit/near-api-kotlin/providers-api/src/main/kotlin/antlapit/near/api/providers/ContractProvider.kt
[GasProvider]: https://github.com/antlapit/near-api-kotlin/providers-api/src/main/kotlin/antlapit/near/api/providers/GasProvider.kt
[NetworkProvider]: https://github.com/antlapit/near-api-kotlin/providers-api/src/main/kotlin/antlapit/near/api/providers/NetworkProvider.kt
[TransactionProvider]: https://github.com/antlapit/near-api-kotlin/providers-api/src/main/kotlin/antlapit/near/api/providers/TransactionProvider.kt

[JsonRpcProvider]: https://github.com/antlapit/near-api-kotlin/providers/src/main/kotlin/antlapit/near/api/providers/base/JsonRpcProvider.kt
[AccessKeyRpcProvider]: https://github.com/antlapit/near-api-kotlin/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/AccessKeyRpcProvider.kt
[BlockRpcProvider]: https://github.com/antlapit/near-api-kotlin/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/BlockRpcProvider.kt
[ContractRpcProvider]: https://github.com/antlapit/near-api-kotlin/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/ContractRpcProvider.kt
[GasRpcProvider]: https://github.com/antlapit/near-api-kotlin/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/GasRpcProvider.kt
[NetworkRpcProvider]: https://github.com/antlapit/near-api-kotlin/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/NetworkRpcProvider.kt
[TransactionRpcProvider]: https://github.com/antlapit/near-api-kotlin/providers/src/main/kotlin/antlapit/near/api/providers/endpoints/TransactionRpcProvider.kt
