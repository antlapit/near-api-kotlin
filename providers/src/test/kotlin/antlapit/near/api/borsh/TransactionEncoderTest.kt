package antlapit.near.api.borsh

import antlapit.near.api.providers.decodeBase64
import antlapit.near.api.providers.model.accesskey.AccessKey
import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.primitives.PublicKey
import antlapit.near.api.providers.model.transaction.SignedTransaction
import antlapit.near.api.providers.model.transaction.Transaction
import antlapit.near.api.providers.model.transaction.TransactionSignature
import antlapit.near.api.providers.util.toHexString
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.Tuple2
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.math.BigInteger

@ExperimentalKotest
class TransactionEncoderTest : FunSpec({

    context("Serialize transaction") {
        withData(
            nameFn = { "${it.b}" },
            Tuple2(
                "09000000746573742e6e65617200917b3d268d4b58f7fec1b150bd68d69be3ee5d4cc39855e341538465bb77860d01000000000000000d00000077686174657665722e6e6561720fa473fd26901df296be6adc4cc4df34d040efa2435224b6986910e630c2fef6010000000301000000000000000000000000000000",
                Transaction(
                    signerId = "test.near",
                    publicKey = PublicKey("Anu7LYDfpLtkP7E16LT9imXF694BdQaa9ufVkQiwTQxC"),
                    nonce = 1,
                    receiverId = "whatever.near",
                    actions = listOf(
                        Action.Transfer(BigInteger.valueOf(1))
                    ),
                    blockHash = "244ZQ9cgj3CQ6bWBdytfrJMuMQ1jdXLFGnr4HhvtCTnM"
                ),

                ),
            Tuple2(
                "09000000746573742e6e656172000f56a5f028dfc089ec7c39c1183b321b4d8f89ba5bec9e1762803cc2491f6ef80100000000000000030000003132330fa473fd26901df296be6adc4cc4df34d040efa2435224b6986910e630c2fef608000000000103000000010203020300000071717103000000010203e80300000000000040420f00000000000000000000000000037b0000000000000000000000000000000440420f00000000000000000000000000000f56a5f028dfc089ec7c39c1183b321b4d8f89ba5bec9e1762803cc2491f6ef805000f56a5f028dfc089ec7c39c1183b321b4d8f89ba5bec9e1762803cc2491f6ef800000000000000000000030000007a7a7a010000000300000077777706000f56a5f028dfc089ec7c39c1183b321b4d8f89ba5bec9e1762803cc2491f6ef80703000000313233",
                Transaction(
                    signerId = "test.near",
                    publicKey = PublicKey("5ZGzNvMNqV2g29YdMuYNfXi9LYa3mTqdxWXt9Nx4xF5tb"),
                    nonce = 1,
                    receiverId = "123",
                    actions = listOf(
                        Action.CreateAccount,
                        Action.DeployContract(listOf(Char(1), Char(2), Char(3)).joinToString("")),
                        Action.FunctionCall(
                            methodName = "qqq",
                            gas = 1000,
                            deposit = BigInteger.valueOf(1000000),
                            args = listOf(Char(1), Char(2), Char(3)).joinToString("")
                        ),
                        Action.Transfer(deposit = BigInteger.valueOf(123)),
                        Action.Stake(
                            stake = BigInteger.valueOf(1000000),
                            publicKey = PublicKey("5ZGzNvMNqV2g29YdMuYNfXi9LYa3mTqdxWXt9Nx4xF5tb")
                        ),
                        Action.AddKey(
                            publicKey = PublicKey("5ZGzNvMNqV2g29YdMuYNfXi9LYa3mTqdxWXt9Nx4xF5tb"),
                            accessKey = AccessKey.functionCallAccessKey(
                                receiverId = "zzz",
                                methodNames = listOf("www"),
                                allowance = null
                            )
                        ),
                        Action.DeleteKey(
                            publicKey = PublicKey("5ZGzNvMNqV2g29YdMuYNfXi9LYa3mTqdxWXt9Nx4xF5tb")
                        ),
                        Action.DeleteAccount(
                            beneficiaryId = "123"
                        )
                    ),
                    blockHash = "244ZQ9cgj3CQ6bWBdytfrJMuMQ1jdXLFGnr4HhvtCTnM"
                ),

                ),
        ) { (expected, transaction) ->
            shouldNotThrow<Throwable> {
                val encoded = transaction.encode()
                val hex = encoded.toHexString()
                hex shouldBe expected
            }
        }
    }

    context("serialize signed transaction") {
        withData(
            nameFn = { "${it.b}" },
            listOf(
                Tuple2(
                    "EgAAAGFwaV9rb3RsaW4udGVzdG5ldACS8k/5XB4GHHFbzmqimY9rCgP2ToFslyFCyHBIW2WUH4nxmIKNPwAAEAAAAGFudGxhcGl0LnRlc3RuZXRdEM0piVbS61/+AV2EDZqdYM09CtoPlEJ9uLpd4HB7/gEAAAADAAAAoe3MzhvC0wAAAAAAAAC74T6jjSI2fpO3RHS8mqiTfDMzMUxmWD6igsz3vUFQHrIlQo7bVJ8Ati7rjpXg4Q6QIzjxTT81hgLqfzPfTI0B",
                    SignedTransaction(
                        transaction = Transaction(
                            signerId = "api_kotlin.testnet",
                            publicKey = PublicKey("AtcpvHyqTULXBjmM2GDMrZN3AXy4HauxcpW7rC4kUHkS"),
                            nonce = 69877014000009,
                            receiverId = "antlapit.testnet",
                            actions = listOf(
                                Action.Transfer(BigInteger("1000000000000000000000000"))
                            ),
                            blockHash = "7GHmxbmB6Uk5eaZuJ482QxMHCzRN9jkr1kmDX1rQFWS1"
                        ),
                        signature = TransactionSignature(
                            "4ksG9frZi6sN7XyeGmaJ65s7GzmBuVZSsCi6dmFyzxnJ1jMKrqmRK3eew699G6LMn31hnvhn72yXcy2rnMNuWent"
                        )
                    )
                )
            )
        ) { (expected, signedTransaction) ->
            shouldNotThrow<Throwable> {
                val expectedSignedTransaction = expected.decodeBase64()
                val expectedHex = expectedSignedTransaction.toHexString()
                val encoded = signedTransaction.encode()
                val hex = encoded.toHexString()
                hex shouldBe expectedHex
            }
        }
    }
})
