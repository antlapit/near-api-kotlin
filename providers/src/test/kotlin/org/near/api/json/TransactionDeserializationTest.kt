package org.near.api.json

import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.near.api.common.TestData
import org.near.api.model.block.Action
import org.near.api.model.primitives.ActionErrorKind
import org.near.api.model.primitives.PublicKey
import org.near.api.model.primitives.TxExecutionError
import org.near.api.model.transaction.*
import java.math.BigInteger

@ExperimentalKotest
class TransactionDeserializationTest : FunSpec({

    val mapper = ObjectMapperFactory.newInstance()

    context("Execution status") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """"Unknown"""", ExecutionStatus.Unknown
            ),
            TestData(
                """{"SuccessReceiptId":"BLV2q6p8DX7pVgXRtGtBkyUNrnqkNyU7iSksXG7BjVZh"}""",
                ExecutionStatus.SuccessReceiptId(receiptId = "BLV2q6p8DX7pVgXRtGtBkyUNrnqkNyU7iSksXG7BjVZh")

            ),
            TestData(
                """{"SuccessValue":"result"}""",
                ExecutionStatus.SuccessValue(value = "result")
            ),
            TestData(
                """{"SuccessValue":""}""",
                ExecutionStatus.SuccessValue(value = "")
            ),
            TestData(
                """
                {
                    "Failure": {
                        "ActionError": {
                            "index": 1,
                            "kind": {
                                "AccountAlreadyExists": {
                                    "account_id": "test"
                                }
                            }
                        }
                    }
                }        
                """,
                ExecutionStatus.Failure(
                    TxExecutionError.ActionError(
                        kind = ActionErrorKind.AccountAlreadyExists(
                            accountId = "test"
                        ),
                        index = 1
                    )
                )
            )
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as ExecutionStatus shouldBe obj
            }
        }
    }

    context("Final execution status") {
        withData(
            nameFn = { "${it.typed}" },
            TestData("\"NotStarted\"", FinalExecutionStatus.NotStarted),
            TestData("\"Started\"", FinalExecutionStatus.Started),
            TestData(
                """{"SuccessValue":"result"}""",
                FinalExecutionStatus.SuccessValue(value = "result")
            ),
            TestData(
                """{"SuccessValue":""}""",
                FinalExecutionStatus.SuccessValue(value = "")
            ),
            TestData(
                """
                {
                    "Failure": {
                        "ActionError": {
                            "index": 1,
                            "kind": {
                                "AccountAlreadyExists": {
                                    "account_id": "test"
                                }
                            }
                        }
                    }
                }
                """,
                FinalExecutionStatus.Failure(
                    TxExecutionError.ActionError(
                        kind = ActionErrorKind.AccountAlreadyExists(
                            accountId = "test"
                        ),
                        index = 1
                    )
                )
            )
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as FinalExecutionStatus shouldBe obj
            }
        }
    }

    context("Final execution outcome") {
        withData(
            nameFn = { "${it.typed}" },
            listOf(
                TestData(
                    """
                        {
                           "status":{
                              "SuccessValue":"success"
                           },
                           "transaction":{
                              "signer_id":"namlebao19.testnet",
                              "public_key":"ed25519:AfEKZddqE52chy49b2tFqnULJLjKyzE8JecH8DMcTV4L",
                              "nonce":71618374000001,
                              "receiver_id":"namlebao19.testnet",
                              "actions":[{
                                "AddKey":{
                                   "public_key":"ed25519:28PPWHwSLteXJE2HSpbHnbgrsKri9tTWwp62sUFh2WpA",
                                   "access_key":{
                                      "nonce":0,
                                      "permission":"FullAccess"
                                   }
                                }
                              }],
                              "signature":"ed25519:5kWiAMsz9Q5NZmgXvoUKurcXUHH1fq3xpagDFRuEAtsA25y9wGUPZENZhbf4LNkUsCB9jwrEZZpiX51nj5w8BioY",
                              "hash":"AB6pehcunRvvo2ErEWnWsAUX3xFsDTy6He2SgiwqJZQt"
                           },
                           "transaction_outcome":{
                              "proof":[{
                                "hash":"7PXJg1oWg2GZW2hewGNvRH5cafJBTs7PN4ApcFetntN5",
                                "direction":"Left"
                              }],
                              "block_hash":"4fcBTYkY1A49Wr8JKm2C5LGepBioFFHWfK3a7pFkEX3W",
                              "id":"AB6pehcunRvvo2ErEWnWsAUX3xFsDTy6He2SgiwqJZQt",
                              "outcome":{
                                 "logs":[
                                    "some log"
                                 ],
                                 "receipt_ids":[
                                    "J5auLFssiuMxFYyXT7Q4G26otMZzz4ryXkpL5U46Sc3Y"
                                 ],
                                 "gas_burnt":209824625000,
                                 "tokens_burnt":"20982462500000000000",
                                 "executor_id":"namlebao19.testnet",
                                 "status":{
                                    "SuccessReceiptId":"J5auLFssiuMxFYyXT7Q4G26otMZzz4ryXkpL5U46Sc3Y"
                                 },
                                 "metadata":{
                                    "version":1,
                                    "gas_profile": [
                                        {
                                            "cost_category":"category", 
                                            "cost":"123", 
                                            "gas_used":1
                                        }
                                    ]
                                 }
                              }
                           },
                           "receipts_outcome":[
                              {
                              "proof":[{
                                "hash":"7PXJg1oWg2GZW2hewGNvRH5cafJBTs7PN4ApcFetntN5",
                                "direction":"Left"
                              }],
                              "block_hash":"4fcBTYkY1A49Wr8JKm2C5LGepBioFFHWfK3a7pFkEX3W",
                              "id":"AB6pehcunRvvo2ErEWnWsAUX3xFsDTy6He2SgiwqJZQt",
                              "outcome":{
                                 "logs":[
                                    "some log"
                                 ],
                                 "receipt_ids":[
                                    "J5auLFssiuMxFYyXT7Q4G26otMZzz4ryXkpL5U46Sc3Y"
                                 ],
                                 "gas_burnt":209824625000,
                                 "tokens_burnt":"20982462500000000000",
                                 "executor_id":"namlebao19.testnet",
                                 "status":{
                                    "SuccessReceiptId":"J5auLFssiuMxFYyXT7Q4G26otMZzz4ryXkpL5U46Sc3Y"
                                 },
                                 "metadata":{
                                    "version":1,
                                    "gas_profile": [
                                        {
                                            "cost_category":"category", 
                                            "cost":"123", 
                                            "gas_used":1
                                        }
                                    ]
                                 }
                              }
                           }
                           ]
                        }
                    """,
                    FinalExecutionOutcome(
                        status = FinalExecutionStatus.SuccessValue(value = "success"),
                        transaction = SignedTransactionView(
                            signerId = "namlebao19.testnet",
                            publicKey = PublicKey("ed25519:AfEKZddqE52chy49b2tFqnULJLjKyzE8JecH8DMcTV4L"),
                            nonce = 71618374000001,
                            receiverId = "namlebao19.testnet",
                            actions = listOf(
                                Action.AddKey(
                                    publicKey = PublicKey("ed25519:28PPWHwSLteXJE2HSpbHnbgrsKri9tTWwp62sUFh2WpA"),
                                    accessKey = org.near.api.model.accesskey.AccessKey(
                                        nonce = 0,
                                        permission = org.near.api.model.accesskey.AccessKeyPermission.FullAccess
                                    )
                                )
                            ),
                            signature = "ed25519:5kWiAMsz9Q5NZmgXvoUKurcXUHH1fq3xpagDFRuEAtsA25y9wGUPZENZhbf4LNkUsCB9jwrEZZpiX51nj5w8BioY",
                            hash = "AB6pehcunRvvo2ErEWnWsAUX3xFsDTy6He2SgiwqJZQt"
                        ),
                        transactionOutcome = ExecutionOutcomeWithIdView(
                            proof = listOf(
                                MerklePathItem(
                                    hash = "7PXJg1oWg2GZW2hewGNvRH5cafJBTs7PN4ApcFetntN5",
                                    direction = Direction.Left
                                )
                            ),
                            blockHash = "4fcBTYkY1A49Wr8JKm2C5LGepBioFFHWfK3a7pFkEX3W",
                            id = "AB6pehcunRvvo2ErEWnWsAUX3xFsDTy6He2SgiwqJZQt",
                            outcome = ExecutionOutcomeView(
                                logs = listOf("some log"),
                                receiptIds = listOf("J5auLFssiuMxFYyXT7Q4G26otMZzz4ryXkpL5U46Sc3Y"),
                                gasBurnt = 209824625000,
                                tokensBurnt = BigInteger("20982462500000000000"),
                                executorId = "namlebao19.testnet",
                                status = ExecutionStatus.SuccessReceiptId("J5auLFssiuMxFYyXT7Q4G26otMZzz4ryXkpL5U46Sc3Y"),
                                metadata = ExecutionMetadataView(
                                    version = 1,
                                    gasProfile = listOf(
                                        CostGasUsed(costCategory = "category", cost = "123", gasUsed = 1)
                                    )
                                )
                            )
                        ),
                        receiptsOutcome = listOf(
                            ExecutionOutcomeWithIdView(
                                proof = listOf(
                                    MerklePathItem(
                                        hash = "7PXJg1oWg2GZW2hewGNvRH5cafJBTs7PN4ApcFetntN5",
                                        direction = Direction.Left
                                    )
                                ),
                                blockHash = "4fcBTYkY1A49Wr8JKm2C5LGepBioFFHWfK3a7pFkEX3W",
                                id = "AB6pehcunRvvo2ErEWnWsAUX3xFsDTy6He2SgiwqJZQt",
                                outcome = ExecutionOutcomeView(
                                    logs = listOf("some log"),
                                    receiptIds = listOf("J5auLFssiuMxFYyXT7Q4G26otMZzz4ryXkpL5U46Sc3Y"),
                                    gasBurnt = 209824625000,
                                    tokensBurnt = BigInteger("20982462500000000000"),
                                    executorId = "namlebao19.testnet",
                                    status = ExecutionStatus.SuccessReceiptId("J5auLFssiuMxFYyXT7Q4G26otMZzz4ryXkpL5U46Sc3Y"),
                                    metadata = ExecutionMetadataView(
                                        version = 1,
                                        gasProfile = listOf(
                                            CostGasUsed(costCategory = "category", cost = "123", gasUsed = 1)
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as FinalExecutionOutcome shouldBe obj
            }
        }
    }
})
