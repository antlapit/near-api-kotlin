# near-api-kotlin

Kotlin API to interact with NEAR via RPC API

API Docs https://docs.near.org/docs/api/rpc

# Usage



# Implementation Details
* Ktor for making requests
* Jackson for serializing/deserializing requests and models
* Kotest and JUnit for testing

## Testing
All tests are divided into several groups:
* End-to-end tests
* Deserialization unit tests

### End-to-end tests
These tests perform calls to NEAR testnet using special account (api_kotlin.testnet) if needed.
This is useful for testing the equality of responses by different request parameters.
Also end-to-end tests are designed to fail in case of changing RPC API in testnet without backward compatibility.

### Deserialization unit tests
These tests use prepared JSON responses based on a real for many variants.
This stabilizes API and prevents the appearance of bugs during refactoring of future improvements.

# Examples

# License
This repository is distributed under the terms of both the MIT license and the Apache License (Version 2.0). See LICENSE and LICENSE-APACHE for details.

[`Repository URL`]: https://github.com/antlapit/near-api-kotlin

[`JsonRpcProvider`]: https://github.com/antlapit/near-api-kotlin/providers/src/main/kotlin/antlapit/near/api/providers/base/JsonRpcProvider.kt
