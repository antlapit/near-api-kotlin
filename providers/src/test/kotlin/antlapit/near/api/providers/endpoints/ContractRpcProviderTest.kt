package antlapit.near.api.providers.endpoints

import antlapit.near.api.providers.ContractProvider
import antlapit.near.api.providers.Finality
import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.base.config.JsonRpcConfig
import antlapit.near.api.providers.base.config.NetworkEnum
import antlapit.near.api.providers.exception.UnknownAccountException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.*
import kotlin.test.*

@ExperimentalCoroutinesApi
class ContractRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    private lateinit var endpoint: ContractProvider

    @BeforeTest
    fun initEndpoint() {
        endpoint = ContractRpcProvider(client)
    }

    @Test
    fun viewAccount_whenLatest_thenCorrect() = runBlocking {
        val accountByLastBlock = endpoint.getAccount(baseAccount, Finality.OPTIMISTIC)
        val accountByBlockId = endpoint.getAccount(baseAccount, accountByLastBlock.blockHeight)
        assertEquals(accountByLastBlock, accountByBlockId, "account by block id should equals account by last block id")

        val accountByBlockHash = endpoint.getAccount(baseAccount, accountByLastBlock.blockHash)
        assertEquals(
            accountByLastBlock,
            accountByBlockHash,
            "account by block id should equals account by last block hash"
        )
        return@runBlocking
    }

    @Test
    fun viewAccount_whenUnknown_thenCorrect() = runBlocking {
        val e = assertFails(
            message = "UnknownAccountException expected"
        ) {
            endpoint.getAccount("not_existing.api_kotlin.testnet")
        }
        assertTrue(e is UnknownAccountException)
        assertEquals("not_existing.api_kotlin.testnet", e.requestedAccountId)
    }

    @Test
    fun getContractCode_whenLatest_thenCorrect() = runBlocking {
        val contractCodeByLastBlock = endpoint.getContractCode(testContractName, Finality.OPTIMISTIC)
        val contractCodeByBlockId = endpoint.getContractCode(testContractName, contractCodeByLastBlock.blockHeight)
        assertEquals(
            contractCodeByLastBlock,
            contractCodeByBlockId,
            "contract code by block id should equals contract code by last block"
        )

        val contractCodeByBlockHash = endpoint.getContractCode(testContractName, contractCodeByLastBlock.blockHash)
        assertEquals(
            contractCodeByLastBlock,
            contractCodeByBlockHash,
            "contract code by block id should equals contract code by last block"
        )
    }

    @Test
    fun getContractCodeInBase64_whenLatest_thenCorrect() = runBlocking {
        val contractCodeByLastBlock = endpoint.getContractCode(testContractName, Finality.OPTIMISTIC)
        // check source
        val contractCodeRaw =
            Base64.getEncoder().encode(File("src/test/resources/contract/test_contract.wasm").readBytes())
        assertEquals(
            String(contractCodeRaw),
            contractCodeByLastBlock.codeBase64,
            "contract code should equals code in local file"
        )
        return@runBlocking
    }

    @Test
    fun getContractState_whenLatest_thenCorrect() = runBlocking {
        val contractState = endpoint.getContractState(baseAccount, Finality.OPTIMISTIC)

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
        assertEquals(
            "\"{\\\"accountId\\\":\\\"api_kotlin.testnet\\\"}\"",
            strRes,
            "smart contract should return expected string"
        )
        return@runBlocking
    }

    companion object {
        const val baseAccount = "api_kotlin.testnet"
        const val testContractName = "test_contract.$baseAccount"
    }
}
