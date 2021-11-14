package antlapit.near.api.providers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ContractRpcProviderTest {

    private val client = JsonRpcProvider("https://rpc.testnet.near.org")
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
    fun viewAccount_whenLatest_thenCorrect() = runBlocking {
        val account = endpoint.getAccount("api_kotlin.testnet", Finality.OPTIMISTIC)
        println(account)

        val accountByBlockId = endpoint.getAccount("api_kotlin.testnet", account.blockHeight)
        assertEquals(account, accountByBlockId)

        val accountByBlockHash = endpoint.getAccount("api_kotlin.testnet", account.blockHash)
        assertEquals(account, accountByBlockHash)
        return@runBlocking
    }

    @Test
    fun getContractCode_whenLatest_thenCorrect() = runBlocking {
        val contractCode = endpoint.getContractCode("api_kotlin.testnet", Finality.OPTIMISTIC)
        println(contractCode)

        val contractCodeByBlockId = endpoint.getContractCode("api_kotlin.testnet", contractCode.blockHeight)
        assertEquals(contractCode, contractCodeByBlockId)

        val contractCodeByBlockHash = endpoint.getContractCode("api_kotlin.testnet", contractCode.blockHash)
        assertEquals(contractCode, contractCodeByBlockHash)
        return@runBlocking
    }
}
