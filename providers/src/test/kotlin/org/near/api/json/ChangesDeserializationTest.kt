package org.near.api.json

import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.near.api.common.TestData
import org.near.api.providers.camelCaseToSnakeCase
import org.near.api.providers.model.accesskey.AccessKey
import org.near.api.providers.model.accesskey.AccessKeyPermission
import org.near.api.providers.model.account.Account
import org.near.api.providers.model.changes.*
import org.near.api.providers.model.primitives.PublicKey
import java.math.BigInteger

/**
 * Access key change container deserialization test
 */
@ExperimentalKotest
class ChangesDeserializationTest : FunSpec({

    val objectMapper = ObjectMapperFactory.newInstance()

    context("State Change Container") {
        withData(
            nameFn = { "${it.typed}" },
            listOf(
                TestData(
                    """
                {
                    "block_hash":"AWXoLJtyQaYkogEjR96NExSDXpwspw5UAfLaHUP8QyB2",
                    "changes":[{
                        "cause":{
                           "type":"transaction_processing",
                           "tx_hash":"DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                        },
                        "type":"access_key_update",
                        "change":{
                           "account_id":"tx1.api_kotlin.testnet",
                           "public_key":"ed25519:5zpBhMxTtD4ozFsBRV9v5hPKTDDFquHqj8gXGERGh6YF",
                           "access_key":{
                              "nonce":75126620000051,
                              "permission":"FullAccess"
                           }
                        }
                    }]
                }
                """,
                    StateChanges(
                        blockHash = "AWXoLJtyQaYkogEjR96NExSDXpwspw5UAfLaHUP8QyB2",
                        changes = listOf(
                            StateChange(
                                cause = StateChangeCause.TransactionProcessing(
                                    txHash = "DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                                ),
                                change = StateChangeType.AccessKeyUpdate(
                                    accountId = "tx1.api_kotlin.testnet",
                                    publicKey = PublicKey("ed25519:5zpBhMxTtD4ozFsBRV9v5hPKTDDFquHqj8gXGERGh6YF"),
                                    accessKey = AccessKey(
                                        nonce = 75126620000051,
                                        permission = AccessKeyPermission.FullAccess
                                    )
                                )
                            )
                        )
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as StateChanges shouldBe b
            }
        }
    }

    context("State Change") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """
                {
                    "cause":{ "type":"initial_state" },
                    "type":"account_update",
                    "change":{
                       "account_id":"tx1.api_kotlin.testnet",
                       "amount": 10,
                       "locked": 0,
                       "code_hash": "ed25519:5zpBhMxTtD4ozFsBRV9v5hPKTDDFquHqj8gXGERGh6YF",
                       "storage_usage": 0,
                       "storage_paid_at": 0
                    }
                }
                """,
                StateChange(
                    cause = StateChangeCause.InitialState,
                    change = StateChangeType.AccountUpdate(
                        accountId = "tx1.api_kotlin.testnet",
                        account = Account(
                            amount = BigInteger.TEN,
                            locked = BigInteger.ZERO,
                            codeHash = "ed25519:5zpBhMxTtD4ozFsBRV9v5hPKTDDFquHqj8gXGERGh6YF",
                            storageUsage = 0,
                            storagePaidAt = 0
                        )
                    )
                )
            ),
            TestData(
                """
                {
                    "cause":{ "type":"initial_state" },
                    "type":"account_deletion",
                    "change":{
                       "account_id":"tx1.api_kotlin.testnet"
                    }
                }
                """,
                StateChange(
                    cause = StateChangeCause.InitialState,
                    change = StateChangeType.AccountDeletion(
                        accountId = "tx1.api_kotlin.testnet"
                    )
                )
            ),
            TestData(
                """
                {
                    "cause":{ "type":"initial_state" },
                    "type":"access_key_update",
                    "change":{
                       "account_id":"tx1.api_kotlin.testnet",
                       "public_key":"ed25519:5zpBhMxTtD4ozFsBRV9v5hPKTDDFquHqj8gXGERGh6YF",
                       "access_key":{
                          "nonce":75126620000051,
                          "permission":"FullAccess"
                       }
                    }
                }
                """,
                StateChange(
                    cause = StateChangeCause.InitialState,
                    change = StateChangeType.AccessKeyUpdate(
                        accountId = "tx1.api_kotlin.testnet",
                        publicKey = PublicKey("ed25519:5zpBhMxTtD4ozFsBRV9v5hPKTDDFquHqj8gXGERGh6YF"),
                        accessKey = AccessKey(
                            nonce = 75126620000051,
                            permission = AccessKeyPermission.FullAccess
                        )
                    )
                )
            ),
            TestData(
                """
                {
                    "cause":{ "type":"initial_state" },
                    "type":"access_key_deletion",
                    "change":{
                       "account_id":"tx1.api_kotlin.testnet",
                       "public_key":"ed25519:5zpBhMxTtD4ozFsBRV9v5hPKTDDFquHqj8gXGERGh6YF"
                    }
                }
                """,
                StateChange(
                    cause = StateChangeCause.InitialState,
                    change = StateChangeType.AccessKeyDeletion(
                        accountId = "tx1.api_kotlin.testnet",
                        publicKey = PublicKey("ed25519:5zpBhMxTtD4ozFsBRV9v5hPKTDDFquHqj8gXGERGh6YF")
                    )
                )
            ),
            TestData(
                """
                {
                    "cause":{ "type":"initial_state" },
                    "type":"data_update",
                    "change":{
                       "account_id":"tx1.api_kotlin.testnet",
                       "key_base64":"key",
                       "value_base64":"value"
                    }
                }
                """,
                StateChange(
                    cause = StateChangeCause.InitialState,
                    change = StateChangeType.DataUpdate(
                        accountId = "tx1.api_kotlin.testnet",
                        keyBase64 = "key",
                        valueBase64 = "value"
                    )
                )
            ),
            TestData(
                """
                {
                    "cause":{ "type":"initial_state" },
                    "type":"data_deletion",
                    "change":{
                       "account_id":"tx1.api_kotlin.testnet",
                       "key_base64":"key"
                    }
                }
                """,
                StateChange(
                    cause = StateChangeCause.InitialState,
                    change = StateChangeType.DataDeletion(
                        accountId = "tx1.api_kotlin.testnet",
                        keyBase64 = "key"
                    )
                )
            ),
            TestData(
                """
                {
                    "cause":{ "type":"initial_state" },
                    "type":"contract_code_update",
                    "change":{
                       "account_id":"tx1.api_kotlin.testnet",
                       "code_base64":"code"
                    }
                }
                """,
                StateChange(
                    cause = StateChangeCause.InitialState,
                    change = StateChangeType.ContractCodeUpdate(
                        accountId = "tx1.api_kotlin.testnet",
                        codeBase64 = "code"
                    )
                )
            ),
            TestData(
                """
                {
                    "cause":{ "type":"initial_state" },
                    "type":"contract_code_deletion",
                    "change":{
                       "account_id":"tx1.api_kotlin.testnet"
                    }
                }
                """,
                StateChange(
                    cause = StateChangeCause.InitialState,
                    change = StateChangeType.ContractCodeDeletion(
                        accountId = "tx1.api_kotlin.testnet"
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as StateChange shouldBe b
            }
        }
    }

    context("State change cause") {
        withData(
            nameFn = { "${it.typed}" },
            listOf(
                TestData(
                    """
                {
                   "type":"transaction_processing",
                   "tx_hash":"DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                }
                """,
                    StateChangeCause.TransactionProcessing(
                        txHash = "DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                    )
                ),
                TestData(
                    """
                {
                   "type":"action_receipt_processing_started",
                   "receipt_hash":"DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                }
                """,
                    StateChangeCause.ActionReceiptProcessingStarted(
                        receiptHash = "DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                    )
                ),
                TestData(
                    """
                {
                   "type":"action_receipt_gas_reward",
                   "receipt_hash":"DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                }
                """,
                    StateChangeCause.ActionReceiptGasReward(
                        receiptHash = "DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                    )
                ),
                TestData(
                    """
                {
                   "type":"receipt_processing",
                   "receipt_hash":"DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                }
                """,
                    StateChangeCause.ReceiptProcessing(
                        receiptHash = "DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                    )
                ),
                TestData(
                    """
                {
                   "type":"postponed_receipt",
                   "receipt_hash":"DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                }
                """,
                    StateChangeCause.PostponedReceipt(
                        receiptHash = "DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as StateChangeCause shouldBe b
            }
        }
    }

    context("State change cause : objects") {
        withData(
            nameFn = { "${it.typed}" },
            StateChangeCause::class.sealedSubclasses
                .filter { it.objectInstance != null }
                .map {
                    TestData(
                        """
                    {
                        "type":"${it.objectInstance!!::class.simpleName!!.camelCaseToSnakeCase()}"
                    }
                """.trimIndent(), it.objectInstance
                    )
                },
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(str) as StateChangeCause shouldBe obj
            }
        }

    }


    context("State Change Kind") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """
                {
                    "type":"account_touched",
                    "account_id":"tx1.api_kotlin.testnet"
                }
                """,
                StateChangeKind.AccountTouched(accountId = "tx1.api_kotlin.testnet")
            ),
            TestData(
                """
                {
                    "type":"access_key_touched",
                    "account_id":"tx1.api_kotlin.testnet"
                }
                """,
                StateChangeKind.AccessKeyTouched(accountId = "tx1.api_kotlin.testnet")
            ),
            TestData(
                """
                {
                    "type":"data_touched",
                    "account_id":"tx1.api_kotlin.testnet"
                }
                """,
                StateChangeKind.DataTouched(accountId = "tx1.api_kotlin.testnet")
            ),
            TestData(
                """
                {
                    "type":"contract_code_touched",
                    "account_id":"tx1.api_kotlin.testnet"
                }
                """,
                StateChangeKind.ContractCodeTouched(accountId = "tx1.api_kotlin.testnet")
            ),
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as StateChangeKind shouldBe b
            }
        }
    }
})
