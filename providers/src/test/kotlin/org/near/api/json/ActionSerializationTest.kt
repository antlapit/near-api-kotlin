package org.near.api.json

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.near.api.common.TestData
import org.near.api.providers.model.accesskey.AccessKey
import org.near.api.providers.model.accesskey.AccessKeyPermission
import org.near.api.providers.model.block.Action
import org.near.api.providers.model.primitives.PublicKey
import java.math.BigInteger

@ExperimentalKotest
class ActionSerializationTest : FunSpec({
    val objectMapper = ObjectMapperFactory.newInstance()

    context("Action") {
        withData(
            nameFn = { "${it.typed}" },
            TestData("\"CreateAccount\"", Action.CreateAccount),
            TestData(
                """{"DeployContract":{"code":"contractcode"}}""",
                Action.DeployContract(code = "contractcode")
            ),
            TestData(
                """{"FunctionCall":{"method_name":"test","args":"args","gas":1,"deposit":10}}""",
                Action.FunctionCall(
                    methodName = "test",
                    args = "args",
                    gas = 1,
                    deposit = BigInteger.TEN
                )
            ),
            TestData(
                """{"Transfer":{"deposit":100}}""",
                Action.Transfer(
                    deposit = BigInteger.valueOf(100)
                )
            ),
            TestData(
                """{"Stake":{"stake":200,"public_key":"ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog"}}""",
                Action.Stake(
                    stake = BigInteger.valueOf(200),
                    publicKey = PublicKey("ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog")
                )
            ),
            TestData(
                """{"AddKey":{"public_key":"ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog","access_key":{"nonce":69877007000001,"permission":"FullAccess"}}}""",
                Action.AddKey(
                    publicKey = PublicKey("ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog"),
                    accessKey = AccessKey(
                        nonce = 69877007000001,
                        permission = AccessKeyPermission.FullAccess
                    )
                )
            ),
            TestData(
                """{"DeleteKey":{"public_key":"ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog"}}""",
                Action.DeleteKey(
                    publicKey = PublicKey("ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog"),
                )
            ),
            TestData(
                """{"DeleteAccount":{"beneficiary_id":"benefeciary"}}""",
                Action.DeleteAccount(
                    beneficiaryId = "benefeciary"
                )
            ),
            TestData(
                """{"StakeChunkOnly":{"stake":100,"public_key":"ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog"}}""",
                Action.StakeChunkOnly(
                    stake = BigInteger.valueOf(100),
                    publicKey = PublicKey("ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog"),
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.writeValueAsString(b) shouldBe a
            }
        }
    }
})
