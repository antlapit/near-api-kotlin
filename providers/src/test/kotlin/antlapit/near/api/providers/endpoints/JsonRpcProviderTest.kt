package antlapit.near.api.providers.endpoints

import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.base.config.JsonRpcConfig
import antlapit.near.api.providers.base.config.NetworkEnum
import antlapit.near.api.providers.model.account.Account
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

@ExperimentalCoroutinesApi
class JsonRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))

    @Test
    fun getAccount_whenPathParam_thenCorrect() = runBlocking {
        val resp = client.query<Account>("account/api_kotlin.testnet", "")
        println(resp)
        return@runBlocking
    }
}
