package antlapit.near.api.deser

import antlapit.near.api.providers.JsonRpcProvider
import antlapit.near.api.providers.model.accesskey.AccessKey
import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.accesskey.AccessKeyPermission.FunctionCall
import antlapit.near.api.providers.model.transaction.ExecutionStatus
import antlapit.near.api.providers.primitives.ActionErrorKind
import antlapit.near.api.providers.primitives.TxExecutionError
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.math.BigInteger

@ExperimentalKotest
class EnumDeserializeTest : FunSpec({
    val mapper = JsonRpcProvider.defaultMapper()

    data class DeserializeCase(
        val raw: String,
        val typed: Any
    )

    context("AccessKeyPermission") {
        withData<DeserializeCase>(
            { "${(it.typed as AccessKey).permission::class.simpleName}" },
            DeserializeCase(
                """
            {
                "nonce": 2,        
                "permission": {
                    "FunctionCall": {
                        "allowance": "10",
                        "receiver_id": "example.testnet",
                        "method_names": ["test"]
                    }
                }
            }
            """.trimIndent(), AccessKey(
                    nonce = 2,
                    permission = FunctionCall(BigInteger.TEN, "example.testnet", listOf("test"))
                )
            ),
            DeserializeCase(
                """
            {
                "nonce": 2,        
                "permission": "FullAccess"
            }
            """.trimIndent(), AccessKey(
                    nonce = 2,
                    permission = AccessKeyPermission.FullAccess
                )
            )
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue<AccessKey>(str) shouldBe obj
            }
        }
    }

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
