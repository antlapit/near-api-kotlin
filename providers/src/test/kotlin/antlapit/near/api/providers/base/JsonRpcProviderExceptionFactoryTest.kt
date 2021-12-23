package antlapit.near.api.providers.base

import antlapit.near.api.common.TestData
import antlapit.near.api.json.ObjectMapperFactory
import antlapit.near.api.providers.exception.*
import antlapit.near.api.providers.model.primitives.InvalidTxError
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


@ExperimentalKotest
class JsonRpcProviderExceptionFactoryTest : FunSpec({

    val mapper = ObjectMapperFactory.newInstance()
    val factory = JsonRpcProviderExceptionFactory(mapper)

    context("deserialize provider exception") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """"Raw message"""",
                ProviderException("Raw message")
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
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                val node = mapper.readValue(a) as JsonNode
                val typed = factory.fromJsonNode(node)
                typed shouldBe b
            }
        }

    }

})
