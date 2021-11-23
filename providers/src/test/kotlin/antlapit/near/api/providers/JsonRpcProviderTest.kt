package antlapit.near.api.providers

import antlapit.near.api.providers.config.JsonRpcConfig
import antlapit.near.api.providers.config.Network
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

@ExperimentalCoroutinesApi
class JsonRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig( Network.TESTNET))

    @Test
    fun getAccount_whenPathParam_thenCorrect() = runBlocking {
        val resp = client.query<Any>("account/api_kotlin.testnet", "")
        println(resp)
        return@runBlocking
    }
}
