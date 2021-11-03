package antlapit.near.api.providers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

@ExperimentalCoroutinesApi
class JsonRpcProviderTest : BaseProviderTest() {

    @Test
    fun getAccount_whenPathParam_thenCorrect() = runBlocking {
        val resp = client.query<Any>("account/api_kotlin.testnet", "")
        println(resp)
        return@runBlocking
    }
}
