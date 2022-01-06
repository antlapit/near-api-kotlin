package antlapit.near.api.providers.base

import antlapit.near.api.common.TestData
import antlapit.near.api.json.ObjectMapperFactory
import antlapit.near.api.providers.exception.*
import antlapit.near.api.providers.model.primitives.InvalidTxError
import antlapit.near.api.providers.model.primitives.PublicKey
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


@ExperimentalKotest
class ExceptionFactoryTest : FunSpec({

    val mapper = ObjectMapperFactory.newInstance()
    val factory = ExceptionFactory(mapper)

    context("deserialize provider exception") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """1""",
                ProviderException("Undefined response error")
            ),
            TestData(
                """"Raw message"""",
                ProviderException("Raw message")
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"UNKNOWN_BLOCK",
                      "info":{"field": "value"}
                   }
                }
                """.trimIndent(),
                UnknownBlockException("""{"field":"value"}""")
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"UNKNOWN_CHUNK",
                      "info":{"chunk_hash": "123"}
                   }
                }
                """.trimIndent(),
                UnknownChunkException(chunkHash = "123")
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"INVALID_SHARD_ID",
                      "info":{"shard_id": 1}
                   }
                }
                """.trimIndent(),
                InvalidShardIdException(shardId = 1)
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"NOT_SYNCED_YET",
                      "info":{"field": "value"}
                   }
                }
                """.trimIndent(),
                NotSyncedException("""{"field":"value"}""")
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"INVALID_ACCOUNT",
                      "info":{
                        "requested_account_id": "123",
                        "block_height": 1,
                        "block_hash": "456"
                      }
                   }
                }
                """.trimIndent(),
                InvalidAccountException("123", 1, "456")
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"UNKNOWN_ACCOUNT",
                      "info":{
                        "requested_account_id": "123",
                        "block_height": 1,
                        "block_hash": "456"
                      }
                   }
                }
                """.trimIndent(),
                UnknownAccountException("123", 1, "456")
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"UNKNOWN_ACCESS_KEY",
                      "info":{
                        "public_key": "5ZGzNvMNqV2g29YdMuYNfXi9LYa3mTqdxWXt9Nx4xF5tb",
                        "block_height": 1,
                        "block_hash": "456"
                      }
                   }
                }
                """.trimIndent(),
                UnknownAccessKeyException(PublicKey("5ZGzNvMNqV2g29YdMuYNfXi9LYa3mTqdxWXt9Nx4xF5tb"), 1, "456")
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"UNAVAILABLE_SHARD",
                      "info":{"field": "value"}
                   }
                }
                """.trimIndent(),
                UnavailableShardException("""{"field":"value"}""")
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"NO_SYNCED_BLOCKS",
                      "info":{"field": "value"}
                   }
                }
                """.trimIndent(),
                NoSyncedBlocksException("""{"field":"value"}""")
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"INVALID_TRANSACTION",
                      "info":{}
                   },
                   "code":-32000,
                   "message":"Server error",
                   "data":{
                      "TxExecutionError":{
                         "InvalidTxError":{
                            "InvalidNonce":{
                               "tx_nonce":75126620000027,
                               "ak_nonce":75126620000027
                            }
                         }
                      }
                   }
                }
                """.trimIndent(),
                InvalidTransactionException(
                    txExecutionError = InvalidTxError.InvalidNonce(
                        txNonce = 75126620000027,
                        akNonce = 75126620000027
                    )
                )
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"TIMEOUT_ERROR",
                      "info":{"field": "value"}
                   }
                }
                """.trimIndent(),
                TimeoutErrorException("""{"field":"value"}""")
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"UNKNOWN_EPOCH",
                      "info":{"field": "value"}
                   }
                }
                """.trimIndent(),
                UnknownEpochException("""{"field":"value"}""")
            ),
            TestData(
                """
                {
                   "name":"HANDLER_ERROR",
                   "cause":{
                      "name":"dummy",
                      "info":{"field": "value"}
                   }
                }
                """.trimIndent(),
                ProviderException("HANDLER_ERROR", "dummy")
            ),
            TestData(
                """
                {
                   "name":"REQUEST_VALIDATION_ERROR",
                   "cause":{
                      "name":"PARSE_ERROR",
                      "info":{
                        "error_message": "error"
                      }
                   }
                }
                """.trimIndent(),
                ParseErrorException("error")
            ),
            TestData(
                """
                {
                   "name":"REQUEST_VALIDATION_ERROR",
                   "cause":{
                      "name":"dummy",
                      "info":{"field": "value"}
                   }
                }
                """.trimIndent(),
                ProviderException("REQUEST_VALIDATION_ERROR", "dummy")
            ),
            TestData(
                """
                {
                   "name":"INTERNAL_ERROR",
                   "cause":{
                      "name":"INTERNAL_ERROR",
                      "info":{"error_message": "error"}
                   }
                }
                """.trimIndent(),
                InternalErrorException(errorMessage = "error")
            ),
            TestData(
                """
                {
                   "name":"INTERNAL_ERROR",
                   "cause":{
                      "name":"dummy",
                      "info":{"field": "value"}
                   }
                }
                """.trimIndent(),
                ProviderException("INTERNAL_ERROR", "dummy")
            ),
            TestData(
                """
                {
                   "name":"dummyerror",
                   "cause":{
                      "name":"dummy",
                      "info":{"field": "value"}
                   }
                }
                """.trimIndent(),
                ProviderException("dummyerror", "dummy")
            ),
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                val node = mapper.readValue(a) as JsonNode
                val typed = factory.fromJsonNode(node)
                typed shouldBe b
            }
        }

    }

})
