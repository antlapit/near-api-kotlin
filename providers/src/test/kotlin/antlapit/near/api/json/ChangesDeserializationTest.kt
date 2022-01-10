package antlapit.near.api.json

import antlapit.near.api.common.TestData
import antlapit.near.api.providers.camelCaseToSnakeCase
import antlapit.near.api.providers.model.accesskey.AccessKey
import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.changes.AccessKeyChange
import antlapit.near.api.providers.model.changes.AccessKeysChangesContainer
import antlapit.near.api.providers.model.changes.StateChange
import antlapit.near.api.providers.model.changes.StateChangeCause
import antlapit.near.api.providers.model.primitives.PublicKey
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

/**
 * Access key change container deserialization test
 */
@ExperimentalKotest
class ChangesDeserializationTest : FunSpec({

    val objectMapper = ObjectMapperFactory.newInstance()

    context("Access Key Change Container") {
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
                    AccessKeysChangesContainer(
                        blockHash = "AWXoLJtyQaYkogEjR96NExSDXpwspw5UAfLaHUP8QyB2",
                        changes = listOf(
                            AccessKeyChange(
                                cause = StateChangeCause.TransactionProcessing(
                                    txHash = "DKUAQ9ovwb31AnzxyaVUkECBSHcjPoUKb6TZrsCy3b4J"
                                ),
                                change = StateChange.AccessKeyUpdate(
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
                objectMapper.readValue(a) as AccessKeysChangesContainer shouldBe b
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
                .map { TestData("""
                    {
                        "type":"${it.objectInstance!!::class.simpleName!!.camelCaseToSnakeCase()}"
                    }
                """.trimIndent(), it.objectInstance) },
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                (objectMapper.readValue(str) as StateChangeCause)::class shouldBe obj!!::class
            }
        }

    }
})
