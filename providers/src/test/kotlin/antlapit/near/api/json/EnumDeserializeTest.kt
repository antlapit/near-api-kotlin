package antlapit.near.api.json

import antlapit.near.api.providers.model.primitives.ActionErrorKind
import antlapit.near.api.providers.model.primitives.TxExecutionError
import antlapit.near.api.providers.model.transaction.ExecutionStatus
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

@ExperimentalKotest
class EnumDeserializeTest : FunSpec({

    val mapper = ObjectMapperFactory.newInstance()

    data class DeserializeCase(
        val raw: String,
        val typed: Any
    )

    context("ExecutionStatus") {
        withData<DeserializeCase>(
            { "${it.typed}" },
            DeserializeCase(
                """
            "Unknown"
            """.trimIndent(), ExecutionStatus.Unknown
            ),
            DeserializeCase(
                """
                {"SuccessReceiptId":"BLV2q6p8DX7pVgXRtGtBkyUNrnqkNyU7iSksXG7BjVZh"}        
                """.trimIndent(),
                ExecutionStatus.SuccessReceiptId(
                    receiptId = "BLV2q6p8DX7pVgXRtGtBkyUNrnqkNyU7iSksXG7BjVZh"
                )

            ),
            DeserializeCase(
                """
                {"SuccessValue":"result"}        
                """.trimIndent(),
                ExecutionStatus.SuccessValue(
                    value = "result"
                )
            ),
            DeserializeCase(
                """
                {"SuccessValue":""}        
                """.trimIndent(),
                ExecutionStatus.SuccessValue(
                    value = ""
                )
            ),
            DeserializeCase(
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
                """.trimIndent(),
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
                mapper.readValue<ExecutionStatus>(str) shouldBe obj
            }
        }
    }
})
