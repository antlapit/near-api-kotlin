package antlapit.near.api.json

import antlapit.near.api.providers.model.accesskey.*
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.math.BigInteger

@ExperimentalKotest
@ExperimentalCoroutinesApi
class AccessKeyDeserializationTest : FunSpec({

    val objectMapper = ObjectMapperFactory.newInstance()

    context("getAccessKeyList") {
        data class GetAccessKeyListData(
            val raw: String,
            val accessKeys: AccessKeysContainer
        )

        withData<GetAccessKeyListData>(
            { "${it.accessKeys.keys[0].publicKey} ${it.accessKeys.keys[0].accessKey.permission}" },
            GetAccessKeyListData(
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
                            publicKey = "ed25519:6PKfSu4zZarrFVk1Z4uf8kjiNwbMHSaiUmBgaWYF1dCj",
                            accessKey = AccessKey(
                                nonce = 69877007000001,
                                permission = AccessKeyPermission.FullAccess,
                            )
                        )
                    )
                )
            ),
            GetAccessKeyListData(
                """
                {
                    "block_height": 73947176,
                    "block_hash": "jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c",
                    "keys": [
                        {
                            "public_key": "ed25519:6PKfSu4zZarrFVk1Z4uf8kjiNwbMHSaiUmBgaWYF1dCj",
                            "access_key": {
                                "nonce": 69877007000001,
                                "permission": {
                                    "FunctionCall": {
                                        "allowance":"250000000000000000000000",
                                        "receiver_id":"dummy.testnet",
                                        "method_names":["test"]
                                    }
                                }
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
                            publicKey = "ed25519:6PKfSu4zZarrFVk1Z4uf8kjiNwbMHSaiUmBgaWYF1dCj",
                            accessKey = AccessKey(
                                nonce = 69877007000001,
                                permission = AccessKeyPermission.FunctionCall(
                                    allowance = BigInteger("250000000000000000000000"),
                                    receiverId = "dummy.testnet",
                                    methodNames = listOf("test")
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

    context("getAccessKey") {
        data class GetAccessKeyData(
            val raw: String,
            val accessKey: AccessKeyInBlock
        )

        withData<GetAccessKeyData>(
            { "${it.accessKey.permission}" },
            GetAccessKeyData(
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
            ),
            GetAccessKeyData(
                """
                {
                    "nonce": 69877007000001,
                    "permission": {
                        "FunctionCall": {
                            "allowance":"250000000000000000000000",
                            "receiver_id":"dummy.testnet",
                            "method_names":["test"]
                        }
                    },
                    "block_height": 73947176,
                    "block_hash": "jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c"
                }
                """,
                AccessKeyInBlock(
                    nonce = 69877007000001,
                    permission = AccessKeyPermission.FunctionCall(
                        allowance = BigInteger("250000000000000000000000"),
                        receiverId = "dummy.testnet",
                        methodNames = listOf("test")
                    ),
                    blockHeight = 73947176,
                    blockHash = "jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c"
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as AccessKeyInBlock shouldBe b
            }
        }
    }
})
