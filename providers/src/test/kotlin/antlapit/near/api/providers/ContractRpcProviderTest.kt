package antlapit.near.api.providers

import antlapit.near.api.providers.config.JsonRpcConfig
import antlapit.near.api.providers.config.Network
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ContractRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(Network.TESTNET))
    private lateinit var endpoint: ContractProvider

    @BeforeTest
    fun initEndpoint() {
        endpoint = ContractRpcProvider(client)
    }

    @Test
    fun viewAccount_whenLatest_thenCorrect() = runBlocking {
        val account = endpoint.getAccount(baseAccount, Finality.OPTIMISTIC)
        println(account)

        val accountByBlockId = endpoint.getAccount(baseAccount, account.blockHeight)
        assertEquals(account, accountByBlockId)

        val accountByBlockHash = endpoint.getAccount(baseAccount, account.blockHash)
        assertEquals(account, accountByBlockHash)
        return@runBlocking
    }

    @Test
    fun getContractCode_whenLatest_thenCorrect() = runBlocking {
        val contractCode = endpoint.getContractCode(testContractName, Finality.OPTIMISTIC)
        println(contractCode)

        val contractCodeByBlockId = endpoint.getContractCode(testContractName, contractCode.blockHeight)
        assertEquals(contractCode, contractCodeByBlockId)

        val contractCodeByBlockHash = endpoint.getContractCode(testContractName, contractCode.blockHash)
        assertEquals(contractCode, contractCodeByBlockHash)

        // check source
        val contractCodeRaw = Base64.getEncoder().encode(File("src/test/resources/contract/test_contract.wasm").readBytes())
        assertEquals(String(contractCodeRaw), contractCode.codeBase64)
        return@runBlocking
    }

    @Test
    fun getContractState_whenLatest_thenCorrect() = runBlocking {
        val contractState = endpoint.getContractState(baseAccount, Finality.OPTIMISTIC)
        println(contractState)

        val contractStateByBlockId = endpoint.getContractState(baseAccount, contractState.blockHeight)
        assertEquals(contractState, contractStateByBlockId)

        val contractStateByBlockHash = endpoint.getContractState(baseAccount, contractState.blockHash)
        assertEquals(contractState, contractStateByBlockHash)
        return@runBlocking
    }

    @Test
    fun callFunction_thenCorrect() = runBlocking {
        val resp = endpoint.callFunction(
            testContractName,
            "test",
            "{\"accountId\":\"api_kotlin.testnet\"}",
            Finality.OPTIMISTIC
        )
        val strRes = String(ByteArray(resp.result.size) { resp.result[it] })
        assertEquals("\"{\\\"accountId\\\":\\\"api_kotlin.testnet\\\"}\"", strRes)
        return@runBlocking
    }

    companion object {
        const val baseAccount = "api_kotlin.testnet"
        const val testContractName = "test_contract.$baseAccount"
    }
}
