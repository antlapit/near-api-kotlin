package antlapit.near.api.providers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
class ContractRpcProviderTest : BaseProviderTest() {

    private lateinit var endpoint: ContractProvider

    @BeforeTest
    fun initEndpoint() {
        endpoint = ContractRpcProvider(client)
    }

    @Test
    fun callFunction_thenCorrect() = runBlocking {
        val resp = endpoint.callFunction("guest-book.testnet", "getMessages", "{}", Finality.OPTIMISTIC)
        println(resp)
        return@runBlocking
    }

    @Test
    fun viewAccount_thenCorrect() = runBlocking {
        val resp = endpoint.getAccount("api_kotlin.testnet", Finality.OPTIMISTIC)
        println(resp)
        return@runBlocking
    }
}
