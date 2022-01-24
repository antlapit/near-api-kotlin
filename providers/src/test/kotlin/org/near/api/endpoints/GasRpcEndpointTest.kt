package org.near.api.endpoints

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.near.api.provider.JsonRpcProvider
import org.near.api.provider.config.JsonRpcConfig
import org.near.api.provider.config.NetworkEnum
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GasRpcEndpointTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    private lateinit var endpoint: GasEndpoint
    private lateinit var blockEndpoint: BlockEndpoint

    @BeforeAll
    fun initEndpoint() {
        endpoint = GasRpcEndpoint(client)
        blockEndpoint = BlockRpcEndpoint(client)
    }

    @AfterAll
    fun close() {
        client.close()
    }

    @Test
    fun getLatestGasPrice_thenCorrect() = runBlocking {
        // latest block
        val finalGasPrice = endpoint.getLatestGasPrice()
        assertNotNull(finalGasPrice.gasPrice)
        return@runBlocking
    }

    @Test
    fun getGasPrice_byBlock_thenCorrect() = runBlocking {
        val block = blockEndpoint.getLatestBlock()

        // latest queried block by hash
        val priceByHash = endpoint.getGasPrice(block.header.hash)

        // latest queried block by id
        val priceById = endpoint.getGasPrice(block.header.height)
        assertEquals(priceByHash, priceById, "gas price should equals by block id and hash")
        return@runBlocking
    }

}
