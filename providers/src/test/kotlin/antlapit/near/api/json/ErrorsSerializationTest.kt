package antlapit.near.api.json

import antlapit.near.api.common.TestData
import antlapit.near.api.providers.model.primitives.ActionsValidationError
import antlapit.near.api.providers.model.primitives.InvalidAccessKeyErrorType
import antlapit.near.api.providers.model.primitives.InvalidTxError
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.math.BigInteger

@ExperimentalKotest
class ErrorsSerializationTest : FunSpec({

    val mapper = ObjectMapperFactory.newInstance()

    context("Invalid tx error") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """{"InvalidAccessKeyError":"RequiresFullAccess"}""",
                InvalidTxError.InvalidAccessKeyError(InvalidAccessKeyErrorType.RequiresFullAccess)
            ),
            TestData(
                """{"InvalidSignerId":{"signer_id":"signer"}}""",
                InvalidTxError.InvalidSignerId(signerId = "signer")
            ),
            TestData(
                """{"SignerDoesNotExist":{"signer_id":"signer"}}""",
                InvalidTxError.SignerDoesNotExist(signerId = "signer")
            ),
            TestData(
                """{"InvalidNonce":{"tx_nonce":1,"ak_nonce":2}}""",
                InvalidTxError.InvalidNonce(txNonce = 1, akNonce = 2)
            ),
            TestData(
                """{"NonceTooLarge":{"tx_nonce":1,"upper_bound":2}}""",
                InvalidTxError.NonceTooLarge(txNonce = 1, upperBound = 2)
            ),
            TestData(
                """{"InvalidReceiverId":{"receiver_id":"signer"}}""",
                InvalidTxError.InvalidReceiverId(receiverId = "signer")
            ),
            TestData("\"InvalidSignature\"", InvalidTxError.InvalidSignature),
            TestData(
                """{"NotEnoughBalance":{"signer_id":"signer","balance":10,"cost":10}}""",
                InvalidTxError.NotEnoughBalance(
                    signerId = "signer",
                    balance = BigInteger.TEN,
                    cost = BigInteger.TEN
                )
            ),
            TestData(
                """{"LackBalanceForState":{"signer_id":"signer","amount":10}}""",
                InvalidTxError.LackBalanceForState(
                    signerId = "signer",
                    amount = BigInteger.TEN
                )
            ),
            TestData("\"CostOverflow\"", InvalidTxError.CostOverflow),
            TestData("\"InvalidChain\"", InvalidTxError.InvalidChain),
            TestData("\"Expired\"", InvalidTxError.Expired),
            TestData(
                """{"ActionsValidation":"DeleteActionMustBeFinal"}""",
                InvalidTxError.ActionsValidation(
                    error = ActionsValidationError.DeleteActionMustBeFinal
                )
            ),
            TestData(
                """{"TransactionSizeExceeded":{"size":1,"limit":2}}""",
                InvalidTxError.TransactionSizeExceeded(
                    size = 1,
                    limit = 2
                )
            ),
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                mapper.writeValueAsString(b) shouldBe a
            }
        }
    }
})
