package antlapit.near.api.providers

import antlapit.near.api.providers.config.JsonRpcConfig
import antlapit.near.api.providers.config.NetworkEnum
import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.PublicKey
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.math.BigInteger

@ExperimentalKotest
@ExperimentalCoroutinesApi
class AccessKeyRpcProviderTest : FunSpec({

    val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    val endpoint = AccessKeyRpcProvider(client)

    context("getAccessKeyList") {
        data class GetAccessKeyListData(
            val accountId: AccountId,
            val finality: Finality
        )

        withData<GetAccessKeyListData>(
            { "${it.accountId}, ${it.finality}" },
            listOf(GetAccessKeyListData("api_kotlin.testnet", Finality.FINAL))
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                endpoint.getAccessKeyList(a, b).keys.size shouldBeGreaterThan 0
            }
        }
    }

    context("getAccessKey") {
        data class GetAccessKeyData(
            val accountId: AccountId,
            val publicKey: PublicKey,
            val finality: Finality,
            val permission: AccessKeyPermission
        )

        withData<GetAccessKeyData>(
            { "${it.accountId}, ${it.permission}" },
            GetAccessKeyData(
                "api_kotlin.testnet", "ed25519:7jytAYzviGM2a3dcXYAD5YTtCcYJ3XiYSwLcHYdEx1uk", Finality.FINAL,
                AccessKeyPermission.FullAccess
            ),
            GetAccessKeyData(
                "api_kotlin.testnet",
                "ed25519:6PKfSu4zZarrFVk1Z4uf8kjiNwbMHSaiUmBgaWYF1dCj",
                Finality.FINAL,
                AccessKeyPermission.FunctionCall(
                    allowance = BigInteger("250000000000000000000000"),
                    receiverId = "skyward.testnet",
                    methodNames = emptyList()
                )
            )
        ) { (a, b, c, d) ->
            shouldNotThrow<Throwable> {
                endpoint.getAccessKey(a, b, c).permission shouldBe d
            }
        }
    }
})