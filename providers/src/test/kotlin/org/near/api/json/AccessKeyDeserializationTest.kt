package org.near.api.json

import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.near.api.common.TestData
import org.near.api.model.primitives.PublicKey
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
                    org.near.api.model.accesskey.AccessKeysContainer(
                        blockHeight = 73947176,
                        blockHash = "jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c",
                        keys = listOf(
                            org.near.api.model.accesskey.AccessKeyInfo(
                                publicKey = PublicKey("ed25519:6PKfSu4zZarrFVk1Z4uf8kjiNwbMHSaiUmBgaWYF1dCj"),
                                accessKey = org.near.api.model.accesskey.AccessKey(
                                    nonce = 69877007000001,
                                    permission = org.near.api.model.accesskey.AccessKeyPermission.FullAccess,
                                )
                            )
                        )
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as org.near.api.model.accesskey.AccessKeysContainer shouldBe b
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
                    org.near.api.model.accesskey.AccessKeyInBlock(
                        nonce = 69877007000001,
                        permission = org.near.api.model.accesskey.AccessKeyPermission.FullAccess,
                        blockHeight = 73947176,
                        blockHash = "jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c"
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as org.near.api.model.accesskey.AccessKeyInBlock shouldBe b
            }
        }
    }

    context("Access Key Permission") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                "\"FullAccess\"",
                org.near.api.model.accesskey.AccessKeyPermission.FullAccess
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
                org.near.api.model.accesskey.AccessKeyPermission.FunctionCall(
                    allowance = BigInteger("250000000000000000000000"),
                    receiverId = "dummy.testnet",
                    methodNames = listOf("test")
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as org.near.api.model.accesskey.AccessKeyPermission shouldBe b
            }
        }
    }
})
