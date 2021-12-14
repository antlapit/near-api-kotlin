package antlapit.near.api.json

import antlapit.near.api.providers.model.accesskey.*
import antlapit.near.api.providers.model.primitives.PublicKey
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.math.BigInteger

/**
 * Access keys deserialization test
 */
@ExperimentalKotest
class AccessKeyDeserializationTest : FunSpec({

    val objectMapper = ObjectMapperFactory.newInstance()

    context("Access Keys Container") {
        withData(
            nameFn = { "${it.typed.keys[0].publicKey} ${it.typed.keys[0].accessKey.permission}" },
            listOf(
                TestData(
                    """
                {
                    "block_height": 73947176,
                    "block_hash": "jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c",
                    "keys": [
                        {
                            "public_key": "ed25519:6PKfSu4zZarrFVk1Z4uf8kjiNwbMHSaiUmBgaWYF1dCj",
                            "access_key": {
                                "nonce": 69877007000001,
                                "permission": "FullAccess"
                            }
                        }
                    ]
                }
                """,
                    AccessKeysContainer(
                        blockHeight = 73947176,
                        blockHash = "jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c",
                        keys = listOf(
                            AccessKeyInfo(
                                publicKey = PublicKey("ed25519:6PKfSu4zZarrFVk1Z4uf8kjiNwbMHSaiUmBgaWYF1dCj"),
                                accessKey = AccessKey(
                                    nonce = 69877007000001,
                                    permission = AccessKeyPermission.FullAccess,
                                )
                            )
                        )
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as AccessKeysContainer shouldBe b
            }
        }
    }

    context("Access Key") {
        withData(
            nameFn = { "${it.typed.permission}" },
            listOf(
                TestData(
                    """
                {
                    "nonce": 69877007000001,
                    "permission": "FullAccess",
                    "block_height": 73947176,
                    "block_hash": "jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c"
                }
                """,
                    AccessKeyInBlock(
                        nonce = 69877007000001,
                        permission = AccessKeyPermission.FullAccess,
                        blockHeight = 73947176,
                        blockHash = "jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c"
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as AccessKeyInBlock shouldBe b
            }
        }
    }

    context("Access Key Permission") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                "\"FullAccess\"",
                AccessKeyPermission.FullAccess
            ),
            TestData(
                """
                {
                    "FunctionCall": {
                        "allowance":"250000000000000000000000",
                        "receiver_id":"dummy.testnet",
                        "method_names":["test"]
                    }
                }
                """,
                AccessKeyPermission.FunctionCall(
                    allowance = BigInteger("250000000000000000000000"),
                    receiverId = "dummy.testnet",
                    methodNames = listOf("test")
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as AccessKeyPermission shouldBe b
            }
        }
    }
})
