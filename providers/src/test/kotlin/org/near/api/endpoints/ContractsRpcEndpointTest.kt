package org.near.api.endpoints

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.near.api.exception.UnknownAccountException
import org.near.api.provider.JsonRpcProvider
import org.near.api.provider.config.JsonRpcConfig
import org.near.api.provider.config.NetworkEnum
import java.io.File
import java.util.*
import kotlin.test.*

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContractsRpcEndpointTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    private val archivalClient = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET_ARCHIVAL))
    private lateinit var endpoint: ContractsEndpoint
    private lateinit var archivalEndpoint: ContractsEndpoint
    private lateinit var archivalBlockEndpoint: BlockRpcEndpoint

    @BeforeAll
    fun initEndpoint() {
        endpoint = ContractsRpcEndpoint(client)
        archivalEndpoint = ContractsRpcEndpoint(archivalClient)
        archivalBlockEndpoint = BlockRpcEndpoint(archivalClient)
    }

    @AfterAll
    fun close() {
        client.close()
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
        val contractState = endpoint.getContractState(baseAccount, finality = Finality.OPTIMISTIC)

        val contractStateByBlockId = endpoint.getContractState(baseAccount, blockId = contractState.blockHeight)
        assertEquals(contractState, contractStateByBlockId)

        val contractStateByBlockHash = endpoint.getContractState(baseAccount, blockHash = contractState.blockHash)
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

    @Test
    fun getAccountsChanges_whenConcreteBlock_thenCorrect() = runBlocking {
        val accountId = "api_kotlin.testnet"
        val accountIds = listOf(accountId)

        val block = archivalBlockEndpoint.getBlock("6r9Kc66jNnkoDW2PyvqT9CLPvtaYnrcrU5Uq5htguB4a")

        val changesByBlockHash = archivalEndpoint.getAccountsChanges(accountIds, block.header.hash)

        val changesByBlockId = archivalEndpoint.getAccountsChanges(accountIds, block.header.height)
        assertEquals(changesByBlockId, changesByBlockHash, "changes in block by id and hash should be equal")
    }

    @Test
    fun getAccountsChanges_whenFinal_thenCorrect() : Unit = runBlocking {
        val accountId = "api_kotlin.testnet"
        val accountIds = listOf(accountId)

        val finalChanges = endpoint.getAccountsChanges(accountIds)
        assertNotNull(finalChanges.blockHash, "block hash should not be null")
    }

    @Test
    fun getContractStateChanges_whenConcreteBlock_thenCorrect() = runBlocking {
        val accountIds = listOf(testContractName)

        val block = archivalBlockEndpoint.getBlock("NvLBgCsEeRMzrMdLN8G1YFY1yD9mUmG3THcz8k7DEvv")

        val changesByBlockHash = archivalEndpoint.getContractStateChanges(
            accountIds = accountIds,
            blockHash = block.header.hash
        )

        val changesByBlockId = archivalEndpoint.getContractStateChanges(
            accountIds = accountIds,
            blockId = block.header.height
        )
        assertEquals(changesByBlockId, changesByBlockHash, "changes in block by id and hash should be equal")
    }

    @Test
    fun getContractStateChanges_whenFinal_thenCorrect() : Unit = runBlocking {
        val accountIds = listOf(testContractName)

        val finalChanges = endpoint.getContractStateChanges(accountIds)
        assertNotNull(finalChanges.blockHash, "block hash should not be null")
    }

    @Test
    fun getContractCodeChanges_whenConcreteBlock_thenCorrect() = runBlocking {
        val accountIds = listOf(testContractName)

        val block = archivalBlockEndpoint.getBlock("NvLBgCsEeRMzrMdLN8G1YFY1yD9mUmG3THcz8k7DEvv")

        val changesByBlockHash = archivalEndpoint.getContractCodeChanges(
            accountIds = accountIds,
            blockHash = block.header.hash
        )

        val changesByBlockId = archivalEndpoint.getContractCodeChanges(
            accountIds = accountIds,
            blockId = block.header.height
        )
        assertEquals(changesByBlockId, changesByBlockHash, "changes in block by id and hash should be equal")
    }

    @Test
    fun getContractCodeChanges_whenFinal_thenCorrect() : Unit = runBlocking {
        val accountIds = listOf(testContractName)

        val finalChanges = endpoint.getContractCodeChanges(accountIds)
        assertNotNull(finalChanges.blockHash, "block hash should not be null")
    }

    companion object {
        const val baseAccount = "api_kotlin.testnet"
        const val testContractName = "test_contract.$baseAccount"
    }
}
