package antlapit.near.api.json

import antlapit.near.api.providers.model.primitives.ActionErrorKind
import antlapit.near.api.providers.model.primitives.TxExecutionError
import antlapit.near.api.providers.model.transaction.ExecutionStatus
import antlapit.near.api.providers.model.transaction.FinalExecutionStatus
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

@ExperimentalKotest
class TransactionDeserializeTest : FunSpec({

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

})
