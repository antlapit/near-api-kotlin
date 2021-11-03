package antlapit.near.api.providers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
class AccessKeyRpcProviderTest : BaseProviderTest() {

    private lateinit var endpoint: AccessKeyProvider

    @BeforeTest
    fun initEndpoint() {
        endpoint = AccessKeyRpcProvider(client)
    }

    @Test
    fun getAccessKeyList_thenCorrect() = runBlocking {
        val resp = endpoint.getAccessKeyList("api_kotlin.testnet", Finality.FINAL)
        println(resp)
        return@runBlocking
    }
}
