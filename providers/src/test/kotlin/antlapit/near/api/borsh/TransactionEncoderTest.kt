package antlapit.near.api.borsh

import antlapit.near.api.providers.model.accesskey.AccessKey
import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.primitives.PublicKey
import antlapit.near.api.providers.model.transaction.Transaction
import antlapit.near.api.providers.util.toHexString
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.near.borshj.Borsh
import java.math.BigInteger

@ExperimentalKotest
class TransactionEncoderTest : FunSpec({

    context("Serialize transaction") {
        withData(
            nameFn = { "${it.second}" },
                Pair(
                    "09000000746573742e6e65617200917b3d268d4b58f7fec1b150bd68d69be3ee5d4cc39855e341538465bb77860d01000000000000000d00000077686174657665722e6e6561720fa473fd26901df296be6adc4cc4df34d040efa2435224b6986910e630c2fef6010000000301000000000000000000000000000000",
                    Transaction(
                        signerId = "test.near",
                        publicKey = PublicKey("Anu7LYDfpLtkP7E16LT9imXF694BdQaa9ufVkQiwTQxC"),
                        nonce = 1,
                        receiverId = "whatever.near",
                        hash = "244ZQ9cgj3CQ6bWBdytfrJMuMQ1jdXLFGnr4HhvtCTnM",
                        actions = listOf(
                            Action.Transfer(BigInteger.valueOf(1))
                        )
                    )
            ),
            Pair(
                "09000000746573742e6e656172000f56a5f028dfc089ec7c39c1183b321b4d8f89ba5bec9e1762803cc2491f6ef80100000000000000030000003132330fa473fd26901df296be6adc4cc4df34d040efa2435224b6986910e630c2fef608000000000103000000010203020300000071717103000000010203e80300000000000040420f00000000000000000000000000037b0000000000000000000000000000000440420f00000000000000000000000000000f56a5f028dfc089ec7c39c1183b321b4d8f89ba5bec9e1762803cc2491f6ef805000f56a5f028dfc089ec7c39c1183b321b4d8f89ba5bec9e1762803cc2491f6ef800000000000000000000030000007a7a7a010000000300000077777706000f56a5f028dfc089ec7c39c1183b321b4d8f89ba5bec9e1762803cc2491f6ef80703000000313233",
                Transaction(
                    signerId = "test.near",
                    publicKey = PublicKey("5ZGzNvMNqV2g29YdMuYNfXi9LYa3mTqdxWXt9Nx4xF5tb"),
                    nonce = 1,
                    receiverId = "123",
                    hash = "244ZQ9cgj3CQ6bWBdytfrJMuMQ1jdXLFGnr4HhvtCTnM",
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
                    )
                )
            )
        ) { (expected, transaction) ->
            shouldNotThrow<Throwable> {
                val encoded = Borsh.serialize(transaction.toBorsh())
                val hex = encoded.toHexString()
                hex shouldBe expected
            }
        }
    }
})
